package com.mini.mn.db;

import java.util.HashMap;

import com.mini.mn.platformtools.CodeInfo;
import com.mini.mn.platformtools.MMHandlerThread;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import junit.framework.Assert;
import android.content.ContentValues;
import android.database.Cursor;
/**
 * 数据库操作实现类
 * 
 * @version 1.0.0
 * @date 2014-10-08
 * @author S.Kei.Cheung
 */
public class SqliteDB implements ISQLiteDatabase {
	private String TAG = "Mini.SqliteDB";
	protected MMDataBase db = null;
	private Callbacks callback = null;
	private DBInit dbInit = new DBInit();

	private String closeDBStack = "";
	
	public interface Callbacks {
		void preCloseCallback();

		void postBeginTransCallback();

		void postEndTransCallback();
	}

	public SqliteDB(Callbacks callback) {
		this.callback = callback;
	}

	public SqliteDB() {

	}

	public interface IFactory {
		String[] getSQLs();
	}

	@Override
	protected void finalize() {
		closeDB(false);
	}
	
	public void closeDB(){
		closeDB(false);
	}

	public void closeDB(final boolean isUEH) {
		if (db == null) {
			return;
		}
		if (callback != null) {
			callback.preCloseCallback();
		}
		Log.d(TAG, "begin close db, inTrans:%b ticket:%s  thr:%d {%s}", inTransaction(), Long.toHexString(transactionTicket) , Thread.currentThread().getId() , Util.getStack());
		CodeInfo.TestTime t = new CodeInfo.TestTime();
		if(isUEH){
			closeDBStack = Util.getStack();
		}
		db.close(); // here may be {{{{{WAIT}}}}} END TRANS BY OTHER TRNAS !!
		db = null;
		Log.d(TAG, "end close db time:%d", t.GetDiff());
	}

	public boolean initDb(final String dbCachePath, final HashMap<Integer, IFactory> factories , final boolean checkCreateIni) {
		int idx = dbCachePath.lastIndexOf("/");
		if(idx != -1){
			 TAG += ("." + dbCachePath.substring(idx + 1));
		}
		if (dbInit.initSysDb(dbCachePath, factories , checkCreateIni) && (dbInit.getDB() != null)) {
			this.db = dbInit.getDB();
			return true;
		}
		this.db = null;
		dbInit = null;
		Log.d(TAG ,"initDB failed.");
		return false;
	}

	public String getInitErrMsg(){
		return initErrMsg;
	}
	private String initErrMsg = "";
	public boolean initDb(final String dbCachePath, final String enDbCachePath, final long uin, final String imei,
			final HashMap<Integer, IFactory> factories , final boolean checkCreateIni) {
		int idx = dbCachePath.lastIndexOf("/");
		if(idx != -1){
			 TAG += ("." + dbCachePath.substring(idx + 1));
		}
		if (dbInit.initDb(dbCachePath, enDbCachePath, uin, imei, factories ,checkCreateIni) && (dbInit.getDB() != null)) {
			initErrMsg = dbInit.getError();
			this.db = dbInit.getDB();
			return true;
		}
		initErrMsg = dbInit.getError();
		this.db = null;
		dbInit = null;
		Log.d(TAG ,"initDB failed.");
		return false;
	}

	public boolean isOpen() {
		if (db != null && db.isOpen()) {
			return true;
		}
		Assert.assertTrue("DB has been closed :[" + closeDBStack + "]", Util.isNullOrNil(closeDBStack));

		return false;
	}

	public String getKey() {
		if (dbInit == null) {
			return null;
		}
		return dbInit.getKey();
	}

