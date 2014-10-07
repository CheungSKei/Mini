package com.mini.mn.db;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mini.mn.platformtools.MTimerHandler;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import android.content.ContentValues;

public class WriteSqlHolder {
	private static final String TAG = "MicroMsg.MemoryStorage.Holder";

	public static final int SYNC_TYPE_NO_CACHE = 1;
	public static final int SYNC_TYPE_IN_TRANS = 2;
	public static final int SYNC_TYPE_TIMER = 3;
	public static final int SYNC_TYPE_MAX_COUNT = 4;
	public static final int MAX_SYNC_TYPE = 5;

	private SqliteDB diskDB;
	private String table = null;

	private static final int MAX_QUEUE_SIZE = 40;
	private static final long TIME_MAX_SYNC = Util.MILLSECONDS_OF_MINUTE;
	private MTimerHandler timer = new MTimerHandler(new MTimerHandler.CallBack() {

		@Override
		public boolean onTimerExpired() {
			if (!diskDB.isOpen()) {
				return false;
			}
			appendAllToDisk();
			return false;
		}
	}, false);

	private BlockingQueue<Holder> queue = new LinkedBlockingQueue<Holder>();

	public WriteSqlHolder(SqliteDB diskDB, final String table) {
		this.diskDB = diskDB;
		this.table = table;
	}

	private int appendToDisk(Holder h) {
		if(h == null){
			Log.w(TAG , "appendToDisk Holder == null. table:%s" , table);
			return -2;
		}
		if(diskDB == null || (!diskDB.isOpen())){
			Log.e(TAG , "appendToDisk diskDB already close. table:%s" , table);
			return -3;
		}
		if (h.funcType == Holder.FUNC_INSERT) {
			diskDB.insert(table, h.primaryKey, h.values);
		} else if (h.funcType == Holder.FUNC_DELETE) {
			diskDB.delete(table, h.whereClause, h.whereArgs);
		} else if (h.funcType == Holder.FUNC_EXEC) {
			diskDB.execSQL(table, h.sql);
		} else if (h.funcType == Holder.FUNC_REPLACE) {
			diskDB.replace(table, h.primaryKey, h.values);
		} else if (h.funcType == Holder.FUNC_UPDATE) {
			diskDB.update(table, h.values, h.whereClause, h.whereArgs);
		} else {
			return -1;
		}
		return 0;
	}

	public int appendAllToDisk() {
		Log.d(TAG ,"appendAllToDisk table:%s trans:%b queue:%d" , table , diskDB.inTransaction() , queue.size());
		if (queue.isEmpty()) {
			return 0;
		}
		long ticket = 0;
		if (!diskDB.inTransaction()) {
			ticket = diskDB.beginTransaction(Thread.currentThread().getId());
		}
		while (!queue.isEmpty()) {
			appendToDisk(queue.poll());
		}
		if (ticket > 0) {
			diskDB.endTransaction(ticket);
		}
		return 0;
	}

	private int add(Holder h) {
		queue.add(h);
		if (queue.size() >= MAX_QUEUE_SIZE) {
			appendAllToDisk();
		}
		if (timer.stopped()) {
			timer.startTimer(TIME_MAX_SYNC);
		}
		return 0;
	}

	public int execSQL(final String sql) {
		Holder h = new Holder();
		h.funcType = Holder.FUNC_EXEC;
		h.sql = sql;

		return add(h);
	}

	public int insert(final String primaryKey, final ContentValues values) {
		Holder h = new Holder();
		h.funcType = Holder.FUNC_INSERT;
		h.primaryKey = primaryKey;
		h.values = new ContentValues(values);

		return add(h);
	}

	public int update(final ContentValues values, final String whereClause, final String[] whereArgs) {
		Holder h = new Holder();
		h.funcType = Holder.FUNC_UPDATE;
		h.whereClause = whereClause;
		h.values = new ContentValues(values);
		h.copyWhereArgs(whereArgs);

		return add(h);
	}

	public int replace(final String primaryKey, final ContentValues values) {
		Holder h = new Holder();
		h.funcType = Holder.FUNC_REPLACE;
		h.primaryKey = primaryKey;
		h.values = new ContentValues(values);

		return add(h);
	}

	public int delete(final String whereClause, final String[] whereArgs) {
		Holder h = new Holder();
		h.funcType = Holder.FUNC_DELETE;
		h.whereClause = whereClause;
		h.copyWhereArgs(whereArgs);

		return add(h);
	}

	public static class Holder {

		public static final int FUNC_EXEC = 1;
		public static final int FUNC_INSERT = 2;
		public static final int FUNC_UPDATE = 3;
		public static final int FUNC_REPLACE = 4;
		public static final int FUNC_DELETE = 5;

		public int funcType;
		public String sql;
		public String primaryKey;
		public ContentValues values;
		public String whereClause;
		public String[] whereArgs;

		public void copyWhereArgs(String[] w) {
			if (w == null || w.length <= 0) {
				return;
			}
			whereArgs = new String[w.length];
			for (int i = 0; i < w.length; i++) {
				whereArgs[i] = new String(w[i]);
			}
		}
	}
}
