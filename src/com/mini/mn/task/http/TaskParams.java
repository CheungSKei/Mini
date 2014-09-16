package com.mini.mn.task.http;

import java.util.HashMap;

import com.mini.mn.util.DebugUtils;

/**
 * �������.
 * 
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class TaskParams {

	private HashMap<String, Object> mParams = null;
	
	public TaskParams() {
		mParams = new HashMap<String, Object>();
	}
	
	/**
	 * ��ȡֵ.
	 * 
	 * @param key ������
	 * @return
	 */
	public Object get(String key) {
		return mParams.get(key);
	}
	
	/**
	 * ��Ӳ�����.
	 * 
	 * @param key ������
	 * @param value ����ֵ
	 */
	public void put(String key, Object value) {
		mParams.put(key, value);
	}
	
	/**
	 * ����String���Ͳ���ֵ.
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		if (mParams.get(key) == null) {
			return "";
		}
		return mParams.get(key).toString();
	}
	
	/**
	 * ����Boolean���Ͳ���ֵ.
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		if (mParams.get(key) == null) {
			return false;
		}
		return "true".equalsIgnoreCase(mParams.get(key).toString());
	}
	
	/**
	 * ����Integer���Ͳ���ֵ.
	 * 
	 * @param key
	 * @return  �������NumberFormatException, �򷵻� -999. 
	 */
	public int getInt(String key) {
		String value = null;
		try {
			value = mParams.get(key).toString();
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			DebugUtils.error(e.getMessage(), e);
			return -999;
		}
	}
	
	/**
	 * ����Integer���Ͳ���ֵ. 
	 * 
	 * @param key
	 * @param callback Ĭ�Ϸ���ֵ
	 * @return
	 */
	public int getInt(String key, int callback) {
		String value = null;
		try {
			value = mParams.get(key).toString();
			return Integer.parseInt(value);
		} catch (Exception e) {
			DebugUtils.error(e.getMessage(), e);
			return callback;
		}
	}
	
	/**
	 * ����Double���Ͳ���ֵ.
	 * 
	 * @param key
	 * @return  �������NumberFormatException, �򷵻� -999. 
	 */
	public double getDouble(String key) {
		String value = null;
		try {
			value = mParams.get(key).toString();
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			DebugUtils.error(e.getMessage(), e);
			return -999;
		}
	}
	
	/**
	 * ����HashMap�б����
	 * @return
	 */
	public HashMap<String, Object> getHashMap(){
		return mParams;
	}
	
}
