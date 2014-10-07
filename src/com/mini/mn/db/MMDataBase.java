package com.mini.mn.db;

import junit.framework.Assert;
import android.content.ContentValues;
import android.database.Cursor;

class MMDataBase {
	private info.guardianproject.database.sqlcipher.SQLiteDatabase enDB = null;
	private android.database.sqlite.SQLiteDatabase sysDB = null;

	private boolean isMem = false;

	private boolean isEncrypt() {
		if (!((enDB == null) || (sysDB == null))) {
			Assert.assertTrue("two db not null at same time", false);
		}
		if (!((enDB != null) || (sysDB != null))) {
			Assert.assertTrue("two db null at same time", false);
		}
		return enDB != null;
	}

	public boolean isMemory() {
		return isMem;
	}

	public static MMDataBase openOrCreateSystemDatabase(String dbCachePath) {
		MMDataBase db = new MMDataBase();
		if ((dbCachePath == null) || (dbCachePath.length() == 0)) {
			db.sysDB = android.database.sqlite.SQLiteDatabase.create(null);
			db.isMem = true;
			return (db.sysDB == null) ? null : db;
		}
		try {
			db.sysDB = android.database.sqlite.SQLiteDatabase.openOrCreateDatabase(dbCachePath, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (db.sysDB == null) ? null : db;
	}

	public String getPath() {
		if (isEncrypt()) {
			return enDB.getPath();
		} else {
			return sysDB.getPath();
		}
	}

	public static MMDataBase openOrCreateEncryptDatabase(String dbCachePath) {
		MMDataBase db = new MMDataBase();
		if ((dbCachePath == null) || (dbCachePath.length() == 0)) {
			db.enDB = info.guardianproject.database.sqlcipher.SQLiteDatabase.openOrCreateDatabase(":memory:", null);
			db.isMem = true;
			return (db.enDB == null) ? null : db;
		}
		try {
			db.enDB = info.guardianproject.database.sqlcipher.SQLiteDatabase.openOrCreateDatabase(dbCachePath, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (db.enDB == null) ? null : db;
	}

	public boolean isOpen() {
		if (isEncrypt() && enDB != null) {
			return enDB.isOpen();

		} else if (sysDB != null) {
			return sysDB.isOpen();
		}
		return false;
	}

	public void close() {
		try {
			if (enDB != null && enDB.isOpen()) {
				enDB.close();
				enDB = null;
			}
			if (sysDB != null && sysDB.isOpen()) {
				sysDB.close();
				sysDB = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Cursor rawQuery(String string, String[] object) {
		if (isEncrypt()) {
			return enDB.rawQuery(string, object);
		} else {
			return sysDB.rawQuery(string, object);
		}
	}

	public void execSQL(String string) {
		if (isEncrypt()) {
			enDB.execSQL(string);
		} else {
			sysDB.execSQL(string);
		}
	}

	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		if (isEncrypt()) {
			return enDB.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		} else {
			return sysDB.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		}
	}

	public long insert(String table, String primaryKey, ContentValues values) {
		if (isEncrypt()) {
			return enDB.insert(table, primaryKey, values);
		} else {
			return sysDB.insert(table, primaryKey, values);
		}
	}

	public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		if (isEncrypt()) {
			return enDB.update(table, values, whereClause, whereArgs);
		} else {
			return sysDB.update(table, values, whereClause, whereArgs);
		}
	}

	public long replace(String table, String primaryKey, ContentValues values) {
		if (isEncrypt()) {
			return enDB.replace(table, primaryKey, values);
		} else {
			return sysDB.replace(table, primaryKey, values);
		}
	}

	public int delete(String table, String whereClause, String[] whereArgs) {
		if (isEncrypt()) {
			return enDB.delete(table, whereClause, whereArgs);
		} else {
			return sysDB.delete(table, whereClause, whereArgs);
		}
	}

	public void beginTransaction() {
		if (isEncrypt()) {
			enDB.beginTransaction();
		} else {
			sysDB.beginTransaction();
		}
	}

	public void endTransaction() {
		if (isEncrypt()) {
			enDB.setTransactionSuccessful();
			enDB.endTransaction();
		} else {
			sysDB.setTransactionSuccessful();
			sysDB.endTransaction();
		}
	}

	// inTransaction ()
	// Since: API Level 1
	// Returns true if the current thread has a transaction pending.
	// !!!! FUCK ( the current thread ) !!!!!
	// public boolean inTransaction() {
	// if (isEncrypt()) {
	// return enDB.inTransaction();
	// } else {
	// return sysDB.inTransaction();
	// }
	// }

	public static boolean checkTableExist(MMDataBase db, String table) {
		Cursor cu = db.rawQuery("select tbl_name from sqlite_master  where type = \"table\" and tbl_name=\"" + table
				+ "\"", null);
		if (cu == null) {
			return false;
		}
		int count = cu.getCount();
		cu.close();
		return count > 0;
	}

}
