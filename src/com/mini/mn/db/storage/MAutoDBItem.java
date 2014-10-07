package com.mini.mn.db.storage;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * auto database record container using reflect mechanism<br />
 * mapping all public fields starts with {@linkplain MAutoDBItem.FIELD_PREFIX} to database table columns<br />
 * <b>for example</b>, class TestItem as follow
 * 
 * <pre>
 * public class TestItem extends MAutoDBItem {
 * 	public int field_id;
 * 	public String field_name;
 * 
 * 	private int other;
 * }
 * </pre>
 * 
 * can be persist to table with columns {@code test(id, name)} of {@linkplain MAutoStorage}, private field {@code other} will not be mapped
 * 
 * <p />
 * acceptable type for fields:
 * <p />
 * boolean <==> INT(0/1) <br />
 * int/Integer <==> INT <br/>
 * long/Long <==> LONG <br/>
 * short/Short <==> SHORT <br/>
 * float/Float <==> FLOAT <br/>
 * double/Double <==> DOUBLE <br/>
 * String <==> TEXT <br/>
 * byte[] <==> BLOB <br/>
 * <br/>
 * fields: must be public<br/>
 * public String field_name;<br/>
 * public String field_version; <br/>
 * public String field_key;<br/>
 * 
 * @author kirozhao
 */
@Deprecated
public abstract class MAutoDBItem extends IAutoDBItem {
	private static final String TAG = "MicroMsg.SDK.MAutoDBItem";

	@Override
	public void convertFrom(final Cursor cu) {
		// added in 4.5 by kirozhao
		// try to make faster
		
		final String[] names = cu.getColumnNames();
		if (names == null) {
			Log.e(TAG, "convertFrom: get column names failed");
			return;
		}

		HashMap<String, Integer> idx = new HashMap<String, Integer>();
		for (int i = 0; i < names.length; i++) {
			idx.put(names[i], i);
		}
		

		for (final Field f : getDBInfo().fields) {
			final String col = getColName(f);
			if (Util.isNullOrNil(col)) {
				continue;
			}

	
			// find in cursor
			int index = Util.nullAs(idx.get(col), -1);
			if (index < 0) {
				// not founded
				continue;
			}
			

			try {
				CursorFieldHelper.setter(f.getType()).invoke(f, this, cu, index);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		int rowidIndex = Util.nullAs(idx.get(SYSTEM_ROWID_FIELD), -1);
		if (rowidIndex >= 0) {
			systemRowid = cu.getLong(rowidIndex);
		}
	}

	@Override
	public ContentValues convertTo() {
		final ContentValues values = new ContentValues();
		for (final Field f : getDBInfo().fields) {

			try {
				CursorFieldHelper.getter(f.getType()).invoke(f, this, values);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (systemRowid > 0) {
			values.put(SYSTEM_ROWID_FIELD, systemRowid);
		}
		return values;
	}

}

/**
 * helper class for set to/get from database by column names
 * 
 * @author kirozhao
 * 
 */
class CursorFieldHelper {

	public interface ISetMethod {
		void invoke(Field f, Object obj, Cursor cu, int index);
	}

	public interface IGetMethod {
		void invoke(Field f, Object obj, ContentValues values);
	}

	private static final Map<Class<?>, ISetMethod> SET_METHODS = new HashMap<Class<?>, ISetMethod>();
	private static final Map<Class<?>, IGetMethod> GET_METHODS = new HashMap<Class<?>, IGetMethod>();
	private static final Map<Class<?>, String> GET_TYPE = new HashMap<Class<?>, String>();
	static {
		try {
			//
			SET_METHODS.put(byte[].class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setBlob(f, obj, cu, index);
				}

			});

			SET_METHODS.put(short.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setShort(f, obj, cu, index);
				}

			});
			SET_METHODS.put(Short.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setShort(f, obj, cu, index);
				}

			});

