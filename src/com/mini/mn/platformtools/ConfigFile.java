package com.mini.mn.platformtools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.mini.mn.util.Log;

public class ConfigFile {
	private static final String TAG = "Mini.ReadConfig";

	private Properties propertie = null;
	private String filePath = null;

	public ConfigFile(String filePath) {
		propertie = new Properties();
		this.filePath = filePath;
		FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream(filePath);
			propertie.load(inputFile);
		} catch (Exception e) {
			Log.e(TAG, "Read File: %s Failed. [%s]", filePath, e.getLocalizedMessage());
			// e.printStackTrace();
		} finally {
			if (inputFile != null) {
				try {
					inputFile.close();
				} catch (IOException e) {
					Log.e(TAG, "Close File: %s Failed. [%s]", filePath, e.getLocalizedMessage());
				}
			}
		}
	}

	public boolean saveValue(String key, String val) {
		FileOutputStream outputFile = null;
		boolean isOk = false;
		try {
			outputFile = new FileOutputStream(filePath);
			propertie.setProperty(key, val);
			propertie.store(outputFile, "");
			isOk = true;
		} catch (Exception e) {
			Log.e(TAG, "Write File: %s Failed. [%s]", filePath, e.getLocalizedMessage());
			isOk = false;
		} finally {
			if (outputFile != null) {
				try {
					outputFile.close();
				} catch (IOException e) {
					Log.e(TAG, "Close File: %s Failed. [%s]", filePath, e.getLocalizedMessage());
				}
			}
		}
		return isOk;
	}

	public boolean saveValue(String key, long val) {
		return saveValue(key, String.valueOf(val));
	}

	public boolean saveValue(String key, int val) {
		return saveValue(key, String.valueOf(val));
	}

	public String getValue(String key) {
		if (propertie != null && propertie.containsKey(key)) {
			String value = propertie.getProperty(key);
			return value;
		}
		return null;
	}

	public Long getLongValue(String key) {
		String st = getValue(key);
		if (st == null) {
			return null;
		}
		Long ret = null;
		try {
			ret = Long.parseLong(st);
		} catch (Exception e) {
			Log.e(TAG, "getLongValue ParseLong : %s Failed. [%s]", st, e.getLocalizedMessage());
			return null;
		}
		return ret;
	}

	public Integer getIntegerValue(String key) {
		String st = getValue(key);
		if (st == null) {
			return null;
		}
		Integer ret = null;
		try {
			ret = Integer.parseInt(st);
		} catch (Exception e) {
			Log.e(TAG, "getIntegerValue ParseInteger : %s Failed. [%s]", st, e.getLocalizedMessage());
			return null;
		}
		return ret;
	}

	public static String getValue(String filePath, String key) {
		return new ConfigFile(filePath).getValue(key);
	}

	public static Long getLongValue(String filePath, String key) {
		return new ConfigFile(filePath).getLongValue(key);
	}

	public static Integer getIntegerValue(String filePath, String key) {
		return new ConfigFile(filePath).getIntegerValue(key);
	}

	public static long getLongValue(String filePath, String key, long def) {
		Long ret = new ConfigFile(filePath).getLongValue(key);
		if (ret == null) {
			return def;
		}
		return ret;
	}

	public static int getIntValue(String filePath, String key, int def) {
		Integer ret = new ConfigFile(filePath).getIntegerValue(key);
		if (ret == null) {
			return def;
		}
		return ret;
	}

	public static boolean saveValue(String filePath, String key, String val) {
		return new ConfigFile(filePath).saveValue(key, val);
	}

	public static boolean saveValue(String filePath, String key, long val) {
		return new ConfigFile(filePath).saveValue(key, val);
	}

	public static boolean saveValue(String filePath, String key, int val) {
		return new ConfigFile(filePath).saveValue(key, val);
	}
}
