package com.mini.mn.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import junit.framework.Assert;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * ÄÚ´æ´¢´æ
 * 
 * @version 1.0.0
 * @date 2014-10-08
 * @author S.Kei.Cheung
 */
public class MemoryStorage implements ISQLiteDatabase {
	private static final String TAG = "Mini.MemoryStorage";

	private MNDataBase db = null;
	private SqliteDB diskDB = null;

	private boolean hasAttach = false;

	private static String lastCloseStack = "";
	
	public void closeDB() {
		lastCloseStack = Util.getStack();
		if (db != null) {
			db.close();
			db = null;
		}
	}
	
	public boolean isClose(){
		if((db ==null)  || (!db.isOpen()) ){
			Log.e(TAG, "memory db is close [%s]" , lastCloseStack);
			return true;
		}
		return false;
	}

	public MemoryStorage(SqliteDB diskDB) {
		this.diskDB = diskDB;
		if (!Util.isNullOrNil(diskDB.getKey())) {
			db = MNDataBase.openOrCreateEncryptDatabase(null);
		}
	}

	private void attachTable() {
		try {
			if (hasAttach) {
				db.execSQL("DETACH DATABASE old");
				hasAttach = false;
			}
			if (Util.isNullOrNil(diskDB.getKey())) {
				db.execSQL("ATTACH DATABASE '" + diskDB.getPath() + "' AS old ");
			} else {
				db.execSQL("ATTACH DATABASE '" + diskDB.getPath() + "' AS old KEY '" + diskDB.getKey() + "'");
			}
			hasAttach = true;
		} catch (Exception e) {
			hasAttach = false;
			Log.e(TAG, "ERROR : attach disk db [%s] , will do again !", e.getMessage());
			e.printStackTrace();
		}
	}

	public void preDiskClose() {
		Set<String> s = mapWriteHolder.keySet();
		for (Iterator<String> it = s.iterator(); it.hasNext();) {
			WriteSqlHolder h = mapWriteHolder.get(it.next());
			h.appendAllToDisk();
		}
	}

	public void postDiskBeginTrans() {
	}

	Map<String, WriteSqlHolder> mapWriteHolder = new HashMap<String, WriteSqlHolder>();

	private int copyTable(String tableName) {
		if (diskDB == null || diskDB.inTransaction()) {
			return -3;
		}
		try {
			if (MNDataBase.checkTableExist(db, tableName)) {
				db.execSQL("drop table " + tableName);
			}
			Cursor cu = diskDB.rawQuery(" select sql from sqlite_master where tbl_name=\"" + tableName + "\" and type = \"table\"", null);
			String create = null;
			if (cu != null) {
				if (cu.getCount() == 1) {
					cu.moveToFirst();
					create = cu.getString(0);
				}
				cu.close();
			}
			if (create == null) {
				return -1;
			}
			db.execSQL(create);
			db.execSQL("insert into " + tableName + " select * from old." + tableName);
			Log.d(TAG, "copy table %s success", tableName);
			return 0;
		} catch (Exception e) {
			// e.printStackTrace();
			return -2;
		}
	}

	public boolean appendToDisk(String tableName) {
		if (Util.isNullOrNil(tableName)) {
			return false;
		}
		WriteSqlHolder h = mapWriteHolder.get(tableName);
		if (h == null) {
			return false;
		}
		h.appendAllToDisk();
		return true;
	}

	public interface IOnAttachTable {
		int onAttachTable(MemoryStorage memDB);

		String getTableName();
	}

	public void postEndTransCallback() {
		if (waitToAttach.size() > 0) {
			attachTable(null);
		}
	}

	Queue<IOnAttachTable> waitToAttach = new LinkedList<MemoryStorage.IOnAttachTable>();

	public boolean dettachTable(final IOnAttachTable mStg) {
		try {
			WriteSqlHolder h = mapWriteHolder.get(mStg.getTableName());
			if (h != null) {
				h.appendAllToDisk();
			}
			db.execSQL("drop table " + mStg.getTableName());

		} catch (Exception e) {
		}
		Log.d(TAG, "dettach table %s succ", mStg.getTableName());
		return true;
	}

