package com.mini.mn.db;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.mini.mn.algorithm.FileOperation;
import com.mini.mn.algorithm.MD5;
import com.mini.mn.app.MiniApplication;
import com.mini.mn.app.MiniApplicationContext;
import com.mini.mn.constant.ConstantsProtocal;
import com.mini.mn.db.MemoryStorage.IOnAttachTable;
import com.mini.mn.db.SqliteDB.IFactory;
import com.mini.mn.db.storage.ConstantsStorage;
import com.mini.mn.db.storage.MsgInfoStorage;
import com.mini.mn.platformtools.FilesCopy;
import com.mini.mn.util.DeviceUtils;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

/**
 * 用户信息数据库存储
 * 
 * @version 1.0.0
 * @date 2014-10-08
 * @author S.Kei.Cheung
 */
public class AccountStorage implements IAccountStorage {
	public static final String TAG = "MicroMsg.AccountStorage";

	public interface IEvent {

		void onAccountPreReset(AccountStorage acc);

		void onAccountPostReset(AccountStorage acc, boolean updated);

		void onAccountInit(AccountStorage acc);
	}
	
	public static interface IDataTransferEvent {
		public void onTransferStart();
		public void onTransferStop();
	}

	private static final String STORAGE_VOICE2 = "voice2/";
	private static final String STORAGE_VOICE = "voice/";
	private static final String STORAGE_IMAGE = "image/";
	private static final String STORAGE_SHAKE_TRAN_IMG = "image/shakeTranImg/";
	private static final String STORAGE_IMAGE2 = "image2/";
	private static final String STORAGE_AVATAR = "avatar/";
	private static final String STORAGE_ALBUM = "album/";
	private static final String STORAGE_EMOJI = "emoji/";
	private static final String STORAGE_WATCHPIG = "locallog";
	private static final String STORAGE_VIDEO = "video/";
	private static final String STORAGE_THEME = "theme/";
	private static final String STORAGE_PACKAGE = "package/";
	private static final String STORAGE_MAILAPP = "mailapp/";
	private static final String STORAGE_OPENAPI = "openapi/";
	private static final String STORAGE_ATTACHMENT = "attachment/";
	private static final String STORAGE_BRANDICON = "brandicon/";
	private static final String STORAGE_REPORT = "logcat/";
	private static final String STORAGE_CARD_PACKAGE = "card/";
	private static final String STORAGE_REC_BIZ = "recbiz/";
	private static final String STORAGE_SPEEX_TEMP = "speextemp/";

	private MsgInfoStorage msgStg;

	private SqliteDB dataDB = null;
	private MemoryStorage memDB = null;

	private int uin = ConstantsProtocal.MM_INVALID_UIN;
	private String sysPath;
	private String accPath;
	private String cachePath;
	private final IEvent event;
	private SharedPreferences sp;
	
	//private static IDataTransferEvent dtEvent;
	private static List<IDataTransferEvent> dtEventList = new ArrayList<IDataTransferEvent>();
	
	/**
	 * 初始化文件保存文件夹
	 * @param sysPath 文件夹路径
	 * @param event
	 */
	public AccountStorage(final String sysPath, final IEvent event) {
		this.sysPath = sysPath;
		this.event = event;
		sp = MiniApplication.getContext().getSharedPreferences(MiniApplicationContext.getDefaultPreferencePath(), Context.MODE_PRIVATE);
	}
	
	public static void addDataTransferEvent(IDataTransferEvent dtEvent) {
		AccountStorage.dtEventList.add(dtEvent);
	}

	public static void removeDataTransferEvent(IDataTransferEvent dtEvent) {
		AccountStorage.dtEventList.remove(dtEvent);
	}
	
	@Override
	public int getUin() {
		return uin;
	}

	public boolean hasSetUin() {
		return uin != ConstantsProtocal.MM_INVALID_UIN;
	}

	public void remount(final String path) {
		// Log.w(TAG, "dksdcard remounted on path:" + path + " sysPath:" + sysPath);
		if (path.equalsIgnoreCase(sysPath)) {
			return;
		}

		this.sysPath = path;

		resetAccUin();
	}

