package com.mini.mn.db.storage;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.mini.mn.db.ISQLiteDatabase;
import com.mini.mn.util.Log;

/**
 * content provider wrapper
 * <p/>
 * NOTE: DO NOT support rawQuery, execSQL, replace
 * 
 * @author kirozhao
 * @param <T>
 */
public abstract class ContentProviderDB<T> implements ISQLiteDatabase {

	private static final String TAG = "MicroMsg.SDK.MContentProviderDB";

	private final Context context;

	public ContentProviderDB(Context context) {
		this.context = context;
	}

	public abstract Uri getUriFromTable(final String table);

	@Override
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		final Uri uri = getUriFromTable(table);
		if (uri == null) {
			Log.e(TAG, "get uri from table failed, table=" + table);
			return new MatrixCursor(columns);
		}

		final Cursor cu = context.getContentResolver().query(uri, columns, selection, selectionArgs, orderBy);
		if (cu == null) {
			return new MatrixCursor(columns);
		}
		return cu;
	}

	@Override
	public Cursor rawQuery(String sql, String[] selectionArgs) {
		Log.e(TAG, "do not support, rawQuery sql=" + sql);
		return null;
	}

	@Override
	public boolean execSQL(String table , String sql) {
		Log.e(TAG, "do not support, execSQL sql=" + sql);
		return false;
	}

	@Override
	public long insert(String table, String primaryKey, ContentValues values) {
		final Uri uri = getUriFromTable(table);
		if (uri == null) {
			Log.e(TAG, "get uri from table failed, table=" + table);
			return -1;
		}

		// TODO
		Uri result = context.getContentResolver().insert(uri, values);
		return ContentUris.parseId(result);
	}
	
	@Override
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		final Uri uri = getUriFromTable(table);
		if (uri == null) {
			Log.e(TAG, "get uri from table failed, table=" + table);
			return 0;
		}
		return context.getContentResolver().update(uri, values, whereClause, whereArgs);
	}

	@Override
	public long replace(String table, String primaryKey, ContentValues values) {
		Log.e(TAG, "do not support, replace table=" + table);
		return 0;
	}

	@Override
	public int delete(String table, String whereClause, String[] whereArgs) {
		final Uri uri = getUriFromTable(table);
		if (uri == null) {
			Log.e(TAG, "get uri from table failed, table=" + table);
			return 0;
		}
		return context.getContentResolver().delete(uri, whereClause, whereArgs);
	}

}
