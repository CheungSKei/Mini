package com.mini.mn.db;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mini.mn.algorithm.FileOperation;
import com.mini.mn.algorithm.MD5;
import com.mini.mn.db.SqliteDB.IFactory;
import com.mini.mn.platformtools.ConfigFile;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import junit.framework.Assert;
import android.database.Cursor;

/**
 * 初始化数据库、复制表、打开加密数据库、打开非加密数据库、创建表
 * 
 * @version 1.0.0
 * @date 2014-10-09
 * @author S.Kei.Cheung
 */
class DBInit {
	private static final String TAG = "MicroMsg.DBInit";
	// 创建数据表正则
	private static final Pattern CREATE_TABLE_PATTERN = Pattern.compile("^[\\s]*CREATE[\\s]+TABLE[\\s]*",Pattern.CASE_INSENSITIVE);

	private MNDataBase db;
	private String key;

	public DBInit() {
	}

	public String getKey() {
		return key;
	}

	public MNDataBase getDB() {
		return db;
	}
	
	
	String errReportCntFile =  "";
	public String getError(){
		if(Util.isNullOrNil(errMsg) || Util.isNullOrNil(errReportCntFile)){
			return "";
		}
		String r = ConfigFile.getValue(errReportCntFile, "Reported");
		if(!Util.isNullOrNil(r)){
			return "";
		}
		ConfigFile.saveValue(errReportCntFile, "Reported", "true");
		return errMsg;
	}

	private String errMsg = "";
	
	/**
	 * 插入选项
	 * @param tFrom
	 * @param tTo
	 */
	private void insertSelect(String tFrom, String tTo) {
		Set<String> s = new HashSet<String>();
		String arr = "";

		Cursor cu = db.rawQuery("select * from " + tFrom + " limit 1 ", null);
		if (cu.getCount() == 0) { // nothing in fromTable
			cu.close();
			return;
		}
		cu.moveToFirst();
		for (int i = 0; i < cu.getColumnCount(); i++) {
			s.add(cu.getColumnName(i));
		}
		cu.close();
		// PRAGMA table_info 输出tTo表结构
		cu = db.rawQuery("PRAGMA table_info( " + tTo + " )", null);
		while (cu.moveToNext()) {
			String name = cu.getString(cu.getColumnIndex("name"));
			if (s.contains(name)) {
				arr += name;
				arr += ",";
			}
		}
		cu.close();
		arr = arr.substring(0, arr.length() - 1);
		String sql = "insert into " + tTo + "(" + arr + ") select " + arr + " from " + tFrom + ";";
		db.execSQL(sql);
	}

	/**
	 * 复制表
	 * @param dbCachePath
	 * @return
	 */
	private boolean copyTables(final String dbCachePath) {
		Cursor cu = db.rawQuery("select DISTINCT  tbl_name from sqlite_master;", null);
		if (cu == null) {
			return false;
		}
		db.execSQL("ATTACH DATABASE '" + dbCachePath + "' AS old KEY ''");
		db.beginTransaction();
		for (int i = 0; i < cu.getCount(); i++) {
			cu.moveToPosition(i);
			Cursor cu2 = db.rawQuery("select * from old.sqlite_master where tbl_name = '" + cu.getString(0) + "'", null);
			int cnt = 0;
			if (cu2 != null) {
				cnt = cu2.getCount();
				cu2.close();
			}
			if (cnt == 0) {
				Log.w(TAG, "In old db not found :" + cu.getString(0));
				continue;
			}
			try {
				insertSelect("old." + cu.getString(0), cu.getString(0));

			} catch (Exception e) {
				Log.w(TAG, "Insertselect FAILED :" + cu.getString(0));
				e.printStackTrace();
				cu.close();
				db.endTransaction();
				return false;
			}
		}
		db.endTransaction();

		cu.close();
		db.execSQL("DETACH DATABASE old;");
		return true;
	}

	/**
	 * 初始化非加密数据库
	 * @param dbCachePath
	 * @param factories
	 * @param checkCreateIni
	 * @return
	 */
	public boolean initSysDb(final String dbCachePath, final HashMap<Integer, IFactory> factories , final boolean checkCreateIni) {
		if (db != null) {
			db.close();
			db = null;
		}

		db = MNDataBase.openOrCreateSystemDatabase(dbCachePath);
		if (db == null) {
			return false;
		}
		if (!createTables(factories ,checkCreateIni)) {
			Log.e(TAG, "check Tables failed");
			return false;
		}
		return true;
	}

