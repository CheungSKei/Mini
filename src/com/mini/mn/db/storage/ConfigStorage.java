package com.mini.mn.db.storage;

import com.mini.mn.algorithm.LRUMap;
import com.mini.mn.db.ISQLiteDatabase;
import com.mini.mn.db.MemoryStorage;
import com.mini.mn.db.MemoryStorage.IOnAttachTable;
import com.mini.mn.db.SqliteDB;
import com.mini.mn.util.Log;

import junit.framework.Assert;
import android.content.ContentValues;
import android.database.Cursor;

public class ConfigStorage extends MStorage implements IOnAttachTable {
	private static final String TAG = "MiniMsg.ConfigStorage";

	public static final String[] SQL_CREATE = new String[] {
			"CREATE TABLE IF NOT EXISTS " + ConfigStorage.TABLE
			+ " ( " + ConfigStorage.COL_ID + " INTEGER PRIMARY KEY, " + ConfigStorage.COL_TYPE
			+ " INT, " + ConfigStorage.COL_VALUE + " TEXT )" };

	public static final String TABLE = "userinfo";
	public static final String COL_ID = "id";
	public static final String COL_TYPE = "type";
	public static final String COL_VALUE = "value";

	// private static final int INDEX_ID = 0;
	private static final int INDEX_TYPE = 1;
	private static final int INDEX_VALUE = 2;

	private static final int TYPE_INT = 1;
	private static final int TYPE_LONG = 2;
	private static final int TYPE_STRING = 3;
	private static final int TYPE_BOOLEAN = 4;
	private static final int TYPE_FLOAT = 5;
	private static final int TYPE_DOUBLE = 6;

	private ISQLiteDatabase db;
	private final LRUMap<Integer, TypeVal> cachesConfig = new LRUMap<Integer, TypeVal>(100);

	private static final int TYPE_EMPTY = -1;

	static class TypeVal {
		public int type;
		public String val;

		TypeVal() {
			type = TYPE_EMPTY;
			val = null;
		}
	}

	public ConfigStorage(SqliteDB db) {
		this.db = db;
	}

	@Override
	public String getTableName() {
		return ConfigStorage.TABLE;
	}

	@Override
	public int onAttachTable(MemoryStorage memDB) {
		if (memDB != null) {
			this.db = memDB;
		}
		return 0;
	}

	public Object get(int id) {
		return get(id, null);
	}

	public Object get(int id, Object defVal) {
		Assert.assertTrue("db is null"  , db != null);
		Assert.assertTrue("cachesConfig is null"  , cachesConfig != null);
		
		TypeVal tv = cachesConfig.getAndUptime(id);
		if (tv != null) {
			return resolveObj(tv.type, tv.val);
		}

		Cursor cu = db.query(TABLE, null, COL_ID + "=" + id, null, null, null, null);
		if (cu.getCount() > 0) {
			cu.moveToFirst();
			tv = new TypeVal();
			tv.type = cu.getInt(INDEX_TYPE);
			tv.val = cu.getString(INDEX_VALUE);
			cachesConfig.update(id, tv);
		}
		cu.close();
		if (tv != null) {
			return resolveObj(tv.type, tv.val);
		} else {
			return defVal;
		}
	}

	public void set(int id, Object value) {
		Assert.assertTrue("db is null"  , db != null);
		Assert.assertTrue("cachesConfig is null"  , cachesConfig != null);
		
		Object old = get(id, null);
		cachesConfig.remove(id);

		if (value == null) {
			if (old != null) {
				db.delete(TABLE, COL_ID + "=" + id, null);
				doNotify("" + id);
			}
			return;
		}

		if (old != null && old.toString().equals(value) && getInstanceof(old) == getInstanceof(value)) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(COL_ID, id);

		int type = getInstanceof(value);
		if (type == TYPE_EMPTY) {
			return;
		}
		values.put(COL_TYPE, type);
		values.put(COL_VALUE, value.toString());

		db.replace(TABLE, COL_ID, values);
		doNotify("" + id);
	}

	public void reset() {

	}

	private int getInstanceof(Object value) {
		if (value instanceof Integer) {
			return TYPE_INT;

		} else if (value instanceof Long) {
			return TYPE_LONG;

		} else if (value instanceof String) {
			return TYPE_STRING;

		} else if (value instanceof Boolean) {
			return TYPE_BOOLEAN;

		} else if (value instanceof Float) {
			return TYPE_FLOAT;

		} else if (value instanceof Double) {
			return TYPE_DOUBLE;

		}
		Log.e(TAG, "unresolve failed, unknown type=" + value.getClass().toString());
		return TYPE_EMPTY;
	}

	private Object resolveObj(int type, String value) {

		try {
			switch (type) {
			case TYPE_INT:
				return Integer.valueOf(value);

			case TYPE_LONG:
				return Long.valueOf(value);

			case TYPE_STRING:
				return value;

			case TYPE_BOOLEAN:
				return Boolean.valueOf(value);

			case TYPE_FLOAT:
				return Float.valueOf(value);

			case TYPE_DOUBLE:
				return Double.valueOf(value);

			default:
				// Log.e(TAG, "unknown type");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
