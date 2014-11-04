package com.mini.mn.compatible;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.mini.mn.util.Log;

import android.os.Build;

public class CpuFeatures {

	private static final String TAG = "CpuFeatures";
	private static Map<String, String> cpuinfo = null;

	public static String getCpuId() {
		if (cpuinfo == null) {
			cpuinfo = getCpu();
		}
		final StringBuilder cpuid = new StringBuilder();
		cpuid.append(": ");
		cpuid.append(readField(cpuinfo, "Features"));
		cpuid.append(": ");
		cpuid.append(readField(cpuinfo, "Processor"));
		cpuid.append(": ");
		cpuid.append(readField(cpuinfo, "CPU architecture"));
		cpuid.append(": ");
		cpuid.append(readField(cpuinfo, "Hardware"));
		cpuid.append(": ");
		cpuid.append(readField(cpuinfo, "Serial"));
		// Log.d(TAG, "cpuid  " + cpuid);

		return cpuid.toString();
	}

	public static boolean hasNeon() {

		if (cpuinfo == null) {
			cpuinfo = getCpu();
		}
		if (cpuinfo != null) {
			String features = readField(cpuinfo, "Features");
			if (features!=null && features.contains("neon")) {
				return true;
			}
		}
		return false;
	}

	public static boolean sdkAboveOrEqual(int level) {
		return android.os.Build.VERSION.SDK_INT >= level;
	}

	public static boolean isArmv7() {
		try {
			// Log.d(TAG, "isArmv7 " + Build.class.getField("CPU_ABI").get(null).toString());
			return sdkAboveOrEqual(4) && Build.class.getField("CPU_ABI").get(null).toString().startsWith("armeabi-v7") && hasNeon();
		} catch (Throwable e) {
		}
		return false;
	}

	public static boolean isArmv6() {
		if (cpuinfo == null) {
			cpuinfo = getCpu();
		}
		if (cpuinfo != null) {
			String arch = readField(cpuinfo, "CPU architecture");
			Log.d(TAG, "arch " + arch);
			int armarch = 0;
			try {
				if (arch != null && arch.length() > 0) {
					arch = removeHead(arch);
					arch = removeEnd(arch);
					armarch = Integer.parseInt(arch);
					Log.d(TAG, "armarch " + armarch);
					if (armarch >= 6) {
						return true;
					}
				}
				// armarch = int
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private static boolean isDigi(char a) {
		if (a >= '0' && a <= '9') {
			return true;
		}
		return false;
	}

	private static String removeHead(String info) {
		try {
			if (info != null && info.length() > 0) {
				while (!isDigi(info.charAt(0))) {
					if (info.length() == 1) {
						return null;
					}
					info = info.substring(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}

	private static String removeEnd(String info) {
		try {
			int len = 0;
			while (isDigi(info.charAt(len++))) {
				if (info.length() <= len)
					break;
			}
			len--;
			if (info.length() > (len + 1) && len > 0) {
				info = info.substring(0, len);
			}
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}

	private static String readField(Map<String, String> info, String field) {
		return info.get(field);
	}

	public static HashMap<String, String> getCpu() {
		final HashMap<String, String> values = new HashMap<String, String>();

		try {
			ProcessBuilder cmd;
			String[] args = {
					"/system/bin/cat",
					"/proc/cpuinfo" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			final InputStream is = process.getInputStream();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			String line = null;
			while ((line = reader.readLine()) != null) {
				// key: value
				final String[] kv = line.split(":", 2);
				if (kv == null || kv.length < 2) {
					continue;
				}

				// key
				final String key = kv[0].trim();
				final String value = kv[1].trim();
				values.put(key, value);
			}

			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return values;
	}

}