			SET_METHODS.put(boolean.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setBoolean(f, obj, cu, index);
				}

			});
			SET_METHODS.put(Boolean.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setBoolean(f, obj, cu, index);
				}

			});

			SET_METHODS.put(int.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setInt(f, obj, cu, index);
				}

			});
			SET_METHODS.put(Integer.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setInt(f, obj, cu, index);
				}

			});

			SET_METHODS.put(float.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setFloat(f, obj, cu, index);
				}

			});
			SET_METHODS.put(Float.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setFloat(f, obj, cu, index);
				}

			});

			SET_METHODS.put(double.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setDouble(f, obj, cu, index);
				}

			});
			SET_METHODS.put(Double.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setDouble(f, obj, cu, index);
				}

			});

			SET_METHODS.put(long.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setLong(f, obj, cu, index);
				}

			});
			SET_METHODS.put(Long.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setLong(f, obj, cu, index);
				}

			});

			SET_METHODS.put(String.class, new ISetMethod() {

				@Override
				public void invoke(Field f, Object obj, Cursor cu, int index) {
					keep_setString(f, obj, cu, index);
				}

			});

			//
			GET_METHODS.put(byte[].class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getBlob(f, obj, values);
				}

			});

			GET_METHODS.put(short.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getShort(f, obj, values);
				}

			});
			GET_METHODS.put(Short.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getShort(f, obj, values);
				}

			});

			GET_METHODS.put(boolean.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getBoolean(f, obj, values);
				}

			});
			GET_METHODS.put(Boolean.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getBoolean(f, obj, values);
				}

			});

			GET_METHODS.put(int.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getInt(f, obj, values);
				}

			});
			GET_METHODS.put(Integer.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getInt(f, obj, values);
				}

			});

			GET_METHODS.put(float.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getFloat(f, obj, values);
				}

			});
			GET_METHODS.put(Float.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getFloat(f, obj, values);
				}

			});

			GET_METHODS.put(double.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getDouble(f, obj, values);
				}

			});
			GET_METHODS.put(Double.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getDouble(f, obj, values);
				}

			});

			GET_METHODS.put(long.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getLong(f, obj, values);
				}

			});
			GET_METHODS.put(Long.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getLong(f, obj, values);
				}

			});

			GET_METHODS.put(String.class, new IGetMethod() {

				@Override
				public void invoke(Field f, Object obj, ContentValues values) {
					keep_getString(f, obj, values);
				}

			});

			//
			GET_TYPE.put(byte[].class, "BLOB");

			GET_TYPE.put(short.class, "SHORT");
			GET_TYPE.put(Short.class, "SHORT");

			GET_TYPE.put(boolean.class, "INTEGER");
			GET_TYPE.put(Boolean.class, "INTEGER");

			GET_TYPE.put(int.class, "INTEGER");
			GET_TYPE.put(Integer.class, "INTEGER");

			GET_TYPE.put(float.class, "FLOAT");
			GET_TYPE.put(Float.class, "FLOAT");

			GET_TYPE.put(double.class, "DOUBLE");
			GET_TYPE.put(Double.class, "DOUBLE");

			GET_TYPE.put(long.class, "LONG");
			GET_TYPE.put(Long.class, "LONG");

			GET_TYPE.put(String.class, "TEXT");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String type(Class<?> type) {
		return GET_TYPE.get(type);
	}

	public static IGetMethod getter(Class<?> type) {
		return GET_METHODS.get(type);
	}

	public static ISetMethod setter(Class<?> type) {
		return SET_METHODS.get(type);
	}

	public static void keep_setBlob(final Field f, final Object object, final Cursor cu, final int columnIndex) {
		try {
			f.set(object, cu.getBlob(columnIndex));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_getBlob(final Field f, final Object object, final ContentValues values) {
		try {
			values.put(MAutoDBItem.getColName(f), (byte[]) f.get(object));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_setShort(final Field f, final Object object, final Cursor cu, final int columnIndex) {
		try {
			if (f.getType().equals(short.class)) {
				f.setShort(object, cu.getShort(columnIndex));

			} else {
				f.set(object, cu.getShort(columnIndex));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_getShort(final Field f, final Object object, final ContentValues values) {
		try {
			values.put(MAutoDBItem.getColName(f), f.getShort(object));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_setBoolean(final Field f, final Object object, final Cursor cu, final int columnIndex) {
		try {
			if (f.getType().equals(boolean.class)) {
				f.setBoolean(object, cu.getInt(columnIndex) != 0);

			} else {
				f.set(object, cu.getInt(columnIndex));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_getBoolean(final Field f, final Object object, final ContentValues values) {
		try {
			values.put(MAutoDBItem.getColName(f), f.getBoolean(object) ? 1 : 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_setInt(final Field f, final Object object, final Cursor cu, final int columnIndex) {
		try {
			if (f.getType().equals(int.class)) {
				f.setInt(object, cu.getInt(columnIndex));
			} else {
				f.set(object, cu.getInt(columnIndex));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_getInt(final Field f, final Object object, final ContentValues values) {
		try {
			if (!f.getType().equals(int.class)) {
				values.put(MAutoDBItem.getColName(f), (Integer) f.get(object));

			} else {
				values.put(MAutoDBItem.getColName(f), f.getInt(object));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_setFloat(final Field f, final Object object, final Cursor cu, final int columnIndex) {
		try {
			if (f.getType().equals(float.class)) {
				f.setFloat(object, cu.getFloat(columnIndex));

			} else {
				f.set(object, cu.getFloat(columnIndex));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_getFloat(final Field f, final Object object, final ContentValues values) {
		try {
			if (!f.getType().equals(float.class)) {
				values.put(MAutoDBItem.getColName(f), (Float) f.get(object));

			} else {
				values.put(MAutoDBItem.getColName(f), f.getFloat(object));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_setDouble(final Field f, final Object object, final Cursor cu, final int columnIndex) {
		try {
			if (f.getType().equals(double.class)) {
				f.setDouble(object, cu.getDouble(columnIndex));

			} else {
				f.set(object, cu.getDouble(columnIndex));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_getDouble(final Field f, final Object object, final ContentValues values) {
		try {
			if (!f.getType().equals(double.class)) {
				values.put(MAutoDBItem.getColName(f), (Double) f.get(object));

			} else {
				values.put(MAutoDBItem.getColName(f), f.getDouble(object));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_setLong(final Field f, final Object object, final Cursor cu, final int columnIndex) {
		try {
			if (f.getType().equals(long.class)) {
				f.setLong(object, cu.getLong(columnIndex));

			} else {
				f.set(object, cu.getLong(columnIndex));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_getLong(final Field f, final Object object, final ContentValues values) {
		try {
			if (!f.getType().equals(long.class)) {
				values.put(MAutoDBItem.getColName(f), (Long) f.get(object));

			} else {
				values.put(MAutoDBItem.getColName(f), f.getLong(object));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_setString(final Field f, final Object object, final Cursor cu, final int columnIndex) {
		try {
			f.set(object, cu.getString(columnIndex));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void keep_getString(final Field f, final Object object, final ContentValues values) {
		try {
			values.put(MAutoDBItem.getColName(f), (String) f.get(object));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