	/**
	 * 初始化数据库
	 * @param dbCachePath
	 * @param enDbCachePath
	 * @param uin
	 * @param imei
	 * @param factories
	 * @param checkCreateIni
	 * @return
	 */
	public boolean initDb(final String dbCachePath, final String enDbCachePath, final long uin, final String imei,
			final HashMap<Integer, IFactory> factories, final boolean checkCreateIni) {
		Assert.assertTrue("create SqliteDB dbCachePath == null ", !Util.isNullOrNil(dbCachePath));
		Assert.assertTrue("create SqliteDB enDbCachePath == null ", !Util.isNullOrNil(enDbCachePath));

		errReportCntFile = enDbCachePath  +  ".errreport";
		Log.d(TAG ,"initDb set :" + errReportCntFile);
		Log.i(TAG, "InitDb :" + dbCachePath);
		Log.i(TAG, "InitDb :" + enDbCachePath);

		if (db != null) {
			db.close();
			db = null;
		}
		boolean shouldCopyFromOld = (!FileOperation.fileExists(enDbCachePath) && (FileOperation.fileExists(dbCachePath)));

		openEncryptDatabase(enDbCachePath, uin, imei);
		if (db == null) {
			key = null;
			Log.e(TAG, "Failed to Use ENCRYPT database!");
			return initSysDb(dbCachePath, factories,checkCreateIni);
		}
		if (!createTables(factories,checkCreateIni)) {
//			Assert.assertTrue("ERROR : createTables failed", false);
			return false;
		}
		if (!shouldCopyFromOld) {
			return true;
		}
		if (copyTables(dbCachePath)) {
			return true;
		}
		Log.e(TAG, "Failed to COPY OLD DATA to ENCRYPT database!");
		key = null;
		db.close();
		db = null;
		return initSysDb(dbCachePath, factories,checkCreateIni);
	}

	/**
	 * 打开加密数据库
	 * @param enDbCachePath
	 * @param uin
	 * @param imei
	 * @return
	 */
	private boolean openEncryptDatabase(final String enDbCachePath, final long uin, final String imei) {
		db = MNDataBase.openOrCreateEncryptDatabase(enDbCachePath);
		if (db == null) {
			errMsg = "Endbinit open failed: [" + uin + "] dev: [" + imei + "]";
			return false;
		}
		key = MD5.getMessageDigest((imei + uin).getBytes()).substring(0, 7);
		String sql = "PRAGMA key=\"" + key + "\";";
		db.execSQL(sql);
		try {
			Cursor cu = db.rawQuery("select count(*) from sqlite_master limit 1;", null);
			if (cu != null) {
				cu.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "Check EnDB Key failed :" + e.getMessage() );
			errMsg = "Endbinit check failed: [" + uin + "] dev: [" + imei + "] msg:[" + e.getMessage() + "] stack:[" + e.getStackTrace() + "]";
			try {
				db.close();
			} catch (Exception e1) {
			}
			db = null;
			key = null;
			return false;
		}
		return true;
	}

	/**
	 * 创建表
	 * @param factories
	 * @param checkCreateIni
	 * @return
	 */
	private boolean createTables(HashMap<Integer, IFactory> factories , final boolean checkCreateIni) {
		String iniFilename = "";
		String newMd5 = "";
		if(checkCreateIni){
			iniFilename = db.getPath() + ".ini";
			StringBuilder hashSum = new StringBuilder();
			for (final IFactory factory : factories.values()) {
				for (final String sql : factory.getSQLs()) {
					hashSum.append(sql.hashCode());
				}
			}
			newMd5 = MD5.getMessageDigest(hashSum.toString().getBytes());
	
			String md5 = ConfigFile.getValue(iniFilename, "createmd5");
			if ((!Util.isNullOrNil(md5)) && (newMd5.equals(md5))) {
				Log.d(TAG, "Create table factories not changed , no need create !");
				return true;
			}
		}
		db.execSQL("pragma auto_vacuum = 0 ");
		Cursor cu1 = db.rawQuery("pragma journal_mode=\"WAL\"", null);
		cu1.close();

		db.beginTransaction();
		for (final IFactory factory : factories.values()) {
			for (final String sql : factory.getSQLs()) {
				Log.d(TAG, "init sql:" + sql);
				try {
					db.execSQL(sql);
				} catch (Exception e) {
					Matcher matcher = CREATE_TABLE_PATTERN.matcher(sql);
					if(matcher != null && matcher.matches()){ // MUST ASSERT!
						Assert.assertTrue("CreateTable failed:[" + sql + "][" + e.getMessage() + "]" , false);
						//TODO REBUILD ???
					}
					else{
						Log.f(TAG , "CreateTable failed:[" + sql + "][" + e.getMessage() + "]");
					}
				}
			}
		}
		db.endTransaction();
		if(checkCreateIni){
			ConfigFile.saveValue(iniFilename, "createmd5", newMd5);
		}
		return true;
	}

}
