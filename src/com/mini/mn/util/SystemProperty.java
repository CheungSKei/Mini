package com.mini.mn.util;

import java.util.HashMap;

public final class SystemProperty {

	private static final HashMap<String, String> PROPERTIES = new HashMap<String, String>();

	private SystemProperty() {

	}

	public static String getProperty(final String key) {
		return PROPERTIES.get(key);
	}

	public static void setProperty(final String key, final String value) {
		PROPERTIES.put(key, value);
	}
}
