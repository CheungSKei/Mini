package com.mini.mn.db;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * 数据库操作接口
 * 
 * @version 1.0.0
 * @date 2014-10-08
 * @author S.Kei.Cheung
 */
public interface ISQLiteDatabase {
	/** 查询 按参数方法区分query和rawQuery*/
	public Cursor query(final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy);
	/** 查询 */
	public Cursor rawQuery(final String sql, final String[] selectionArgs);
	/** 执行删增更改 */
	public boolean execSQL(final String table , final String sql);
	/** 插入 */
	public long insert(final String table, final String primaryKey, final ContentValues values);
	/** 更新 */
	public int update(final String table, final ContentValues values, final String whereClause, final String[] whereArgs);
	/** 替换 */
	public long replace(final String table, final String primaryKey, final ContentValues values);
	/** 删除 */
	public int delete(final String table, final String whereClause, final String[] whereArgs);
}