	/**
	 * SD卡是否可用
	 * @return
	 */
	public boolean isSDCardAvailable() {
		boolean bRet = Util.isSDCardAvail();
		// Log.d(TAG ,"dksdcard isSDCardAvailable:" + bRet + " stack:" + com.tencent.mm.sdk.platformtools.Util.getStack());
		if (!bRet) {
			return bRet;
		}

		if (sysPath.startsWith(ConstantsStorage.SDCARD_ROOT)) {
			return true;
		}
		// !!!!!!!!!!!!
		if (hasSetUin()) {
			remount(IAccountStorage.Factory.buildSysPath());
		}
		return true;
	}

	/**
	 * 移动数据文件线程
	 */
	static class MoveDataFiles extends Thread {
		String from;
		String to;

		public MoveDataFiles(String from, String to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public void run() {
			if ((Util.isNullOrNil(from)) || (Util.isNullOrNil(to))) {
				return;
			}

			Log.d(TAG, "MoveDataFiles :" + from + " to :" + to);
			if (!Util.isSDCardAvail()) {
				return;
			}
			if (!to.substring(0, ConstantsStorage.DATAROOT_SDCARD_PATH.length()).equals(ConstantsStorage.DATAROOT_SDCARD_PATH)) {
				return;
			}
			FilesCopy.copy(from + STORAGE_IMAGE, to + STORAGE_IMAGE, true);
			FilesCopy.copy(from + STORAGE_IMAGE2, to + STORAGE_IMAGE2, true);
			FilesCopy.copy(from + STORAGE_AVATAR, to + STORAGE_AVATAR, true);
			FilesCopy.copy(from + STORAGE_VIDEO, to + STORAGE_VIDEO, true);
			FilesCopy.copy(from + STORAGE_VOICE, to + STORAGE_VOICE, true);
			FilesCopy.copy(from + STORAGE_VOICE2, to + STORAGE_VOICE2, true);
			FilesCopy.copy(from + STORAGE_PACKAGE, to + STORAGE_PACKAGE, true);
			FilesCopy.copy(from + STORAGE_EMOJI, to + STORAGE_EMOJI, true);
			FilesCopy.copy(from + STORAGE_MAILAPP, to + STORAGE_MAILAPP, true);
			FilesCopy.copy(from + STORAGE_ALBUM, to + STORAGE_ALBUM, true);
			FilesCopy.copy(from + STORAGE_CARD_PACKAGE, to + STORAGE_CARD_PACKAGE, true);
			FilesCopy.copy(from + STORAGE_BRANDICON, to + STORAGE_BRANDICON, true);
		}
	}

	public void resetAccUin() {
		final int uin = this.uin;
		if (this.uin != 0) {
			release();
		}
		this.uin = ConstantsProtocal.MM_INVALID_UIN;
		sp.edit().putBoolean("isLogin", false).commit();
		setAccUin(uin);
	}
	
	public void  setAccUin(final int uin) {
		if (this.uin == uin) {
			Log.v(TAG, "setAccUin, uin not changed, return");
			return;
		}
		if (this.uin != 0) {
			release(); //klem add
		}
		setAccUinImp(uin);
	}

