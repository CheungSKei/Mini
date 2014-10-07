package com.mini.mn.platformtools;

import android.os.SystemClock;

public final class CodeInfo {

	private CodeInfo() {

	}

	public static int getLine() {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		return ste.getLineNumber();
	}

	public static StackTraceElement[] getStack() {
		return new Throwable().getStackTrace();
	}

	public static String getLong() {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		return ste.toString();
	}

	public static String getShort() {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		int index = ste.getMethodName().lastIndexOf('.');
		return ste.getMethodName().substring(index + 1) + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
	}

	public static String getLongClass() {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		return ste.getClassName();
	}

	public static String getShortClass() {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		String str = ste.getClassName();
		if (str == null) {
			return null;
		}
		String[] arr = str.split("\\.");
		if ((arr == null) || (arr.length < 2)) {
			return "";
		}
		return arr[arr.length - 1];
	}

	public static class TestTime {
		private long msBegin;

		public TestTime() {
			reset();
		}

		public void reset() {
			msBegin = getTime();
		}

		public long GetDiff() {
			return getTime() - msBegin;
		}

		public static long getTime() {
			return SystemClock.elapsedRealtime();
		}

		public static long DiffMS(long old, long now) {
			return now - old;
		}
	}
}
