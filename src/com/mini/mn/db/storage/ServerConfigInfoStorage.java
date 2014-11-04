package com.mini.mn.db.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mini.mn.algorithm.FileOperation;
import com.mini.mn.platformtools.KVConfig;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

public class ServerConfigInfoStorage extends MStorage {
	private static final String TAG = "MicroMsg.ServerConfigInfoStorage";

	private String infoCache = null;
	private ConfigStorage cfgStg = null;

	private boolean isAlreadyReadLocal = false;
	private static final String LOCAL_CONFIG_FILENAME = "deviceconfig.cfg";

	public ServerConfigInfoStorage(ConfigStorage cfgStg) {
		this.cfgStg = cfgStg;
		
	}
	
	public void readFromLocalIfNeed(){
		readFromLocal(ConstantsStorage.DATAROOT_MOBILEMEM_PATH);
	}
	
	private void readConfig(){
		
		String info = (String) cfgStg.get(ConstantsStorage.USERINFO_SERVER_CONFIG_INFO);
		Log.d(TAG, "readConfig xml " + info);
		if(!Util.isNullOrNil(infoCache)){
			super.doNotify(info);
		}
	}
	private int readFromLocal(String accPath) {
		// isAlreadyReadLocal = true;
		String path = accPath + LOCAL_CONFIG_FILENAME;
		if (!FileOperation.fileExists(path)) {
			
			readConfig();
			return -1;
		}
		byte[] buf = FileOperation.readFromFileV2(path, 0, -1);
		if (Util.isNullOrNil(buf)) {
			
			readConfig();
			return -2;
		}
		String info = new String(buf);
		if (Util.isNullOrNil(info)) {
			
			readConfig();
			return -3;
		}
		Log.d(TAG, "readFromLocal info " + info);
		cfgStg.set(ConstantsStorage.USERINFO_SERVER_CONFIG_INFO, info);
		isAlreadyReadLocal = true;

		super.doNotify(info);
		return 0;
	}

	public String getInfoByKey(final int key) {
		infoCache = (String) cfgStg.get(key);
		Log.d(TAG, "getInfoByKey xml " + infoCache + " key " + key);
		return infoCache;
	}

	public int saveConfigInfo(String info) {
		Log.d(TAG, "dkconf info:[%s] ", info);
		if (isAlreadyReadLocal) {
			return 0;
		}
		cfgStg.set(ConstantsStorage.USERINFO_SERVER_CONFIG_INFO, info);
		infoCache = null;

		super.doNotify(info);
		return 0;
	}

	public static String getLocalFingerprint() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String flinger = android.os.Build.FINGERPRINT;
		if (flinger != null) {
			flinger = flinger.replace("/", ":");
		}
		map.put("fingerprint", flinger);
		map.put("manufacturer", android.os.Build.MANUFACTURER);
		map.put("device", android.os.Build.DEVICE);
		map.put("model", android.os.Build.MODEL);
		map.put("product", android.os.Build.PRODUCT);
		map.put("board", android.os.Build.BOARD);
		map.put("release", android.os.Build.VERSION.RELEASE);
		map.put("codename", android.os.Build.VERSION.CODENAME);
		map.put("incremental", android.os.Build.VERSION.INCREMENTAL);
		map.put("display", android.os.Build.DISPLAY);
		String key = Util.mapToXml("key", map);
		Log.d(TAG, "getLocalFingerprint  " + key);
		return key;
	}

	/**
	 * <deviceinfo>
	 * <MANUFACTURER name="ZTE">
	 * <MODEL name="U880">
	 * <VERSION_RELEASE name="2.2.2">
	 * <VERSION_INCREMENTAL name="eng.root.20111209.164040">
	 * <DISPLAY name="MIUI.2.3.7c"></DISPLAY>
	 * </VERSION_INCREMENTAL>
	 * </VERSION_RELEASE>
	 * </MODEL>
	 * </MANUFACTURER>
	 * </deviceinfo>
	 */
	public static String getFingerprint() {
		StringBuffer key = new StringBuffer();
		key.append("<deviceinfo>");
		key.append("<MANUFACTURER name=\"");
		key.append(android.os.Build.MANUFACTURER);
		key.append("\">");
		key.append("<MODEL name=\"");
		key.append(android.os.Build.MODEL);
		key.append("\">");
		key.append("<VERSION_RELEASE name=\"");
		key.append(android.os.Build.VERSION.RELEASE);
		key.append("\">");
		key.append("<VERSION_INCREMENTAL name=\"");
		key.append(android.os.Build.VERSION.INCREMENTAL);
		key.append("\">");
		key.append("<DISPLAY name=\"");
		key.append(android.os.Build.DISPLAY);
		key.append("\">");
		key.append("</DISPLAY></VERSION_INCREMENTAL></VERSION_RELEASE></MODEL></MANUFACTURER></deviceinfo>");
		Log.d(TAG, "getFingerprint  " + key.toString());
		return key.toString();
	}

	public static boolean writeConfigToFile(String info) {
		
		if(Util.isNullOrNil(info)){
			
			return false;
		}
		try{
			 Map<String, String> maps = KVConfig.parseXml(info, "voip", null);
			 
			 if(maps == null){
				 return false;
			 }
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		boolean flag = false;
		File filePath = new File(ConstantsStorage.DATAROOT_MOBILEMEM_PATH);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(ConstantsStorage.DATAROOT_MOBILEMEM_PATH + LOCAL_CONFIG_FILENAME);
			fw.write(info);
			flag = true;
			if (fw != null) {
				fw.close();
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return flag;
	}
}
