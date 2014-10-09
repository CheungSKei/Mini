package com.mini.mn.db;

import java.io.File;
import java.io.IOException;

import com.mini.mn.db.storage.ConstantsStorage;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

/**
 * 用户信息数据库存储接口
 * 
 * @version 1.0.0
 * @date 2014-10-08
 * @author S.Kei.Cheung
 */
public interface IAccountStorage {

	// return current uin 用户识别码
	int getUin();

	Object getAccCfgItem(int item);

	void setAccCfgItem(int item, Object value);
	/**
	 * 获取帐号路径
	 * @return
	 */
	String getAccPath();
	/**
	 * 获取缓存路径
	 * @return
	 */
	String getCachePath();

	/**
	 * class for hold global account
	 * 
	 * @author S.Kei.Cheung
	 * 
	 */
	public static class Factory {

		private static final String TAG = "Mini.IAccountStorage.Factory";

		private static IAccountStorage gAccount;

		public static void init(IAccountStorage acc) {
			gAccount = acc;
		}

		public static IAccountStorage getAccStg() {
			if (gAccount == null) {
				Log.e(TAG, "account storage not initialized");
				// TODO:原来在MMCore里面初始化实现该方法
				gAccount = new AccountStorage(buildSysPath(), new AccountStorage.IEvent() {

					@Override
					public void onAccountPreReset(final AccountStorage acc) {
						
					}

					@Override
					public void onAccountPostReset(final AccountStorage acc, final boolean updated) {
						
					}

					@Override
					public void onAccountInit(final AccountStorage acc) {
					
					}
				});
			}
			return gAccount;
		}

		@SuppressWarnings("unchecked")
		public static <T> T getAccCfgItem(int item, T def) {
			if (gAccount == null) {
				return def;
			}

			final Object o = gAccount.getAccCfgItem(item);
			if (o == null) {
				return def;
			}

			return (T) o;
		}

		public static void setAccCfgItem(int item, Object value) {
			if (gAccount == null) {
				return;
			}

			gAccount.setAccCfgItem(item, value);
		}
		
		public static String buildSysPath() {
			String sysPath = ConstantsStorage.DATAROOT_MOBILEMEM_PATH;
			File file = new File(sysPath);
			if (!file.exists()) {
				file.mkdirs();
			}

			file = new File(ConstantsStorage.SDCARD_ROOT);
			// bug! 这里exist 不代表就是sdcard可用!
			if (file.exists() && Util.isSDCardAvail()) {
				file = new File(ConstantsStorage.DATAROOT_SDCARD_PATH);
				if (!file.exists()) {
					if (file.mkdirs()) {
						sysPath = ConstantsStorage.DATAROOT_SDCARD_PATH;
					}

				} else {
					sysPath = ConstantsStorage.DATAROOT_SDCARD_PATH;
				}

				file = new File(ConstantsStorage.DATAROOT_SDCARD_CAMERA_PATH);
				if (!file.exists()) {
					file.mkdirs();
				}
				file = new File(ConstantsStorage.DATAROOT_SDCARD_VIDEO_PATH);
				if (!file.exists()) {
					file.mkdirs();
				}
				file = new File(ConstantsStorage.DATAROOT_SDCARD_VUSER_ICON_PATH);
				if (!file.exists()) {
					file.mkdirs();
				}
				file = new File(ConstantsStorage.DATAROOT_SDCARD_VUSER_ICON_PATH + ConstantsStorage.NO_MEDIA_FILENAME);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}

			}
			file = new File(sysPath);
			if (!file.exists()) {
				file.mkdirs();
			}

			return sysPath;
		}
	}
}