	private void setAccUinImp(final int uin) {
		// Assert.assertTrue("setAccUin should be run in main thread thread:[" +
		// Thread.currentThread().getId() + "," + Thread.currentThread().getName() + "]" ,
		// Thread.currentThread().getId() == 1);
		Log.d(TAG, "dkinit setAccuin uin:%d this:%d stack:%s", uin, this.uin, Util.getStack());
		if (this.uin == uin) {
			Log.v(TAG, "setAccUin, uin not changed, return");
			return;
		}
		
		if (event != null) {
			event.onAccountPreReset(this);
		}

		Log.i(TAG, "has set uin:" + uin);
		this.uin = uin;
		sp.edit().putBoolean("isLogin", true).commit();

		final String uinPath = MD5.getMessageDigest(("mn" + uin).getBytes());
		accPath = sysPath + uinPath + "/";
		cachePath = ConstantsStorage.DATAROOT_MOBILEMEM_PATH + uinPath + "/";

		File file = new File(cachePath);
		Log.d(TAG, "dkacc cachePath:" + cachePath + " accPath:" + accPath);
		if (!file.exists()) {
			file.mkdirs();
			// accPath == cachePath while user login/reg first time without sdcard inserted
			// 这里不删除是不行的, 主要不是逻辑问题, 只是给用户带来大量的无效数据.
			// 后续考虑先rename , 另找时间删除
			// if (!cachePath.equalsIgnoreCase(accPath)) {
			// FileOperation.deleteDir(new File(accPath));
			// }
		}

		file = new File(accPath);
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccCardPath());
		if (!file.exists()) {
			file.mkdir();
		}
		file = new File(getAccImgPath());
		if (!file.exists()) {
			file.mkdir();
		}
		file = new File(getAccShakeTranImgPath());
		if (!file.exists()) {
			file.mkdir();
		}
		file = new File(getAccImgPath2());
		if (!file.exists()) {
			file.mkdir();
		}
		file = new File(getAccAvatarPath());
		if (!file.exists()) {
			file.mkdir();
		}
		file = new File(getAccAlbumPath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccEmojiPath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccWatchPigPath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccMailAppCachePath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccVoicePath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccVoicePath2());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccVideoPath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccThemePath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccPackagePath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccOpenApiPath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccAttachMentPath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getAccBrandIconPath());
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(getCacheReportPath());
		if (!file.exists()) {
			file.mkdir();
		}

		// Log.d(TAG ,"dksdcard isSDCardAvail:" +Util.isSDCardAvail() + " sysPath:" +sysPath );
		if (Util.isSDCardAvail() && (sysPath.equals(ConstantsStorage.DATAROOT_SDCARD_PATH))) {
			MoveDataFiles movefile = new MoveDataFiles(cachePath, accPath);
			movefile.start();
		}