	public String getPath() {
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return null;
		}
		return db.getPath();
	}

	public static String escape(String sql) {
		if (Util.isNullOrNil(sql)) {
			return "";
		}
		return android.database.DatabaseUtils.sqlEscapeString(sql);
	}

	@Override
	public Cursor query(final String table, final String[] columns, final String selection,
			final String[] selectionArgs, final String groupBy, final String having, final String orderBy) {
		
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return EmptyCursor.get();
		}
		DKTest.begin();

		try {
			Cursor cu = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
			DKTest.print(table, cu, transactionTicket, true);
			return cu;
		} catch (Exception e) {
			Log.e(TAG, "execSQL Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return EmptyCursor.get();
		}
	}

	@Override
	public Cursor rawQuery(final String sql, final String[] selectionArgs) {
		Assert.assertTrue("sql is null ", !Util.isNullOrNil(sql));
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return EmptyCursor.get();
		}
		DKTest.begin();
		try {
			Cursor cu = db.rawQuery(sql, selectionArgs);
			DKTest.print(sql, cu, transactionTicket, true);
			return cu;
		} catch (Exception e) {
			Log.e(TAG, "execSQL Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return EmptyCursor.get();
		}
	}

	public boolean execSQL(String table, final String sql) {
		Assert.assertTrue("sql is null ", !Util.isNullOrNil(sql));
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return false;
		}
		DKTest.begin();
		try {
			db.execSQL(sql);
			DKTest.print(sql, null, transactionTicket, true);
			return true;
		} catch (final Exception e) {
			Log.e(TAG, "execSQL Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return false;
		}
	}

	@Override
	public long insert(final String table, final String primaryKey, final ContentValues values) {
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return -2;
		}
		DKTest.begin();
		try {
			long ret = db.insert(table, primaryKey, values);
			DKTest.print(table, null, transactionTicket, true);
			return ret;
		} catch (final Exception e) {
			Log.e(TAG, "insert Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return -1;
		}
	}

	@Override
	public int update(final String table, final ContentValues values, final String whereClause, final String[] whereArgs) {
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return -2;
		}
		DKTest.begin();
		try {
			int ret = db.update(table, values, whereClause, whereArgs);
			DKTest.print(table, null, transactionTicket, true);
			return ret;
		} catch (final Exception e) {
			Log.e(TAG, "update Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return -1;
		}
	}

	@Override
	public long replace(final String table, final String primaryKey, final ContentValues values) {
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return -2;
		}
		DKTest.begin();
		try {
			long ret = db.replace(table, primaryKey, values);
			DKTest.print(table, null, transactionTicket, true);
			return ret;
		} catch (final Exception e) {
			Log.e(TAG, "repalce  Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return -1;
		}
	}

	@Override
	public int delete(final String table, final String whereClause, final String[] whereArgs) {
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return -2;
		}
		DKTest.begin();
		try {
			int ret = db.delete(table, whereClause, whereArgs);
			DKTest.print(table, null, transactionTicket, true);
			return ret;
		} catch (final Exception e) {
			Log.e(TAG, "delete Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return -1;
		}
	}

	public boolean drop(final String table) {
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return false;
		}
		try {
			db.execSQL("DROP TABLE " + table);
		} catch (final Exception e) {
			Log.e(TAG, "drop table Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return false;
		}
		return false;
	}

	private long transactionTicket = 0;

	public synchronized long beginTransaction() {
		return beginTransaction(-1);
	}

	public synchronized long beginTransaction(final long threadID) {

		final long curId = Thread.currentThread().getId();
		Log.d(TAG, "beginTransaction thr:(%d,%d) ticket:%d db:%b  {%s}", threadID, curId, transactionTicket, isOpen() ,  Util.getStack());
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return -4;
		}

		if (transactionTicket > 0) {
			Log.e(TAG, "ERROR beginTransaction transactionTicket:" + transactionTicket);
			return -1;
		}

		if ((!MMHandlerThread.isMainThread()) && (threadID == -1)) {
			Log.e(TAG, "FORBID: beginTrans UNKNOW_THREAD ParamID:%d nowThr:%d", threadID, curId);
			return -2;
		}

		try {
			DKTest.begin();
			db.beginTransaction();
			DKTest.print("beginTrans", null, 0, true);
		} catch (Exception e) {
			Log.e(TAG, "beginTransaction Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return -3;
		}
		transactionTicket = (Util.nowMilliSecond() & 0x7fffffffL);
		transactionTicket |= ((curId & 0x7fffffffL) << 32);

		if (callback != null) {
			callback.postBeginTransCallback();
		}
		return transactionTicket;
	}

	public synchronized int endTransaction(final long ticket) {

		final long curId = Thread.currentThread().getId();
		Log.d(TAG, "endTransaction thr:%d ticket:(%d,%d) db:%b  {%s} ", curId, ticket, transactionTicket, isOpen() , Util.getStack());

		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return -4;
		}

		if (ticket != transactionTicket) {
			Log.e(TAG, "ERROR endTransaction ticket:" + ticket + " transactionTicket:" + transactionTicket);
			return -1;
		}
		long begId = ((ticket >> 32) & 0x7fffffffL);
		if (begId != curId) {
			Log.e(TAG, "FORBID: endTrans UNKNOW_THREAD ticket:%s ParamID:%d nowThr:%d", Long.toHexString(ticket),
					begId, curId);
			return -2;
		}

		try {
			DKTest.begin();
			db.endTransaction();
			DKTest.print("endTrans", null, 0, true);
		} catch (Exception e) {
			Log.e(TAG, "endTransaction Error :" + e.getMessage());
			DKTest.printStackTrace(e);
			return -3;
		}
		transactionTicket = 0;
		if (callback != null) {
			callback.postEndTransCallback();
		}
		return 0;
	}

	public synchronized boolean inTransaction() {
		if (!isOpen()) {
			Log.e(TAG, "DB IS CLOSED ! {%s}"  , Util.getStack());
			return false;
		}
		return (transactionTicket > 0);
//		 inTransaction ()
//		 Since: API Level 1
//		 Returns true if the current thread has a transaction pending.
//		!!!!	FUCK   	( the current thread )  !!!!!	
	}

	public static boolean checkTableExist(SqliteDB db, String table) {
		return MMDataBase.checkTableExist(db.db, table);
	}
}

final class DKTest {

	private static boolean on = false;
	private static int index = 0;
	private static CodeInfo.TestTime runTime = null;
	private static long checkCursorTime = 0;
	private static int cusorCount = 0;

	private DKTest() {
	}

	public static void printStackTrace(Exception e) {
		if (on) {
			e.printStackTrace();
		}
	}

	static void begin() {
		if (!on) {
			return;
		}
		runTime = new CodeInfo.TestTime();
		index++;
	}

	static void checkCursor(Cursor cu) {
		if (!on) {
			return;
		}

		if (cu == null) {
			return;
		}
		cusorCount = cu.getCount();
		CodeInfo.TestTime t = new CodeInfo.TestTime();

		for (int i = 0; i < cusorCount; i++) {
			cu.moveToPosition(i);
		}
		cu.moveToPosition(-1);
		checkCursorTime = t.GetDiff();
	}

	static void print(String string, Cursor cu, long transactionTicket, boolean getStack) {
		if (!on) {
			return;
		}
		long diff = runTime.GetDiff();
		String t = "Thread:[" + Thread.currentThread().getId() + "," + Thread.currentThread().getName() + "]";
		t += "[" + index + "][" + diff + "]";
		if (transactionTicket != 0) {
			t += "[INTRANS]";
		}
		if (cu != null) {
			checkCursor(cu);
			t += "[cuCnt:" + cusorCount + "," + "cuTime:" + checkCursorTime + "]";
		}
		t += "[" + string + "]--";
		if (getStack) {
			t += Util.getStack();
		}
		Log.v("MicroMsg.dbtest", t);
	}

}