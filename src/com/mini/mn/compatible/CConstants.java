package com.mini.mn.compatible;

import android.os.Environment;

public class CConstants {
	
	// STORAGE PATH
	public static String DATAROOT_MOBILEMEM_PATH = "/data/data/com.mini.mn/MiniMsg/";
	public static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String DATAROOT_SDCARD_PATH = SDCARD_ROOT + "/mini/MiniMsg/";

	public static final String DATAROOT_SDCARD_DOWNLOAD_PATH = SDCARD_ROOT + "/mini/MiniMsg/Download/";
	public static final String DATAROOT_SDCARD_CAMERA_PATH = SDCARD_ROOT + "/mini/MiniMsg/Camera/";
	
	public static final String COMPATIBLE_INFO_FILENAME = "CompatibleInfo.cfg";
	
	// SYSTEM CONFIGURE
	public static final int DEVICE_ID = 0x100; // 256
	public static final int DEVICE_TYPE = 0x101; // 257
	public static final int DEVICE_IMEI = 0x102; // 258
	
	public static final int USERINFO_AUDIO_IN_MODE = 0x17001;
	public static final int USERINFO_AUDIO_ON_SPEAKER = 0x18001;

}
