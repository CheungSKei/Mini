package com.mini.mn.platformtools;

import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import android.os.StrictMode;

public class StrictModeHelper {

	private static final String TAG = "MicroMsg.SDK.StrictModeHelper";

	private static final ThreadLocal<IStrictModeHelper> TLS = new ThreadLocal<IStrictModeHelper>();

	private interface IStrictModeHelper {

		void resumeWriteCheck();

		void pauseWriteCheck(String tag);

	}

	private StrictModeHelper() {

	}

	public static void enableStrictMode(boolean on) {
		return ;
//		Log.w(TAG, "enable strict mode: %b, sdk: %d", on, android.os.Build.VERSION.SDK_INT);
//		if (!on || android.os.Build.VERSION.SDK_INT < 9) {
//			return;
//		}
//
//		TLS.set(new Impl23());
	}

	public static void pauseWriteCheck(final String tag) {

		final IStrictModeHelper helper = TLS.get();

		if (helper == null) {
			// Log.d(TAG, "pause write check failed");
			return;
		}

		helper.pauseWriteCheck(tag);
	}

	public static void resumeWriteCheck() {

		final IStrictModeHelper helper = TLS.get();
		if (helper == null) {
			// Log.d(TAG, "resume write check failed");
			return;
		}

		helper.resumeWriteCheck();
	}

	static class Impl23 implements IStrictModeHelper {

		private StrictMode.ThreadPolicy tp = StrictMode.getThreadPolicy();

		private long pauseWriteTS;
		private String pauseWriteTag;

		public Impl23() {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskWrites().detectDiskReads().detectNetwork().penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
		}

		@Override
		public void resumeWriteCheck() {
			StrictMode.setThreadPolicy(tp);
			Log.d(TAG, "%s, cost=%d", pauseWriteTag, Util.ticksToNow(pauseWriteTS));
		}

		@Override
		public void pauseWriteCheck(final String tag) {
			tp = StrictMode.allowThreadDiskWrites();
			pauseWriteTS = Util.currentTicks();
			pauseWriteTag = tag;
		}

	}
}
