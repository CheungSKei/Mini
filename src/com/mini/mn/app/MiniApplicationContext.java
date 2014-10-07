package com.mini.mn.app;

import com.mini.mn.util.Log;

import android.content.Context;

public final class MiniApplicationContext {

	private static final String TAG = "Mini.MMApplicationContext";

	private static Context context = null;
	private static String pkgName = "com.tencent.mm";

	private MiniApplicationContext() {

	}

	public static void setContext(Context context) {
		MiniApplicationContext.context = context;
		pkgName = context.getPackageName();

		Log.d(TAG, "setup application context for package: " + pkgName);
	}

	public static Context getContext() {
		return context;
	}

	public static String getPackageName() {
		return pkgName;
	}

	public static String getDefaultPreferencePath() {
		return pkgName + "_preferences";
	}
}
