package com.mini.mn.util;

import com.mini.mn.BuildConfig;

import android.util.Log;

/**
 * ������Ϣ������.
 * 
 * @version 1.0.0
 * @date 2013-1-29
 * @author S.Kei.Cheung
 */
public class DebugUtils {
	
	private static final String DEBUG_TAG = "ZhiYin";
	
	private DebugUtils() {}

	/**
	 * ��ӡ������Ϣ.
	 * 
	 * @param msg
	 */
	public static void debug(String msg) {
		debug(DEBUG_TAG, msg);
	}

	/**
	 * ��ӡ������Ϣ.
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void debug(String tag,String msg,Object[] args) {
		debug(DEBUG_TAG, String.format(msg,args));
	}
	
	/**
	 * ��ӡ������Ϣ.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void debug(String tag,String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, msg);
		}
	}

	/**
	 * ��ӡ������Ϣ.
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void warn(String tag,String msg,Object[] args) {
		warn(DEBUG_TAG, String.format(msg,args));
	}
	
	/**
	 * ��ӡ������Ϣ.
	 * 
	 * @param msg
	 */
	public static void warn(String msg) {
		warn(DEBUG_TAG, msg);
	}
	
	/**
	 * ��ӡ������Ϣ.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void warn(String tag,String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(tag, msg);
		}
	}
	
	/**
	 * ��ӡ��ʾ��Ϣ.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void info(String tag,String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(tag, msg);
		}
	}
	
	/**
	 * ��ӡ��ʾ��Ϣ.
	 * 
	 * @param msg
	 */
	public static void info(String msg) {
		info(DEBUG_TAG, msg);
	}

	/**
	 * ��ӡ��ʾ��Ϣ.
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void info(String tag,String msg,Object[] args) {
		info(tag, String.format(msg,args));
	}
	
	/**
	 * ��ӡ������Ϣ(��ɫ������ʾwarn).
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void error(String tag,String msg, Exception e) {
		if (BuildConfig.DEBUG) {
			Log.w(tag, msg, e);
		}
	}
	
	/**
	 * ��ӡ������Ϣ(��ɫ������ʾwarn).
	 * 
	 * @param msg
	 */
	public static void error(String msg, Exception e) {
		error(DEBUG_TAG, msg, e);
	}
	
	/**
	 * ��ӡ������Ϣ(��ɫ������ʾwarn).
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void error(String tag,String msg,Object[] args, Exception e) {
		error(tag, String.format(msg,args), e);
	}
	
	/**
	 * ��ӡ������Ϣ(��ɫ������ʾerror).
	 * 
	 * @param tag
	 * @param msg
	 * @param args
	 */
	public static void error(String tag,String msg,Object[] args) {
		Log.e(tag, String.format(msg,args));
	}
	
}
