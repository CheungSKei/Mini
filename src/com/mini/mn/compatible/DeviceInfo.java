package com.mini.mn.compatible;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import com.mini.mn.algorithm.MD5;
import com.mini.mn.app.MiniApplicationContext;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public final class DeviceInfo {
	private static final String TAG = "MiniMsg.DeviceInfo";

	
	public  static final int  NOTCONFIG = -1;
	public  static final int  ENABLE 	=  1;
	public  static final int  DISABLE	=  2;
	
	public static CpuInfo mCpuInfo = new CpuInfo();
	public static CameraInfo mCameraInfo = new CameraInfo();
	public static AudioInfo mAudioInfo = new AudioInfo();

	private static int mOldDeviceInfoHash = -1;
	public static CommonInfo mCommonInfo = new CommonInfo();

	private DeviceInfo() {

	}

	// return IMEI or fake-IMEI
	public static String getIMEI() {
		String imei = (String) CompatibleFileStorage.getConfigFileStg().get(CConstants.DEVICE_IMEI);
		if (imei != null) {
			return imei;
		}

		imei = getDeviceID(MiniApplicationContext.getContext());
		// NOTE: IMEI must be not empty
		if (imei == null) {
			imei = "1234567890ABCDEF";
		}

		CompatibleFileStorage.getConfigFileStg().set(CConstants.DEVICE_IMEI, imei);
		return imei;
	}

	/**
	 * get device imei
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceID(final Context context) {
		if (context == null) {
			return null;
		}

		try {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm == null) {
				return null;
			}
			final String deviceId = tm.getDeviceId();
			return deviceId == null ? null : deviceId.trim();

		} catch (final SecurityException e) {
			Log.e(TAG, "getDeviceId failed, security exception");

		} catch (final Exception ignore) {
			ignore.printStackTrace();
		}

		return null;
	}

	private static String mmguid = null;

	/**
	 * updated in 4.3, consider ANDROID_ID valid
	 * 
	 * @returnk
	 */
	public static String getMMGUID() {
		if (mmguid == null) {
			final StringBuilder sb = new StringBuilder();
			sb.append(Secure.getString(MiniApplicationContext.getContext().getContentResolver(), Secure.ANDROID_ID)); //
			sb.append(getMMGUIDImp()); // change storage
			sb.append(getHardWareId());
			mmguid = "A" + MD5.getMessageDigest(sb.toString().getBytes()).substring(0, 15);
			Log.w(TAG, "guid:%s", mmguid);
		}

		return mmguid;
	}

	public static String getHardWareId() {
		final String hid = android.os.Build.MANUFACTURER + android.os.Build.MODEL + CpuFeatures.getCpuId();
		Log.d(TAG, "getHardWareId " + hid);
		return hid;
	}

	public static String getLocalMacAddress(Context context) {

		String macAddress = null;
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			macAddress = info.getMacAddress();
		}
		return macAddress;
	}

	public static String getLocalBtMacAddress() {

		String macAddress = null;
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter != null) {
			macAddress = mBluetoothAdapter.getAddress();
		}
		return macAddress;
	}

	private static String getMMGUIDImp() {
		String deviceId = (String) CompatibleFileStorage.getConfigFileStg().get(CConstants.DEVICE_ID);
		if (deviceId != null) {
			return deviceId;
		}

		deviceId = generateDeviceId();
		CompatibleFileStorage.getConfigFileStg().set(CConstants.DEVICE_ID, deviceId);
		return deviceId;
	}

	private static String generateDeviceId() {
		String deviceId = Util.getDeviceId(MiniApplicationContext.getContext());

		final int cLen = 15;
		if (deviceId != null && deviceId.length() > 0) {
			deviceId = ("A" + deviceId + "123456789ABCDEF").substring(0, cLen);

		} else {
			final Random rand = new Random();
			rand.setSeed(System.currentTimeMillis());

			deviceId = "A";
			for (int i = 0; i < cLen; i++) {
				deviceId += (char) ('A' + rand.nextInt('Z' - 'A'));
			}
		}

		Log.w(TAG, "generated deviceId=" + deviceId);
		return deviceId;
	}

	public static void update(String deviceInfo) {
		if (deviceInfo == null || deviceInfo.length() <= 0) {
			return;
		}

		if (deviceInfo.hashCode() == mOldDeviceInfoHash) {
			return; // No changed
		}

		mOldDeviceInfoHash = deviceInfo.hashCode();

		mCpuInfo.reset();
		mCameraInfo.reset();
		mAudioInfo.reset();
		mCommonInfo.reset();
		DeviceInfoParser parser = new DeviceInfoParser();
		if (!parser.parse(deviceInfo, mCpuInfo, mCameraInfo, mAudioInfo, mCommonInfo)) {
			return;
		}
	}

	// get RAM Size
	public static short getTotalRamSize(Context context) {
		String str1 = "/proc/meminfo";
		String str2;
		String[] arrayOfString;
		long initialMemory = 0L;

		BufferedReader localBufferedReader = null;
		try {
			FileReader fr = new FileReader(str1);
			localBufferedReader = new BufferedReader(fr, 8192);
			str2 = Util.nullAsNil(localBufferedReader.readLine());
			arrayOfString = str2.split("\\s+");
			initialMemory = Integer.valueOf(arrayOfString[1]).intValue();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (localBufferedReader != null) {
				try {
					localBufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return (short) (initialMemory / 1024);
	}

	// 获取设备名
	public static String getPhoneModel() {
		return android.os.Build.MODEL;
	}

	// 获取运营商
	public static String getMobileSPType(Context context) {
		TelephonyManager iPhoneManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return iPhoneManager.getSimOperatorName();
	}

	// 获取cpu信息 返回二维数据第一维是cpu型号，第二维是cpu主频，主频有时候取不准，不过最主要的型号取对了就可以了
	public static String[] getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "0" };
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = Util.nullAsNil(localBufferedReader.readLine());
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = Util.nullAsNil(localBufferedReader.readLine());
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] = arrayOfString[2];
			localBufferedReader.close();

		} catch (IOException e) {
		}
		return cpuInfo;
	}

}
