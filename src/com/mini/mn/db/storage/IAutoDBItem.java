package com.mini.mn.db.storage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import junit.framework.Assert;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;

public abstract class IAutoDBItem implements MDBItem {
	private static final String TAG = "MicroMsg.SDK.IAutoDBItem";
	
    public static final String FIELD_PREFIX = "field_";
    public static final String SYSTEM_ROWID_FIELD = "rowid";
    public static final String COL_ROWID = "rowid";
    
    public static class MAutoDBInfo {
        public Field[] fields;
        public String primaryKey;
        public String[] columns;
        public Map<String, String> colsMap = new HashMap<String, String>();
        public String sql;
    }
    public long systemRowid = -1;
    
    protected abstract MAutoDBInfo getDBInfo();
    
    @Override
    public abstract void convertFrom(final Cursor cu);
   
    @Override
    public abstract ContentValues convertTo();
    
	public static MAutoDBInfo initAutoDBInfo(final Class<?> clazz) {
		MAutoDBInfo info = new MAutoDBInfo();
		
		final List<Field> all = new LinkedList<Field>();
		for (final Field f : clazz.getDeclaredFields()) {
			final int modifiers = f.getModifiers();
			final String name = f.getName();

			if (name == null || !Modifier.isPublic(modifiers) || Modifier.isFinal(modifiers)) {
				continue;
			}

			String colName = name.startsWith(FIELD_PREFIX) ? name.substring(FIELD_PREFIX.length()) : name;
			if (f.isAnnotationPresent(MAutoDBFieldAnnotation.class)) {
				if (((MAutoDBFieldAnnotation) (f.getAnnotation(MAutoDBFieldAnnotation.class))).primaryKey() == 1) {
					info.primaryKey = colName;
				}

			} else if (!name.startsWith(FIELD_PREFIX)) {
				continue;
			}

			if (Util.isNullOrNil(colName)) {
				continue;
			}

			if (colName.equals(SYSTEM_ROWID_FIELD)) {
				Assert.assertTrue("field_rowid reserved by MAutoDBItem, change now!", false);
			}
			all.add(f);
		}
		info.fields = all.toArray(new Field[0]);
		info.columns = getFullColumns(info.fields);
		
		info.colsMap = getColsMap(info.fields);
		info.sql = getSql(info.fields);
		
		return info;
	}
	
	/**
	 * get all column names for certain fields
	 * 
	 * @param fields
	 *            fields to generated names
	 * @return all names starts without {@linkplain FIELD_PREFIX}
	 */
	private static String[] getFullColumns(final Field[] fields) {
		final String[] columns = new String[fields.length + 1];
		for (int i = 0; i < fields.length; i++) {
			columns[i] = getColName(fields[i]);
			Assert.assertTrue("getFullColumns failed:" + fields[i].getName(), !Util.isNullOrNil(columns[i]));
		}
		columns[fields.length] = SYSTEM_ROWID_FIELD;
		
		return columns;
	}

	private static Map<String, String> getColsMap(final Field[] fields) {
		final Map<String, String> cols = new HashMap<String, String>();

		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			String type = CursorFieldHelper.type(f.getType());
			if (type == null) {
				Log.e(TAG, "failed identify on column: " + f.getName() + ", skipped");
				continue;
			}

			final String col = getColName(f);
			if (Util.isNullOrNil(col)) {
				continue;
			}

			cols.put(col, type);
		}

		return cols;
	}
	
	private static String getSql(final Field[] fields) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			String type = CursorFieldHelper.type(f.getType());
			if (type == null) {
				Log.e(TAG, "failed identify on column: " + f.getName() + ", skipped");
				continue;
			}

			final String col = getColName(f);
			if (Util.isNullOrNil(col)) {
				continue;
			}

			String defVal = "";
			int isPrimaryKey = 0;
			if (f.isAnnotationPresent(MAutoDBFieldAnnotation.class)) {
				defVal = " default '" + ((MAutoDBFieldAnnotation) (f.getAnnotation(MAutoDBFieldAnnotation.class))).defValue() + "' ";
				isPrimaryKey = ((MAutoDBFieldAnnotation) (f.getAnnotation(MAutoDBFieldAnnotation.class))).primaryKey();
			}

			if (sb != null) {
				sb.append(col + " " + type + defVal + ((isPrimaryKey == 1) ? " PRIMARY KEY " : ""));
				sb.append((i == fields.length - 1) ? "" : ", ");
			}
		}

		return sb.toString();
	}
	
	public static String getColName(Field f) {
		if (f == null) {
			return null;
		}

		final String name = f.getName();
		if (name == null || name.length() <= 0) {
			return null;
		}

		if (name.startsWith(FIELD_PREFIX)) {
			return name.substring(FIELD_PREFIX.length());
		}

		return name;
	} 

	public static Field[] getValidFields(final Class<?> clazz) {
		final MAutoDBInfo info = initAutoDBInfo(clazz);
		return info.fields;
	}
    
    private static boolean checkBlobEqual(final byte[] a, final byte[] b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null && b != null) {
            return false;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
    public static boolean checkIOEqual(ContentValues cv, Cursor cu) {
        if (cv == null) {
            return cu == null;
        }
        if (cu == null || cu.getCount() != 1) {
            return false;
        }
        cu.moveToFirst();
        int cuCnt = cu.getColumnCount();
        int cvCnt = cv.size();
        if (cv.containsKey(SYSTEM_ROWID_FIELD)) {
            cvCnt -= 1;
        }
        if (cu.getColumnIndex(SYSTEM_ROWID_FIELD) != -1) {
            cuCnt -= 1;
        }
        if (cvCnt != cuCnt) {
            return false;
        }
        try {
            for (final Entry<String, Object> e : cv.valueSet()) {
                final String k = e.getKey();
                if (k.equals(SYSTEM_ROWID_FIELD)) {
                    continue;
                }
                int idx = cu.getColumnIndex(k);
                if (idx == -1) {
                    return false;
                }
                if (cv.get(k) instanceof byte[]) {
                    byte[] bcv = (byte[]) cv.get(k);
                    byte[] bcu = cu.getBlob(idx);
                    if (!checkBlobEqual(bcv, bcu)) {
                        return false;
                    }
                } else {
                    if (cu.getString(idx) == null) {
                        if (cv.get(k) != null) {
                            return false;
                        }
                    }
                    if (cv.get(k) == null) {
                        return false;
                    }
                    if (!cv.get(k).toString().equals(cu.getString(idx))) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

	/**
	 * static method to get a cursor with only one row data
	 * 
	 * @param cv
	 *            content values of this record
	 * @param projection
	 *            column names
	 * @return cursor with one record of content values
	 */
	public static Cursor getCursorForProjection(final ContentValues cv, final String[] projection) {
		final Object[] ret = new Object[projection.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = cv.get(projection[i]);
		}

		final MatrixCursor cu = new MatrixCursor(projection);
		cu.addRow(ret);
		return cu;
	}
}