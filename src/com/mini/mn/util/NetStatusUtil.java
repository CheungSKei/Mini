package com.mini.mn.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class NetStatusUtil {

	private static final String TAG = "MicroMsg.NetStatusUtil";
	public static final int NON_NETWORK = -1;
	public static final int WIFI = 0;
	public static final int UNINET = 1;
	public static final int UNIWAP = 2;
	public static final int WAP_3G = 3;
	public static final int NET_3G = 4;
	public static final int CMWAP = 5;
	public static final int CMNET = 6;
	public static final int CTWAP = 7;
	public static final int CTNET = 8;
	public static final int MOBILE = 9;
	public static final int LTE = 10;

	/** No specific network policy, use system default. */
	public static final int POLICY_NONE = 0x0;
	/** Reject network usage on metered networks when application in background. */
	public static final int POLICY_REJECT_METERED_BACKGROUND = 0x1;

	public static final int TBACKGROUND_NOT_LIMITED = 0x0;
	public static final int TBACKGROUND_PROCESS_LIMITED = 0x1;
	public static final int TBACKGROUND_DATA_LIMITED = 0x2;
	public static final int TBACKGROUND_WIFI_LIMITED = 0x3;

	public static final int NO_SIM_OPERATOR = 0;
	
	public static void dumpNetStatus(Context context) {

		try {

			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
			Log.e(TAG, "isAvailable " + activeNetInfo.isAvailable());
			Log.e(TAG, "isConnected " + activeNetInfo.isConnected());
			Log.e(TAG, "isRoaming " + activeNetInfo.isRoaming());
			Log.e(TAG, "isFailover " + activeNetInfo.isFailover());
			Log.e(TAG, "getSubtypeName " + activeNetInfo.getSubtypeName());
			Log.e(TAG, "getExtraInfo " + activeNetInfo.getExtraInfo());
			Log.e(TAG, "activeNetInfo " + activeNetInfo.toString());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static boolean isConnected(Context context) {

		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = conMan.getActiveNetworkInfo();
		boolean connect = false;
		try {
			connect = activeNetInfo.isConnected();
		} catch (Exception e) {
		}
		return connect;
	}

	public static String getNetTypeString(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return "NON_NETWORK";
		}
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo == null) {
			return "NON_NETWORK";
		}

		if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return "WIFI";

		} else {
			Log.d(TAG, "activeNetInfo extra=%s, type=%d", activeNetInfo.getExtraInfo(), activeNetInfo.getType());
			if (activeNetInfo.getExtraInfo() != null) {
				return activeNetInfo.getExtraInfo();
			}
			return "MOBILE";
		}
	}

	public static int getNetType(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return NON_NETWORK;
		}
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo == null) {
			return NON_NETWORK;
		}

		if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return WIFI;

		} else {
			Log.d(TAG, "activeNetInfo extra=%s, type=%d", activeNetInfo.getExtraInfo(), activeNetInfo.getType());
			if (activeNetInfo.getExtraInfo() != null) {
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("uninet"))
					return UNINET;
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("uniwap"))
					return UNIWAP;
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("3gwap"))
					return WAP_3G;
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("3gnet"))
					return NET_3G;
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("cmwap"))
					return CMWAP;
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("cmnet"))
					return CMNET;
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("ctwap"))
					return CTWAP;
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("ctnet"))
					return CTNET;
				if (activeNetInfo.getExtraInfo().equalsIgnoreCase("LTE"))
					return LTE;
			}
			return MOBILE;
		}
	}
	
	public static int getISPCode(Context context){
		TelephonyManager tel = (TelephonyManager) context 
                .getSystemService(Context.TELEPHONY_SERVICE); 
		if (tel == null) {
			return NO_SIM_OPERATOR;
		}

		String simOperator = tel.getSimOperator(); 
		if(simOperator == null || simOperator.length() < 5){ //IMSI共有15位,取前5位
			return NO_SIM_OPERATOR;
		}
		
		String MCC_MNC = simOperator.substring(0, 5);
		
		Log.d(TAG, "getISPCode MCC_MNC=%s", MCC_MNC);
		
		return Integer.valueOf(MCC_MNC);
	}
	
	public static String getISPName(Context context){
		TelephonyManager tel = (TelephonyManager) context 
                .getSystemService(Context.TELEPHONY_SERVICE); 
		if (tel == null) {
			return "";
		}
		
		final int MAX_LENGTH = 100; // 不超过100的长度
		Log.d(TAG, "getISPName ISPName=%s", tel.getSimOperatorName());
		
		if(tel.getSimOperatorName().length() <= MAX_LENGTH){
			return tel.getSimOperatorName();
		} else {
			return tel.getSimOperatorName().substring(0, MAX_LENGTH);
		}
	}

	public static int guessNetSpeed(Context context) {
		try {
			final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			final NetworkInfo netInfo = manager.getActiveNetworkInfo();
			if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return 100 * 1024;
			}

			switch (netInfo.getSubtype()) {

			case TelephonyManager.NETWORK_TYPE_GPRS:
				return 4 * 1024;
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return 8 * 1024;

			case TelephonyManager.NETWORK_TYPE_UMTS:
			case TelephonyManager.NETWORK_TYPE_CDMA:
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
			case TelephonyManager.NETWORK_TYPE_1xRTT:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
			case TelephonyManager.NETWORK_TYPE_HSUPA:
			case TelephonyManager.NETWORK_TYPE_HSPA:
			case TelephonyManager.NETWORK_TYPE_IDEN:
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
			case TelephonyManager.NETWORK_TYPE_LTE:
			case TelephonyManager.NETWORK_TYPE_EHRPD:
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				return 100 * 1024;

			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 100 * 1024;
	}

	public static boolean is2G(Context context) {
		try {
			final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			final NetworkInfo netInfo = manager.getActiveNetworkInfo();
			if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return false;
			}
			if (netInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE || netInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean is4G(Context context) {
		try {

			final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			final NetworkInfo netInfo = manager.getActiveNetworkInfo();
			if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return false;
			}
			// TODO:may be 5G in the future
			if (netInfo.getSubtype() >= TelephonyManager.NETWORK_TYPE_LTE) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	public static boolean isWap(Context context) {
		int type = getNetType(context);
		return isWap(type);
	}

	public static boolean isWap(int type) {
		if (type == UNIWAP || type == CMWAP || type == CTWAP || type == WAP_3G) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean is2G(int type) {
		if (type == UNINET || type == UNIWAP || type == CMWAP || type == CMNET || type == CTWAP || type == CTNET) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean is4G(int type) {
		if (type == LTE) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean is3G(int type) {
		if (type == WAP_3G || type == NET_3G) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isMobile(Context context) {
		final int type = getNetType(context);
		return is3G(type) || is2G(type) || is4G(type);
	}

	public static boolean isMobile(int type) {
		return is3G(type) || is2G(type) || is4G(type);
	}

	public static boolean is3G(Context context) {

		try {
			final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			final NetworkInfo netInfo = manager.getActiveNetworkInfo();
			if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return false;
			}
			if (netInfo.getSubtype() >= TelephonyManager.NETWORK_TYPE_EVDO_0 && netInfo.getSubtype() < TelephonyManager.NETWORK_TYPE_LTE) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean isWifi(Context context) {
		int type = getNetType(context);
		return isWifi(type);
	}

	public static boolean isWifi(int type) {
		if (type == WIFI) {
			return true;
		} else {
			return false;
		}
	}

	private static Intent searchIntentByClass(Context context, String className) {
		try {
			PackageManager pmPack = context.getPackageManager();
			List<PackageInfo> packinfos = pmPack.getInstalledPackages(0);
			if (packinfos != null && packinfos.size() > 0) {
				Log.e(TAG, "package  size" + packinfos.size());

				for (int i = 0; i < packinfos.size(); i++) {
					try {
						Log.e(TAG, "package " + packinfos.get(i).packageName);
						Intent mainIntent = new Intent();
						mainIntent.setPackage(packinfos.get(i).packageName);
						List<ResolveInfo> sampleActivityInfos = pmPack.queryIntentActivities(mainIntent, 0);
						int activityCount = sampleActivityInfos != null ? sampleActivityInfos.size() : 0;
						if (activityCount > 0) {

							try {
								Log.e(TAG, "activityName count " + activityCount);
								for (int j = 0; j < activityCount; j++) {
									ActivityInfo activityInfo = sampleActivityInfos.get(j).activityInfo;
									String activityName = activityInfo.name;

									if (activityName.contains(className)) {
										Intent mIntent = new Intent("/");
										ComponentName comp = new ComponentName(activityInfo.packageName, activityInfo.name);
										mIntent.setComponent(comp);
										mIntent.setAction("android.intent.action.VIEW");
										context.startActivity(mIntent);
										return mIntent;
									}
								}
							} catch (Exception eee) {
								eee.printStackTrace();
							}

						}

					} catch (Exception ee) {
						ee.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void startSettingItent(Context context, int type) {

		switch (type) {
		case TBACKGROUND_NOT_LIMITED: {
			// may not be happen
			break;
		}
		case TBACKGROUND_DATA_LIMITED: {
			try {
				{
					Intent mIntent = new Intent("/");
					ComponentName comp = new ComponentName("com.android.providers.subscribedfeeds", "com.android.settings.ManageAccountsSettings");
					mIntent.setComponent(comp);
					mIntent.setAction("android.intent.action.VIEW");
					context.startActivity(mIntent);
				}
			} catch (Exception e) {

				try {
					Intent mIntent = new Intent("/");
					ComponentName comp = new ComponentName("com.htc.settings.accountsync", "com.htc.settings.accountsync.ManageAccountsSettings");
					mIntent.setComponent(comp);
					mIntent.setAction("android.intent.action.VIEW");
					context.startActivity(mIntent);
				} catch (Exception ee) {
					// ee.printStackTrace();
					// final try
					searchIntentByClass(context, "ManageAccountsSettings");
				}

			}
			break;
		}
		case TBACKGROUND_PROCESS_LIMITED: {
			try {
				Intent mIntent = new Intent("/");
				ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
				mIntent.setComponent(comp);
				mIntent.setAction("android.intent.action.VIEW");
				context.startActivity(mIntent);
			} catch (Exception e) {
				// e.printStackTrace();
				// final try
				searchIntentByClass(context, "DevelopmentSettings");
			}
			break;
		}
		case TBACKGROUND_WIFI_LIMITED: {
			try {
				// Intent mIntent = new Intent("/");
				// ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.wifi.AdvancedSettings");
				// mIntent.setComponent(comp);
				// mIntent.setAction("android.intent.action.VIEW");
				// context.startActivity(mIntent);
				Intent it = new Intent();
				it.setAction(Settings.ACTION_WIFI_IP_SETTINGS);
				context.startActivity(it);
			} catch (Exception e) {
				// e.printStackTrace();
				// final try
				searchIntentByClass(context, "AdvancedSettings");
			}
			break;
		}
		}
	}

	public static int getWifiSleeepPolicy(Context context) {
		return Settings.System.getInt(context.getContentResolver(), Settings.System.WIFI_SLEEP_POLICY, Settings.System.WIFI_SLEEP_POLICY_NEVER);
	}

	public static boolean isLimited(int type) {
		if (type == TBACKGROUND_DATA_LIMITED || type == TBACKGROUND_PROCESS_LIMITED || type == TBACKGROUND_WIFI_LIMITED) {
			return true;
		}
		return false;
	}

	public static int getBackgroundLimitType(Context context) {
		if (android.os.Build.VERSION.SDK_INT < 14) {
			// ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			// if (!connectivityManager.getBackgroundDataSetting()) {
			// return TBACKGROUND_DATA_LIMITED;
			// }
		} else {

			try {
				Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
				Object am = activityManagerNative.getMethod("getDefault").invoke(activityManagerNative);
				Object limit = am.getClass().getMethod("getProcessLimit").invoke(am);
				if ((Integer) limit == 0)
					return TBACKGROUND_PROCESS_LIMITED;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {

			int policy = getWifiSleeepPolicy(context);
			if (policy == Settings.System.WIFI_SLEEP_POLICY_NEVER || (getNetType(context) != WIFI)) {
				return TBACKGROUND_NOT_LIMITED;
			} else if (policy == Settings.System.WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED || policy == Settings.System.WIFI_SLEEP_POLICY_DEFAULT) {
				return TBACKGROUND_WIFI_LIMITED;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TBACKGROUND_NOT_LIMITED;
	}

	public static boolean isRestrictBacground(Context Context) {

		int uid = Context.getApplicationInfo().uid;

		try {
			Class<?> cnetworkPolicyManager = Class.forName("android.net.NetworkPolicyManager");
			Object networkPolicyManager = cnetworkPolicyManager.getMethod("getSystemService", Context.class).invoke(cnetworkPolicyManager, Context);
			Field field = cnetworkPolicyManager.getDeclaredField("mService");
			field.setAccessible(true);
			Object service = (Object) field.get(networkPolicyManager);
			int policy = (Integer) service.getClass().getMethod("getUidPolicy", int.class).invoke(service, uid);
			Log.e(TAG, "policy is " + policy);

			if (policy == POLICY_REJECT_METERED_BACKGROUND) {
				return true;
			} else if (policy == POLICY_NONE) {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return checkFromXml(uid);
	}

	public static boolean isImmediatelyDestroyActivities(Context context) {
		return Settings.System.getInt(context.getContentResolver(), Settings.System.ALWAYS_FINISH_ACTIVITIES, 0) != 0;
	}

	public static boolean checkFromXml(int uid) {

		try {

			runRootCommand();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			FileInputStream stream = new FileInputStream(new File("/data/system/netpolicy.xml"));
			Document document = builder.parse(stream);
			Element root = document.getDocumentElement();
			NodeList items = root.getElementsByTagName("uid-policy");
			for (int i = 0; i < items.getLength(); i++) {

				Element item = (Element) items.item(i);
				String u = item.getAttribute("uid");
				String p = item.getAttribute("policy");

				Log.e(TAG, "uid is " + u + "  policy is " + p);

				if (u.equals(Integer.toString(uid))) {

					if (Integer.parseInt(p) == POLICY_REJECT_METERED_BACKGROUND) {
						return true;
					} else if (Integer.parseInt(p) == POLICY_NONE) {
						return false;
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public static boolean runRootCommand() {

		Process process = null;
		DataOutputStream os = null;
		try {

			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();

		} catch (Exception e) {
			Log.d(TAG, "the device is not rooted， error message： " + e.getMessage());
			return false;

		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (process != null) {
					process.destroy();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return true;
	}

}
