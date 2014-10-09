package com.mini.mn.db;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * ���ݿ�����ӿ�
 * 
 * @version 1.0.0
 * @date 2014-10-08
 * @author S.Kei.Cheung
 */
public interface ISQLiteDatabase {
	/** ��ѯ ��������������query��rawQuery*/
	public Cursor query(final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy);
	/** ��ѯ */
	public Cursor rawQuery(final String sql, final String[] selectionArgs);
	/** ִ��ɾ������ */
	public boolean execSQL(final String table , final String sql);
	/** ���� */
	public long insert(final String table, final String primaryKey, final ContentValues values);
	/** ���� */
	public int update(final String table, final ContentValues values, final String whereClause, final String[] whereArgs);
	/** �滻 */
	public long replace(final String table, final String primaryKey, final ContentValues values);
	/** ɾ�� */
	public int delete(final String table, final String whereClause, final String[] whereArgs);
}