	public boolean attachTable(final IOnAttachTable mStg) {
		if (db == null) {
			return false;
		}

		if (mStg != null) {
			waitToAttach.add(mStg);
		}
		if (diskDB.inTransaction()) {
			return false;
		}

		while (waitToAttach.size() > 0) {
			if (diskDB.inTransaction()) {
				return false;
			}
			IOnAttachTable stg = waitToAttach.peek();
			if (stg == null) {
				waitToAttach.poll();
				continue;
			}
			String table = stg.getTableName();
			if (Util.isNullOrNil(table)) {
				Log.e(TAG, "Error table Name :%s", table);
				waitToAttach.poll();
				continue;
			}
			if (MNDataBase.checkTableExist(db, table)) {
				Log.e(TAG, "Error Attach table twice :%s", table);
				waitToAttach.poll();
				continue;
			}
			int ret = copyTable(table);
			if (ret != 0) {
				attachTable();
				if (0 != copyTable(table)) { // do it again
					Log.e(TAG, "copy table failed :" + table);
					return false;
				}
			}
			Log.d(TAG, "Attach Table %s succ", table);
			stg.onAttachTable(this);
			WriteSqlHolder h = new WriteSqlHolder(diskDB, table);
			mapWriteHolder.put(table, h);
			waitToAttach.poll();
		}
		return true;
	}

	@Override
	public int delete(String table, String whereClause, String[] whereArgs) {
		Assert.assertTrue("Not Attach Mem Storage:" + table, mapWriteHolder.containsKey(table));

		if (db != null && (db.isOpen())) {
			mapWriteHolder.get(table).delete(whereClause, whereArgs);
			return db.delete(table, whereClause, whereArgs);
		}
		Log.w(TAG, "memoryDB already close delete [%s] [%s]", table ,lastCloseStack);
		if (diskDB != null && (diskDB.isOpen())) {
			return diskDB.delete(table, whereClause, whereArgs);
		}
		return -1;
	}

	@Override
	public boolean execSQL(String table, String sql) {
		Assert.assertTrue("Not Attach Mem Storage:" + table, mapWriteHolder.containsKey(table));

		if (db != null && (db.isOpen())) {
			mapWriteHolder.get(table).execSQL(sql);
			db.execSQL(sql);
			return true;
		}
		Log.w(TAG, "memoryDB already close execSQL [%s] [%s]", table ,lastCloseStack);
		if (diskDB != null && (diskDB.isOpen())) {
			diskDB.execSQL(sql, table);
			return true;
		}
		return false;
	}

	@Override
	public long insert(String table, String primaryKey, ContentValues values) {
		Assert.assertTrue("Not Attach Mem Storage:" + table, mapWriteHolder.containsKey(table));

		if (db != null && (db.isOpen())) {
			mapWriteHolder.get(table).insert(primaryKey, values);
			return db.insert(table, primaryKey, values);
		}
		Log.w(TAG, "memoryDB already close insert [%s] [%s]", table ,lastCloseStack);
		if (diskDB != null && (diskDB.isOpen())) {
			return diskDB.insert(table, primaryKey, values);
		}
		return -1;
	}

	@Override
	public long replace(String table, String primaryKey, ContentValues values) {
		Assert.assertTrue("Not Attach Mem Storage:" + table, mapWriteHolder.containsKey(table));
		if (db != null && (db.isOpen())) {
			mapWriteHolder.get(table).replace(primaryKey, values);
			return db.replace(table, primaryKey, values);
		}
		Log.w(TAG, "memoryDB already close replace [%s] [%s]", table, lastCloseStack);
		if (diskDB != null && (diskDB.isOpen())) {
			return diskDB.replace(table, primaryKey, values);
		}
		return -1;
	}

	@Override
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		Assert.assertTrue("Not Attach Mem Storage:" + table, mapWriteHolder.containsKey(table));
		if (db != null && (db.isOpen())) {
			mapWriteHolder.get(table).update(values, whereClause, whereArgs);
			return db.update(table, values, whereClause, whereArgs);
		}
		Log.w(TAG, "memoryDB already close update [%s] [%s]", table , lastCloseStack);
		if (diskDB != null && (diskDB.isOpen())) {
			return diskDB.update(table, values, whereClause, whereArgs);
		}
		return -1;
	}

	@Override
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

		if (db != null && (db.isOpen())) {
			return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		}
		Log.w(TAG, "memoryDB already close query [%s] [%s]", table , lastCloseStack);
		return EmptyCursor.get();

	}

	@Override
	public Cursor rawQuery(String sql, String[] selectionArgs) {
		if (db != null && (db.isOpen())) {

			return db.rawQuery(sql, selectionArgs);
		}
		Log.w(TAG, "memoryDB already close rawQuery [%s] [%s]", sql , lastCloseStack);
		return EmptyCursor.get();
	}
}