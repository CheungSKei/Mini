package com.mini.mn.db.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.mini.mn.compatible.CConstants;
import com.mini.mn.compatible.CompatibleFileStorage;

public final class ConfigFileStorage {

	private Map<Integer, Object> values;
	private boolean writeLock = false;
	private String filePath = "";

	private static ConfigFileStorage fileCfg = null;

	public synchronized static ConfigFileStorage getConfigFileStg() {
		if (fileCfg == null) {
			fileCfg = new ConfigFileStorage(ConstantsStorage.DATAROOT_MOBILEMEM_PATH + ConstantsStorage.SYSTEM_INFO_FILENAME);

			String imei = (String)fileCfg.get(ConstantsStorage.DEVICE_IMEI);
			if(imei != null){
				CompatibleFileStorage.getConfigFileStg().set(CConstants.DEVICE_IMEI, imei);
			}
		}
		return fileCfg;
	}

	private ConfigFileStorage(String filePath) {
		this.filePath = filePath;
		openCfg();
		writeLock = false;
	}

	public void set(int type, Object value) {
		values.put(type, value);
		if (!writeLock) {
			writeCfg();
		}
	}

	public Object get(int type) {
		return values.get(type);
	}

	public Object get(int type, Object defObj) {
		Object obj = values.get(type);
		if (obj == null) {
			return defObj;
		}
		return obj;
	}

	public void lockWrite() {
		writeLock = true;
	}

	public void unlockWrite() {
		writeLock = false;
		writeCfg();
	}

	@SuppressWarnings("unchecked")
	private void openCfg() {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			if (file.length() == 0) {
				values = new HashMap<Integer, Object>();
				return;
			}

			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			values = (Map<Integer, Object>) objIn.readObject();
			objIn.close();
			fileIn.close();

		} catch (Exception e) {
			values = new HashMap<Integer, Object>();
			e.printStackTrace();
		}
	}

	private synchronized void writeCfg() {
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(values);
			objOut.close();
			fileOut.flush();
			fileOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}

		values = new HashMap<Integer, Object>();
	}

}