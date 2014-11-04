package com.mini.mn.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import com.mini.mn.constant.ConstantsProtocal;
import com.mini.mn.db.AccountStorage;
import com.mini.mn.db.IAccountStorage;
import com.mini.mn.db.storage.ConfigFileStorage;
import com.mini.mn.db.storage.ConstantsStorage;
import com.mini.mn.network.socket.IDispatcher;
import com.mini.mn.network.socket.IMessageListener_AIDL;
import com.mini.mn.network.socket.NetSceneQueue;
import com.mini.mn.platformtools.MMHandlerThread;
import com.mini.mn.platformtools.MMHandlerThread.ResetCallback;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

public class MiniCore {
	
	private static final String TAG = "MiniMsg.MMCore";
	
	private static IDispatcher mMessageEvent_dispatcher;
	// 帐号信息,记录数据库数据
	private final AccountStorage accStg;
	
	private static MiniCore theCore = null;
	
	private final MMHandlerThread workerThread;
	// 文件系统路径
	private final String sysPath;
	// 配置文件
	private final ConfigFileStorage sysConfigStg;
	// 网络队列
	private final NetSceneQueue queue;
	
	/**
	 * 消息监听(全局消息,当有消息自动推送过来,则进行处理,与单一请求不同)
	 */
	private static List<IMessageListener_AIDL> messageListeners = new ArrayList<IMessageListener_AIDL>();
	
	private MiniCore(final NetSceneQueue.IOnQueueIdle queueIdle) {
		sysPath = buildSysPath();
		
		workerThread = new MMHandlerThread();
		
		// system config force phone mem
		sysConfigStg = ConfigFileStorage.getConfigFileStg();
		
		accStg = new AccountStorage(sysPath, new AccountStorage.IEvent() {

			@Override
			public void onAccountPreReset(final AccountStorage acc) {
				// update account storage factory
				IAccountStorage.Factory.init(accStg);
			}

			@Override
			public void onAccountPostReset(final AccountStorage acc, final boolean updated) {
				long transactionTicket = getAccStg().getDataDB().beginTransaction(Thread.currentThread().getId());

				getAccStg().getDataDB().endTransaction(transactionTicket);
			}

			@Override
			public void onAccountInit(final AccountStorage acc) {
				
			}
		});
		
		queue = NetSceneQueue.getInstance(queueIdle);
		queue.setWorkerThread(workerThread);
	}
	
	public static void initialize(NetSceneQueue.IOnQueueIdle queueIdle) {
		theCore = new MiniCore(queueIdle);
	}
	
	public static AccountStorage getAccStg() {
		final AccountStorage accStg = getCore().accStg;
		if (accStg != null && accStg.hasSetUin()) {
			return accStg;
		}

		synchronized (accStg) {
			if (accStg != null && accStg.hasSetUin()) {
				return accStg;
			}
			final ConfigFileStorage sysCfg = getCore().sysConfigStg;
			final Integer uin = (Integer) sysCfg.get(ConstantsStorage.DEFAULT_UIN);
			if (uin != null && uin != ConstantsProtocal.MM_INVALID_UIN) {
				Log.w(TAG, "auto set up account storage stack: %s", Util.getStack());

				accStg.setAccUin(uin);

				// IMPORTANT: need to validate config
				final String username = Util.nullAsNil((String) accStg.getConfigStg().get(ConstantsStorage.USERINFO_USERNAME));
				// rule 1
				if (username.length() <= 0) {
					Log.e(TAG, "username of acc stg not set: uin=" + uin);
					accStg.reset();
					sysCfg.set(ConstantsStorage.DEFAULT_UIN, ConstantsProtocal.MM_INVALID_UIN);
					MMHandlerThread.postToMainThread(new Runnable() {
						
						@Override
						public void run() {
							release();
						}
					});
				}

			}
			return accStg;
		}
	}
	
	public static void release() {
		if (getCore().workerThread != null) {
			getCore().workerThread.syncReset(new ResetCallback() {

				@Override
				public void callback() {
					if (getCore().accStg != null) {
						getCore().accStg.release();
					}
				}
			});
		}
	}
	
	private static MiniCore getCore() {
		Assert.assertNotNull("MiniCore not initialized by MiniApplication", theCore);
		return theCore;
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
	
	/**
	 * 设置消息事件对象
	 * @param dispatcher
	 */
	public static void setMessageEvent(IDispatcher dispatcher){
		mMessageEvent_dispatcher = dispatcher;
		getNetSceneQueue().setAutoAuth(dispatcher);
		if(mMessageEvent_dispatcher!=null){
			for(IMessageListener_AIDL iml:messageListeners)
				mMessageEvent_dispatcher.registerMessageListener(iml);
			messageListeners.clear();
		}
	}
	
	/**
	 * 获取消息事件对象
	 * @param dispatcher
	 */
	public static IDispatcher getMessageEvent(){
		return mMessageEvent_dispatcher;
	}
	
	public static List<IMessageListener_AIDL> getMessageListener(){
		return messageListeners;
	}
	
	public static NetSceneQueue getNetSceneQueue() {
		return getCore().queue;
	}
	
	public static MMHandlerThread getWorkerThread() {
		return getCore().workerThread;
	}
}
