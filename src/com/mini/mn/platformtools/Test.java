package com.mini.mn.platformtools;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

public final class Test {
	private static final String TAG = "MicroMsg.Test";

	public static boolean displayErrCode = false;
	public static boolean displayMsgState = false;
	public static boolean simulateNetworkFault = false;
	public static boolean forceTouchMode = false;
	public static boolean outputToSdCardlog = false;
	public static boolean crashIsExit = false;
	// public static boolean showShakeLast = true; // change in 3.5.1
	public static boolean checkPushNotification = false; // added in 4.1

	public static boolean dropAlbumTable = false;
	public static boolean dropAlbumFile = false;

	public static boolean isAlbumShowInfo = false;

	public static boolean isLocationHelp = false;
	public static String locationInfo = "";
	public static boolean isSosoOpen = false;

	public static boolean simulatePostServerError = false;
	public static boolean simulateUploadServerError = false;
	public static boolean thumbOnlyInMemery = false;
	public static int cdnDownLoadThread = 0;
	public static boolean snsXmlPrint = false;
	public static boolean filterFPNP = false;
	public static boolean testForPull = false;

	public static String testHostHttp = "";
	public static String testHostSocket = "";
	public static boolean skipGetDNS = false;

	public static boolean isMMbakToSdcard = false;
	public static boolean isUpdateTest = false;
	public static boolean simulateDownFault = false;

	public static int dataTransferTimes = 0;
	public static int dataTransferDuration = 0;

	public static boolean forceCDNTrans = false; // added in 4.5 for cdn transport

	public static String jsapiPermission = null;
	
	public static int TestForDKKey = 0;
	public static int TestForDKVal = 0;
	
	
	private Test() {

	}

	public static boolean simulateJniDecodeTestCase() {
		if (!simulateNetworkFault) {
			return false;
		}

		Random rand = new Random(System.currentTimeMillis());
		if (rand.nextInt(100) > 80) {
			Log.f(TAG, "[TEST] session timeout");
			return true;
		}
		return false;
	}

	public static boolean simulateNetworkFaultSessionTimeOut() {
		if (!simulateNetworkFault) {
			return false;
		}

		Random rand = new Random(System.currentTimeMillis());
		if (rand.nextInt(100) <= 30) {
			return false;
		}

		Log.f(TAG, "[TEST] simulate no response");

		try {
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static boolean simulateNetworkFaultConnectTimeOut() {
		if (!simulateNetworkFault) {
			return false;
		}

		Random rand = new Random(System.currentTimeMillis());
		if (rand.nextInt(100) <= 90) {
			return false;
		}

		Log.f(TAG, "[TEST] simulate connect timeout");
		return true;
	}

	public static boolean simulateNetworkFaultReadFailed() {
		if (!simulateNetworkFault) {
			return false;
		}

		Random rand = new Random(System.currentTimeMillis());
		if (rand.nextInt(100) <= 99) {
			return false;
		}

		Log.f(TAG, "[TEST] simulate read failed");
		return true;
	}

	public static boolean simulateNetworkFaultWriteFailed() {
		if (!simulateNetworkFault) {
			return false;
		}

		Random rand = new Random(System.currentTimeMillis());
		if (rand.nextInt(100) <= 99) {
			return false;
		}

		Log.f(TAG, "[TEST] simulate write failed");
		return true;
	}

	/**
	 * TEST: simulate ret value
	 * 
	 */
	private static HashMap<Integer, ConcurrentLinkedQueue<Integer>> nextRet = new HashMap<Integer, ConcurrentLinkedQueue<Integer>>();

	public static void pushNextErrorRet(final int type, final int ret) {
		synchronized (nextRet) {
			ConcurrentLinkedQueue<Integer> queue = nextRet.get(type);
			if (queue == null) {
				queue = new ConcurrentLinkedQueue<Integer>();
				nextRet.put(type, queue);
			}
			queue.add(ret);
		}
	}

	public static int simulateNextErrorRet(final int type) {
		synchronized (nextRet) {
			final ConcurrentLinkedQueue<Integer> queue = nextRet.get(type);
			if (queue == null) {
				return 0;
			}
			return Util.nullAsNil(queue.poll());
		}
	}
	
	

	public final static int DK_TEST_KEY_SESSION_TIMEOUT = 10001;
	public final static int DK_TEST_KEY_REMOVE_RSA_BEFORE_AUTH = 10002;
	public final static int DK_TEST_KEY_GETCERT_FAILED = 10003;
	
}
