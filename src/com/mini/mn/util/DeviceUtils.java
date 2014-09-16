package com.mini.mn.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import com.mini.mn.app.MiniApplication;
import com.mini.mn.constant.Constants;
import com.mini.mn.constant.NetworkType;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * �ֻ��豸��ع�����.
 * 
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class DeviceUtils {
	
	public static final int NETWORK_NONE=-1;
	public static final int NETWORK_WIFI=0;
	public static final int NETWORK_UNINET=1;
	public static final int NETWORK_UNIWAP=2;
	public static final int NETWORK_WAP_3G=3;
	public static final int NETWORK_NET_3G=4;
	public static final int NETWORK_CMWAP=5;
	public static final int NETWORK_CMNET=6;
	public static final int NETWORK_CTWAP=7;
	public static final int NETWORK_CTNET=8;
	public static final int NETWORK_MOBILE=9;
	
	private DeviceUtils() {}

	/**
	 * �ж��Ƿ������ӵ�����.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * ��������������.
	 * 
	 * @param context
	 * @return NetworkType.NONE: ����������<br>
	 * NetworkType.WIFI: ͨ��WIFI��������<br>
	 * NetworkType.CMWAP: ͨ���ƶ�, ��ͨGPRS��������<br>
	 * NetworkType.CTWAP: ͨ������GPRS��������<br>
	 */
	public static int checkNetWorkType(Context context) {
		if (isAirplaneModeOn(context)) {
			return NetworkType.NONE;
		}

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState() == NetworkInfo.State.CONNECTED) {
			return NetworkType.NET;
		}

		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == NetworkInfo.State.CONNECTED) {
			String type = connectivityManager.getActiveNetworkInfo()
					.getExtraInfo();
			if (!StringUtils.isEmpty(type) && type.toLowerCase().indexOf("wap") >= 0) {
				if (type.toLowerCase().indexOf("ctwap") >= 0) {
					return NetworkType.CTWAP;
				} else {
					return NetworkType.CMWAP;
				}
			} else {
				return NetworkType.NET;
			}
		}

		return NetworkType.NONE;
	}
	
	/**
	 * ��������������.
	 * 
	 * @param context
	 * @return NETWORK_NONE: ����������<br>
	 * NETWORK_WIFI: ͨ��WIFI��������<br>
	 * NETWORK_CMWAP: ͨ���ƶ�, ��ͨwap/GPRS��������<br>
	 * NETWORK_WAP_3G: ͨ��3G��������<br>
	 * NETWORK_NET_3G: ͨ��3G��������<br>
	 * NETWORK_CMWAP: ͨ���ƶ�, ��ͨwap/GPRS��������<br>
	 * NETWORK_CTWAP: ͨ������wap/GPRS��������<br>
	 * NETWORK_CMNET: ͨ���ƶ�, ��ͨGPRS��������<br>
	 * NETWORK_CTNET: ͨ������GPRS��������<br>
	 */
	public static int checkNetworkTypeAll(Context context){
		//����ģʽ
		if (isAirplaneModeOn(context)) {
			return NETWORK_NONE;
		}
		
		ConnectivityManager mag = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mag.getActiveNetworkInfo();
		//û������
		if(info==null){
			return NETWORK_NONE;
		}
		
		if(info.getTypeName().equals("WIFI")){
			return NETWORK_WIFI;
		}else{
			if(info.getExtraInfo().equals("uninet"))
			 return NETWORK_UNINET;
			if(info.getExtraInfo().equals("uniwap"))
			 return NETWORK_UNIWAP;
			if(info.getExtraInfo().equals("3gwap"))
			 return NETWORK_WAP_3G;
			if(info.getExtraInfo().equals("3gnet"))
			 return NETWORK_NET_3G;
			if(info.getExtraInfo().equals("cmwap"))
			 return NETWORK_CMWAP;
			if(info.getExtraInfo().equals("cmnet"))
			 return NETWORK_CMNET;
			if(info.getExtraInfo().equals("ctwap"))
			 return NETWORK_CTWAP;
			if(info.getExtraInfo().equals("ctnet"))
			 return NETWORK_CTNET;

			return NETWORK_MOBILE;
		}
	}
	
	
	/**
	 * �ж��ֻ��Ƿ��ڷ���ģʽ.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	}
	
	/**
	 * ��ȡ�豸���к�.
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	/**
	 * �ж��ֻ�SDCard�Ƿ��Ѱ�װ���ɶ�д.
	 * 
	 * @return
	 */
	public static boolean isSDCardUsable() {
		return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment
				.getExternalStorageState());
	}
	
	/**
	 * ����ĳ����ؼ��������������.
	 * 
	 * @param context
	 * @param view
	 */
	public static void hideSoftInputFromWindow(Context context, View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Service.INPUT_METHOD_SERVICE);
		IBinder binder = view.getWindowToken();
		inputMethodManager.hideSoftInputFromWindow(binder, 0);
	}
	
	/**
	 * Get the size in bytes of a bitmap.
	 * 
	 * @param bitmap
	 * @return size in bytes
	 */
	public static int getBitmapSize(Bitmap bitmap) {
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
		return bitmap.getByteCount();
		}*/
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	/**
	 * Check how much usable space is available at a given path.
	 *
	 * @param path The path to check
	 * @return The space available in bytes
	 */
	public static long getUsableSpace(File path) {
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
		return path.getUsableSpace();
		}*/
		final StatFs stats = new StatFs(path.getPath());
		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
	}

	/**
	 * Get the memory class of this device (approx. per-app memory limit)
	 *
	 * @param context
	 * @return
	 */
	public static int getMemoryClass(Context context) {
	return ((ActivityManager) context.getSystemService(
	Context.ACTIVITY_SERVICE)).getMemoryClass();
	}

	/**
	 * Get a usable cache directory (external if available, internal otherwise).
	 *
	 * @return The cache dir
	 */
	public static File getDiskCacheDir() {
		return getDiskCacheDir(MiniApplication.getContext());
	}

	/**
	 * Get a usable cache directory (external if available, internal otherwise).
	 *
	 * @return The cache dir
	 */
	public static File getDiskCacheDir(Context context) {
	// Check if media is mounted or storage is built-in, if so, try and use external cache dir
	// otherwise use internal cache dir
			File cacheDir = null;
			if (isSDCardUsable()) {
				cacheDir = new File(Environment.getExternalStorageDirectory(),Constants.DEFAULT_IMAGE_FOLDER_NAME);
			} else {
				cacheDir = context.getFilesDir();
			}
		
		if (cacheDir != null && !cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		
		return cacheDir;
	}

	/**
	 * ��ȡCPU��Ϣ.
	 * 
	 * @return "CPU���ĸ��� x CPUƵ��"
	 */
	public static String getCpuInfo() {
		return getCpuCoreCount() + " x " + getCpuFrequency();
	}
	
	/**
	 * ��ȡCPU���ĸ���.
	 * 
	 * @param context
	 * @return
	 */
	private static int getCpuCoreCount() {
		int coreCount = 1;
		try {
			String cpuDiePath = "/sys/devices/system/cpu";
			File dir = new File(cpuDiePath);
			String[] cpuFiles = dir.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return Pattern.matches("cpu\\d{1}", name);
				}
			});
			if (cpuFiles != null && cpuFiles.length > 0) {
				coreCount = cpuFiles.length;
			}
		} catch (Exception e) {
			DebugUtils.error(e.getMessage(), e);
		}
		return coreCount;
	}
	
	/**
	 * ��ȡCPUƵ��.
	 * 
	 * @param context
	 * @return
	 */
	private static String getCpuFrequency() {
		String cpuFreq = "";
		BufferedReader bufferedReader = null;
		try {
			String[] args = { "/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
			ProcessBuilder cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			cpuFreq = bufferedReader.readLine();
			// convert from Kb to Gb
			float tempFreq = Float.valueOf(cpuFreq.trim());
			cpuFreq = tempFreq / (1000 * 1000) + "Gb";
			return cpuFreq;
		} catch (Exception e) {
			return StringUtils.isEmpty(cpuFreq) ? "N/A" : cpuFreq + "Kb";
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// ignore.
				}
			}
		}
	}

	/**
	 * ���ϵͳ���ڴ��С.
	 * 
	 * @param context
	 * @return
	 */
	public static String getSystemTotalMemory(Context context) {
		// ϵͳ�ڴ���Ϣ�ļ�
		String memInfoFilePath = "/proc/meminfo";
		String firstLine;
		String[] arrayOfString;
		long initialMemory = 0;
		BufferedReader localBufferedReader = null;
		try {
			FileReader localFileReader = new FileReader(memInfoFilePath);
			localBufferedReader = new BufferedReader(localFileReader, 10240);
			// ��ȡmeminfo��һ��, ϵͳ���ڴ��С
			firstLine = localBufferedReader.readLine();
			arrayOfString = firstLine.split("\\s+");
			// ���ϵͳ���ڴ�, ��λ��KB, ����1024ת��ΪByte
			initialMemory = Long.valueOf(arrayOfString[1].trim()) * 1024;
		} catch (Exception e) {
			DebugUtils.error(e.getMessage(), e);
			// ignore.
		}finally {
			if (localBufferedReader != null) {
				try {
					localBufferedReader.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		// �ڴ��С���, Byteת��ΪKB����MB
		return Formatter.formatFileSize(context, initialMemory);
	}

	/**
	 * ��ȡϵͳ��ǰ�����ڴ�.
	 * 
	 * @param context
	 * @return
	 */
	public static String getSystemAvailMemory(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo memoryInfo = new MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		// �ڴ��С���, Byteת��ΪKB����MB
		return Formatter.formatFileSize(context, memoryInfo.availMem); 
	}
	
	/**
	 * ��ǰϵͳ�Ƿ���ڸ���ֵ
	 * @param sdkVersion ����ϵͳ�汾�޶�ֵ
	 * @return
	 */
	public static boolean isBigerSDKVersion(int sdkVersion)
	{
		if (Build.VERSION.SDK_INT >= sdkVersion) {
			return true;
		}
	
		return false;
	}
}
