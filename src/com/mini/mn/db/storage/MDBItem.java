package com.mini.mn.db.storage;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * interface of database record item
 * <p />
 * 
 * a MDBItem can be easily transformed from database record and prepared for {@link ISQLiteDatabase} to insert, delete, query, update
 * <p />
 * 
 * see also {@link MAutoDBItem}, {@link MAutoStorage}
 * 
 * @author kirozhao
 * 
 */
public interface MDBItem {

	/**
	 * get item from abstract database cursor
	 * 
	 * @param cu
	 *            input cursor
	 */
	void convertFrom(final Cursor cu);

	/**
	 * convert java object to named content values
	 * 
	 * @return named content values
	 */
	ContentValues convertTo();
}