		file = new File(getAccCardPath() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();
				
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		file = new File(getAccImgPath() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		file = new File(getAccImgPath2() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		file = new File(getAccAvatarPath() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		file = new File(getAccVoicePath() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		file = new File(getAccVoicePath2() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		file = new File(getAccVideoPath() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		file = new File(getAccPackagePath() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		file = new File(getAccOpenApiPath() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		file = new File(getAccBrandIconPath() + ConstantsStorage.NO_MEDIA_FILENAME);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		// test
		// if(Test.dropAlbumTable){
		// Log.e(TAG,"delete snsMicorMsg File!!");
		// FileOperation.deleteFile(cachePath + "SnsMicroMsg.db" );
		// FileOperation.deleteFile(cachePath + "SnsMicroMsg.db.ini");
		// Test.dropAlbumTable = false;
		// }

		closeDB();

		initDB(cachePath + ConstantsStorage.DB_NAME, uin, cachePath + ConstantsStorage.ENDB_NAME);
	}
	
	/**
	 * 初始化数据库
	 * @param cacheDbPath	cacheDB路径
	 * @param accUin		用户标识码
	 * @param enCacheDbPath 加密CacheDB路径
	 */
	public void initDB(String cacheDbPath, int accUin, String enCacheDbPath) {
		dataDB = new SqliteDB(new SqliteDB.Callbacks() {

			@Override
			public void preCloseCallback() {
				if (memDB != null) {
					memDB.preDiskClose();
				}
			}

			@Override
			public void postBeginTransCallback() {
				if (memDB != null) {
					memDB.postDiskBeginTrans();
				}
			}

			@Override
			public void postEndTransCallback() {
				if (memDB != null) {
					memDB.postEndTransCallback();
				}

			}
		});

		// getBaseDBFactories()为表信息
		if (!dataDB.initDb(cacheDbPath, enCacheDbPath, accUin, DeviceUtils.getIMEI(), getBaseDBFactories() , true)) {
			throw new AccountNotReadyException(AccountNotReadyException.DBVER_EXCEPTION);
		}
		String initDbErrMsg = dataDB.getInitErrMsg();
		if(!Util.isNullOrNil(initDbErrMsg)){
			Log.e(TAG ,"dbinit failed :" + initDbErrMsg);
		}

	}

	public HashMap<Integer, IFactory> getBaseDBFactories() {
		HashMap<Integer, IFactory> map = new HashMap<Integer, SqliteDB.IFactory>();
		map.putAll(baseDBFactories);
		map.putAll(new HashMap<Integer,SqliteDB.IFactory>());
		return map;
	}
	
	public void closeDBForUEH(){
		closeDB(true);
	}
	public void closeDB(){
		closeDB(false);
	}
	
	private void closeDB(boolean isUEH) {
		Log.i(TAG, "closeDB " + Util.getStack());
		if (dataDB != null) {
			dataDB.closeDB(isUEH);
//20130204 comment by dk process in sqliteDB			
//			dataDB = null;
		}

		if (memDB != null) {
			memDB.closeDB();
			memDB = null;
		}

		// TODO
		// if(snsDB != null){
		// snsDB.closeDB();
		// snsDB = null;
		// }
	}
	
	public void attachMemDBTable(IOnAttachTable table) {
		memDB.attachTable(table);
	}

	private static final int MM_PRESENCE_WEBWX = 0x1;
	private static final int MM_NEWSYNC_STATUS_SHAKETRANIMG_BIND = 0x2;
	private static final int MM_NEWSYNC_STATUS_SHAKETRANIMG_ACTIVE = 0x4;
	private static final int MM_NEWSYNC_STATUS_SHAKEBOOKMARK_BIND = 0x8;

	private int userStatus = 0;

	public boolean isWebWXOnline() {
		return (userStatus & MM_PRESENCE_WEBWX) != 0;
	}

	public boolean isShakeTranImgBind() {
		return (userStatus & MM_NEWSYNC_STATUS_SHAKETRANIMG_BIND) != 0;
	}

	public boolean isShakeTranImgActive() {
		return (userStatus & MM_NEWSYNC_STATUS_SHAKETRANIMG_ACTIVE) != 0;
	}

	public boolean isShakeBookmarkBind() {
		return (userStatus & MM_NEWSYNC_STATUS_SHAKEBOOKMARK_BIND) != 0;
	}

	public SqliteDB getDataDB() {
		return dataDB;
	}

	public MemoryStorage getMemDB() {
		return memDB;
	}

	public int memAppendToDisk(final String table){
		if(Util.isNullOrNil(table)){
			return -1;
		}
		if(memDB == null || memDB.isClose()){
			return -2;
		}
		memDB.appendToDisk(table);
		return 0;
	}	
	
	@Override
	public Object getAccCfgItem(int item) {
		return null;
	}

	@Override
	public void setAccCfgItem(int item, Object value) {
	
	}

	public MsgInfoStorage getMessageStg() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return msgStg;
	}

	public String getAccCardPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_CARD_PACKAGE;
	}

	public String getAccImgPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_IMAGE;
	}

	public String getAccImgPath2() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_IMAGE2;
	}

	public String getAccAvatarPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_AVATAR;
	}

	public String getAccVoicePath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_VOICE;
	}

	public String getAccVoicePath2() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_VOICE2;
	}
	
	public String getAccRecBizPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_REC_BIZ;
	}


