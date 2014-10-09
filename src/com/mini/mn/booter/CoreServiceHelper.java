package com.mini.mn.booter;

import com.mini.mn.app.MiniApplication;
import com.mini.mn.util.Log;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * running in com.tencent.mm
 * 
 * @author kirozhao
 */
public final class CoreServiceHelper {
	private static final String TAG = "MicroMsg.CoreServiceHelper";

	public static final String TYPE_CONNECTION = "connection";
	public static final String TYPE_AUTOSTART = "auto";
	public static final String TYPE_NOOP = "noop";
	public static final String TYPE_ALARM = "alarm";
	public static final String TYPE_MEDIA_EJECT = "mediaEject";

	private CoreServiceHelper() {

	}

	public static boolean ensureServiceInstance(final Context context, final String type) {
		if (!type.equals(TYPE_NOOP)) {
			final SharedPreferences sp = context.getSharedPreferences(MiniApplication.getDefaultPreferencePath(), 0);
			if (sp.getBoolean("settings_fully_exit", true)) {
				Log.i(TAG, "fully exited, no need to start service");
				return false;
			}
		}

		Log.d(TAG, "ensure service running, type=" + type);
		final Intent boot = new Intent(context, CoreService.class);
		boot.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(boot);
		return true;
	}

	public static void stopServiceInstance(final Context context) {
		final Intent boot = new Intent(context, CoreService.class);
		context.stopService(boot);
	}
}