	public String getAccAlbumPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_ALBUM;
	}

	public String getAccSpeexTempPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_SPEEX_TEMP;
	}

	public String getAccEmojiPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_EMOJI;
	}

	private String getAccWatchPigPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return sysPath + STORAGE_WATCHPIG;
	}

	public String getAccMailAppCachePath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}

		return accPath + STORAGE_MAILAPP;
	}

	public String getAccVideoPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_VIDEO;
	}
	
	public String getAccShakeTranImgPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException(); 
		}
		return accPath + STORAGE_SHAKE_TRAN_IMG;
	}

	private String getAccThemePath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_THEME;
	}

	public String getAccPackagePath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_PACKAGE;
	}

	public String getAccOpenApiPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_OPENAPI;
	}

	public String getAccAttachMentPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_ATTACHMENT;
	}

	public String getAccBrandIconPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return accPath + STORAGE_BRANDICON;
	}

	public String getCacheReportPath() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			throw new AccountNotReadyException();
		}
		return cachePath + STORAGE_REPORT;
	}

	public String getDbPath() {
		return accPath + ConstantsStorage.DB_NAME;
	}

	public String getCacheDbPath() {
		return cachePath + ConstantsStorage.DB_NAME;
	}

	public String getCacheEnDbPath() {
		return cachePath + ConstantsStorage.ENDB_NAME;
	}

	public String getCachePath() {
		return cachePath;
	}

	public String getAccPath() {
		return accPath;
	}

	public void release() {
		closeDB();
		reset();
	}

	public void reset() {
		uin = ConstantsProtocal.MM_INVALID_UIN;
		sp.edit().putBoolean("isLogin", false).commit();
		Log.w(TAG, "account storage reset");
	}

	public void erase() {
		if (uin == ConstantsProtocal.MM_INVALID_UIN) {
			return;
		}
		/**
		 * need not to delete table
		 */
		// autoMoveStg.reset();
		// contactStg.deleteFolderTable();
		// msgStg.deleteFolderTable();
		// convStg.deleteFolderTable();
		// imgStg.deleteFolderTable();
		// voiceStg.deleteFolderTable();
		// imgFlagStg.deleteFolderTable();
		// videoinfoStg.deleteFolderTable();
		// qqListStg.deleteFolderTable();
		// qqGroupStg.deleteFolderTable();
		// addrUploadStg.deleteFolderTable();
		// verifyContactStg.deleteFolderTable();
		// tcontactStg.deleteFolderTable();
		// qcontactStg.deleteFolderTable();
		// friendextStg.deleteFolderTable();
		// hdheadimginfoStg.deleteFolderTable();
		// throwbottleinfoStg.deleteFolderTable();
		// themeinfoStg.deleteFolderTable();
		// invitefriendopenStg.deleteFolderTable();
		// vuserinfoStg.deleteFolderTable();
		// packageinfoStg.deleteFolderTable();
		// chattingbginfoStg.deleteFolderTable();

		FileOperation.deleteDir(new File(accPath));
		FileOperation.deleteDir(new File(cachePath));
		//
		reset();
	}

	public void dumpDB() {
		final String uinPath = MD5.getMessageDigest(("mm" + uin).getBytes());
		cachePath = ConstantsStorage.DATAROOT_MOBILEMEM_PATH + uinPath + "/";
		final String dbPath = ConstantsStorage.DATAROOT_SDCARD_PATH + uinPath + "/";
		FileOperation.deleteFile(dbPath + ConstantsStorage.ENDB_NAME + ".dump");
		FileOperation.appendBuf(dbPath, ConstantsStorage.ENDB_NAME + ".dump", "", FileOperation.readFromFile(cachePath + ConstantsStorage.ENDB_NAME, 0, -1));

		FileOperation.deleteFile(dbPath + ConstantsStorage.ENDB_NAME + ".dumptmp");
		FileOperation.appendBuf(dbPath, ConstantsStorage.ENDB_NAME + ".dumptmp", "", FileOperation.readFromFile(cachePath + ConstantsStorage.DB_NAME + ".tem", 0, -1));
	}
	
	public void dumpReportDir() {
		final String uinPath = MD5.getMessageDigest(("mm" + uin).getBytes());
		cachePath = ConstantsStorage.DATAROOT_MOBILEMEM_PATH + uinPath + "/";
		final String reportPath = ConstantsStorage.DATAROOT_SDCARD_PATH + uinPath + "/" + "dump_" + STORAGE_REPORT;
		FileOperation.deleteDir(new File(reportPath));
		FilesCopy.copy(cachePath + STORAGE_REPORT, reportPath, false);
	}

	/**
	 * SQLiteFactory SQLs
	 */
	private static HashMap<Integer, IFactory> baseDBFactories = new HashMap<Integer, IFactory>();

	static {
		baseDBFactories.put("MESSAGE_TABLE".hashCode(), new IFactory() {
			@Override
			public String[] getSQLs() {
				return MsgInfoStorage.SQL_CREATE;
			}
		});
	}
}