package com.mini.mn.db.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Looper;

import com.mini.mn.algorithm.LRUMap;
import com.mini.mn.constant.ConstantsProtocal;
import com.mini.mn.db.SqliteDB;
import com.mini.mn.platformtools.TimeLogger;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

public class MsgInfoStorage extends MStorage {
	private static final String TAG = "MiniMsg.MsgInfoStorage";
	
	public static final int FILTER_MMSG = 0x1;
	public static final int FILTER_QMSG = 0x2;
	public static final int FILTER_TMSG = 0x4;
	public static final int FILTER_BOTTLEMSG = 0x8;
	public static final int FILTER_MEISHIMSG = 0x16;
	public static final int FILTER_MSG_ALL = FILTER_MMSG | FILTER_QMSG | FILTER_TMSG | FILTER_BOTTLEMSG | FILTER_MEISHIMSG;
	
	public interface IMsgInfoExtension {
		String[] createTableSQLs();
		
		MsgTable msgTable();
	}
	
	public static class NotifyInfo {
		public String talker;
		public String func;
//		public MsgInfo msg;
		public ArrayList<MsgInfo> msgList = new ArrayList<MsgInfo>();
		public int insertCount;

		public NotifyInfo(String t, String f, MsgInfo m) {
			this(t, f, m, 0);
		}
		
		public NotifyInfo(String t, String f, MsgInfo m, int inCount) {
			talker = t;
			func = f;
			insertCount = inCount;
			if (null != m) {
				msgList.add(m);
			}
		}
		
		boolean shouldAddConversationUnread(MsgInfo currentMsg) {
			return (null != currentMsg && MsgInfo.RECEIVER == currentMsg.getIsSend() && currentMsg.getStatus() == MsgInfo.STATE_REACH);
			// && msg.getType() != ConstantsProtocal.MM_DATA_SYS);
		}
	}

	public interface IOnMsgChange {
		void onNotifyChange(MsgInfoStorage msgstg, NotifyInfo notifyInfo);
	}

	private List<MsgTable> lstTable;

	// private static int msgID = 1;
	public static final int MAIL_CONTENT_CACHE_SIZE = 100;
	public static final int VERIFY_CONTENT_CACHE_SIZE = 100;
	public static final int FRIEND_CONTENT_CACHE_SIZE = 100;
	public static final int LOCATION_CONTENT_CACHE_SIZE = 100;

	private final LRUMap<Integer, MsgInfo.MailContent> cachesForMail = new LRUMap<Integer, MsgInfo.MailContent>(MAIL_CONTENT_CACHE_SIZE);
	private final LRUMap<Integer, MsgInfo.VerifyContent> cachesForVerify = new LRUMap<Integer, MsgInfo.VerifyContent>(VERIFY_CONTENT_CACHE_SIZE);
	private final LRUMap<Integer, MsgInfo.FriendContent> cachesForFriend = new LRUMap<Integer, MsgInfo.FriendContent>(FRIEND_CONTENT_CACHE_SIZE);
	private final LRUMap<Integer, MsgInfo.LocationContent> cachesForLocation = new LRUMap<Integer, MsgInfo.LocationContent>(LOCATION_CONTENT_CACHE_SIZE);
	private final SqliteDB db;

	public static final String[] SQL_CREATE = new String[] {
			// MMSG_TABLE 同时创建索引
			"CREATE TABLE IF NOT EXISTS " + MsgInfo.MMSG_TABLE + " ( "
					+ MsgInfo.COL_MSGID + " INTEGER PRIMARY KEY, "
					+ MsgInfo.COL_MSGSVRID + " INTEGER , "
					+ MsgInfo.COL_TYPE + " INT, "
					+ MsgInfo.COL_STATUS + " INT, "
					+ MsgInfo.COL_ISSEND + " INT, "
					+ MsgInfo.COL_ISSHOWTIMER + " INTEGER, "
					+ MsgInfo.COL_CREATETIME + " INTEGER, "
					+ MsgInfo.COL_TALKER + " TEXT, "
					+ MsgInfo.COL_CONTENT + " TEXT, "
					+ MsgInfo.COL_IMGPATH + " TEXT, "
					+ MsgInfo.COL_RESERVED + " TEXT, "
					+ MsgInfo.COL_LVBUFFER + " BLOB )",
					
	    	"CREATE INDEX IF NOT EXISTS " + " messageIdIndex ON " + MsgInfo.MMSG_TABLE + " ( " + MsgInfo.COL_MSGID + " )",

			"CREATE INDEX IF NOT EXISTS " + " messageSvrIdIndex ON " + MsgInfo.MMSG_TABLE + " ( " + MsgInfo.COL_MSGSVRID + " )",

			"CREATE INDEX IF NOT EXISTS " + " messageTalkerIndex ON " + MsgInfo.MMSG_TABLE + " ( " + MsgInfo.COL_TALKER + " )" ,
			
			"CREATE INDEX IF NOT EXISTS " + " messageTalkerStatusIndex ON " + MsgInfo.MMSG_TABLE + " ( " + MsgInfo.COL_TALKER + "," + MsgInfo.COL_STATUS + " )" ,

			"CREATE INDEX IF NOT EXISTS " + " messageCreateTimeIndex ON " + MsgInfo.MMSG_TABLE + " ( " + MsgInfo.COL_CREATETIME + " )" ,
			
			"CREATE INDEX IF NOT EXISTS " + " messageCreateTaklerTimeIndex ON " + MsgInfo.MMSG_TABLE + " ( " + MsgInfo.COL_TALKER + "," + MsgInfo.COL_CREATETIME + " )" ,
			
			"CREATE INDEX IF NOT EXISTS " + " messageSendCreateTimeIndex ON " + MsgInfo.MMSG_TABLE + " ( " + MsgInfo.COL_STATUS + "," + MsgInfo.COL_ISSEND + "," + MsgInfo.COL_CREATETIME + " )" ,
	};

	// message event
	private final MStorageEvent<IOnMsgChange, NotifyInfo> msgNotifier = new MStorageEvent<IOnMsgChange, NotifyInfo>() {

		@Override
		protected void processEvent(final IOnMsgChange listener, final NotifyInfo notifyInfo) {
			listener.onNotifyChange(MsgInfoStorage.this, notifyInfo);
		}
	};

	private void addEvent(final NotifyInfo notifyInfo) {
		if (msgNotifier.event(notifyInfo)) {
			msgNotifier.doNotify();
		}
	}

	public void addMsgListener(final IOnMsgChange listener, final Looper looper) {
		msgNotifier.add(listener, looper);
	}
	
	public void removeMsgListener(final IOnMsgChange listener) {
		msgNotifier.remove(listener);
	}

	@Override
	public void lock() {
		super.lock();
		msgNotifier.lock();
	}

	@Override
	public void unlock() {
		super.unlock();
		msgNotifier.unlock();
	}

	private boolean lockForSync = false;
	private Map<String, NotifyInfo> mapNotifyInfo = new HashMap<String, NotifyInfo>();

	public void lockForSync() {
		lockForSync = true;
		lock();
	}

	public void unlockForSync() {
		lockForSync = false;
		for (String user : mapNotifyInfo.keySet()) {
			addEvent(mapNotifyInfo.get(user));
		}
		mapNotifyInfo.clear();
		unlock();
		doNotify();
	}

	
	private void tryAddDBCol(final SqliteDB db  , String table){

		boolean hasLvBufCol = false;
		
		final Cursor cu = db.rawQuery("PRAGMA table_info( " + table +  " )", null);
		while (cu.moveToNext()) {
			int nameColIdx = cu.getColumnIndex("name");
			if (nameColIdx >= 0) {
				String colName = cu.getString(nameColIdx);
				if (MsgInfo.COL_LVBUFFER.equalsIgnoreCase(colName)) {
					hasLvBufCol = true;
				}
			}
		}
		cu.close();
		if (!hasLvBufCol) {
			String sql = "Alter table " +  table + " add " + MsgInfo.COL_LVBUFFER + " BLOB ";
			db.execSQL(table , sql);
		}
	}
	
	public MsgInfoStorage(final SqliteDB db) {
		super();
		this.db = db;

		tryAddDBCol(db,MsgInfo.MMSG_TABLE );
		
		if (lstTable == null) {
			lstTable = new LinkedList<MsgTable>();
		}
		lstTable.clear();
		lstTable.add(new MsgTable(FILTER_MMSG, MsgInfo.MMSG_TABLE, MsgInfo.MMSG_ID_MIN_VALUE, MsgInfo.MMSG_ID_MAX_VALUE));

		for (int i = 0; i < lstTable.size(); i++) {
			final Cursor cu = this.db.rawQuery("select max(msgid) from " + lstTable.get(i).getName(), null);
			if (cu.moveToFirst()) {
				final int id = cu.getInt(0);
				// set if larger : bugfix
				if (id >= lstTable.get(i).getMsgLocalId()) {
					lstTable.get(i).setMsgLocalId(id + 1);
				}
			}
			cu.close();
			Log.w(TAG, "loading new msg id:" + lstTable.get(i).getMsgLocalId());
		}
	}

	// 通过本地id获取
	public MsgInfo getById(final long id) {
		final MsgInfo msg = new MsgInfo();
		final Cursor cu = db.query(getTableNameByLocalId(id), null, MsgInfo.COL_MSGID + "=?", new String[] { "" + id },
				null, null, null);
		if (cu.getCount() != 0) {
			cu.moveToFirst();
			msg.convertFrom(cu);
		}
		cu.close();
		return msg;
	}

	// 通过本地id获取
	public MsgInfo getBySvrId(final String talker, final int uid) {
		final MsgInfo msg = new MsgInfo();
		final Cursor cu = db.query(getTableNameByTalker(), null, MsgInfo.COL_MSGSVRID + "=?", new String[] { ""
				+ uid }, null, null, null);
		if (cu.getCount() != 0) {
			cu.moveToFirst();
			msg.convertFrom(cu);
		}
		cu.close();
		return msg;
	}
	
	public MsgInfo getByCreateTime(final String talker, final long time) {
		final MsgInfo msg = new MsgInfo();
		final Cursor cu = db.query(getTableNameByTalker(), null, MsgInfo.COL_CREATETIME + "=?", new String[] { ""
				+ time }, null, null, null);
		if (cu.getCount() != 0) {
			cu.moveToFirst();
			msg.convertFrom(cu);
		}
		cu.close();
		return msg;
	}

	public boolean existMsgBySvrId(final String talker, final int uid) {
		MsgInfo msg = getBySvrId(talker, uid);
		Log.d(TAG,"msg.getMsgSvrId() "+msg.getMsgSvrId());
		return (msg != null) && (msg.getMsgSvrId() > 0);
	}
	
	public boolean existMsgByCreateTime(final String talker, final long time) {
		MsgInfo msg = getByCreateTime(talker, time);
		return (msg != null) && (msg.getMsgId() > 0);
	}
	public int getPositionByCreateTime(final String talker, final long time,int from) {
		final String sql = "SELECT * FROM " + getTableNameByTalker() + " WHERE " + MsgInfo.COL_TALKER + "= '"
			+ Util.escapeSqlValue(talker) + "' AND "+MsgInfo.COL_CREATETIME+" < "+time+" ORDER BY " + MsgInfo.COL_CREATETIME + " ASC LIMIT -1 OFFSET " + from;

		
		 final Cursor cursor = db.rawQuery(sql, null);
		 int count  = 0;
		 count = cursor.getCount();
		 cursor.close();
		 
		 Log.d(TAG, "getPositionByCreateTime talk:" + talker + " time:" + time + " count "+count+" [" + sql + "]");
		 
		 return count;
	}

	/*
	 * @获取talker的最后一条服务器消息
	 */
	public MsgInfo getLastSvrMsg(final String talker) {
		// CodeInfo.TestTime t = new CodeInfo.TestTime();
		final MsgInfo msg = new MsgInfo();
		final Cursor cu = db.query(getTableNameByTalker(), null, MsgInfo.COL_TALKER + "=?", new String[] { ""
				+ talker }, null, null, MsgInfo.COL_MSGSVRID + "  DESC limit 1 ");
		if (cu.getCount() != 0) {
			cu.moveToFirst();
			msg.convertFrom(cu);
		}
		cu.close();
		// Log.d(TAG, "getLastSvrMsg t: " + t.GetDiff());
		return msg;
	}

	/*
	 * @获取talker按时间排序的最后一条消息
	 */
	public MsgInfo getLastMsg(final String talker) {
		if (Util.isNullOrNil(talker)) {
			return null;
		}
		// CodeInfo.TestTime t = new CodeInfo.TestTime();
		final MsgInfo msg = new MsgInfo();
		final String sql = "select * from " + getTableNameByTalker() + " where " + MsgInfo.COL_TALKER + " = '"
				+ Util.escapeSqlValue(talker) + "'  order by " + MsgInfo.COL_CREATETIME + " DESC limit 1";
		final Cursor cu = db.rawQuery(sql, null);

		if (cu.getCount() != 0) {
			cu.moveToFirst();
			msg.convertFrom(cu);
		}
		cu.close();
		// Log.d(TAG, "getLastMsg t: " + t.GetDiff());
		return msg;
	}
	
	// filter sys-msgs, not filtering hardcode sys-msgs
	public static final String FILTER_MSG_WITHOUT_SYS = " and not ( " + MsgInfo.COL_TYPE + " = " + ConstantsProtocal.MM_DATA_SYS
			+ " and " + MsgInfo.COL_ISSEND + " != " + MsgInfo.HARD_CODE + " ) ";
	/*
	 * @获取talker按时间排序和filter选择后的最后一条消息
	 */
	public MsgInfo getLastMsg(final String talker, final String filterSql) {
		if (Util.isNullOrNil(talker)) {
			return null;
		}
		// CodeInfo.TestTime t = new CodeInfo.TestTime();
		final MsgInfo msg = new MsgInfo();
		final String sql = "select * from " + getTableNameByTalker() + " where " + MsgInfo.COL_TALKER + " = '"
				+ Util.escapeSqlValue(talker) + "' " + filterSql + " order by " + MsgInfo.COL_CREATETIME + " DESC limit 1";
		final Cursor cu = db.rawQuery(sql, null);

		if (cu.getCount() != 0) {
			cu.moveToFirst();
			msg.convertFrom(cu);
		}
		cu.close();
		// Log.d(TAG, "getLastMsg t: " + t.GetDiff());
		return msg;
	}
	
	/*
	 * @获取talker按时间排序的最后一条接收到的消息
	 */
	public MsgInfo getLastRecivedMsg(final String talker) {
		if (Util.isNullOrNil(talker)) {
			return null;
		}
		// CodeInfo.TestTime t = new CodeInfo.TestTime();
		final MsgInfo msg = new MsgInfo();
		final String sql = "select * from " + getTableNameByTalker() + " where " + MsgInfo.COL_TALKER + " = '"
				+ Util.escapeSqlValue(talker) + "' and" + " " + MsgInfo.COL_ISSEND + " = " + MsgInfo.RECEIVER
				+ "  order by " + MsgInfo.COL_CREATETIME + " DESC limit 1";
		final Cursor cu = db.rawQuery(sql, null);

		if (cu.getCount() != 0) {
			cu.moveToFirst();
			msg.convertFrom(cu);
		}
		cu.close();
		// Log.d(TAG, "getLastMsg t: " + t.GetDiff());
		return msg;
	}
	
	public MsgInfo getLastMsg(final int filterVal) {
		Assert.assertTrue(lstTable != null);

		MsgInfo msg = new MsgInfo();
		long createTime = 0;
		String sql = "";
		Cursor cu = null;

		for (int i = 0; i < lstTable.size(); i++) {
			if ((lstTable.get(i).getBitVal() & filterVal) == 0) {
				continue;
			}

			sql = "select * from " + lstTable.get(i).getName() + "  order by " + MsgInfo.COL_CREATETIME
					+ " DESC limit 1";
			cu = db.rawQuery(sql, null);

			if (cu.getCount() != 0) {
				cu.moveToFirst();
				if (createTime < cu.getLong(MsgInfo.INDEX_CREATE_TIME)) {
					createTime = cu.getLong(MsgInfo.INDEX_CREATE_TIME);
					msg.convertFrom(cu);
				}
			}
			cu.close();
		}
		return msg;
	}
	
	public MsgInfo getLastMsg(final int filterVal, String filterSql) {
		Assert.assertTrue(lstTable != null);

		MsgInfo msg = new MsgInfo();
		long createTime = 0;
		String sql = "";
		Cursor cu = null;

		if (Util.isNullOrNil(filterSql)) {
			filterSql = "";
		} else {
			filterSql = filterSql.replaceFirst("and", "where");
		}
		
		for (int i = 0; i < lstTable.size(); i++) {
			if ((lstTable.get(i).getBitVal() & filterVal) == 0) {
				continue;
			}

			sql = "select * from " + lstTable.get(i).getName() + filterSql + "  order by " + MsgInfo.COL_CREATETIME
					+ " DESC limit 1";
			cu = db.rawQuery(sql, null);

			if (cu.getCount() != 0) {
				cu.moveToFirst();
				if (createTime < cu.getLong(MsgInfo.INDEX_CREATE_TIME)) {
					createTime = cu.getLong(MsgInfo.INDEX_CREATE_TIME);
					msg.convertFrom(cu);
				}
			}
			cu.close();
		}
		return msg;
	}

	/*
	 * public long getEarliestUnreadEmojiMsgId(final String talker) { String sql = "select min(" + MsgInfo.COL_ID + ") from " + getTableNameByTalker() + " where " + MsgInfo.COL_TALKER + " = '" + Util.escapeSqlValue(talker) + "' AND "
	 * + MsgInfo.COL_TYPE + " = " + ConstantsProtocal.MM_DATA_EMOJI + " AND " + MsgInfo.COL_CONTENT + " = '" + EmojiInfo.GAME_NOT_PLAY + "'";
	 * 
	 * long id = 0; Cursor cu = db.rawQuery(sql, null); if (cu.getCount() == 1) { cu.moveToFirst(); id = cu.getLong(0); } cu.close();
	 * 
	 * if (id == 0) { MsgInfo msg = getLastMsg(talker); if (msg != null) { id = msg.getMsgId() + 1; } }
	 * 
	 * return id; }
	 */

	public int getUnreadCount(final int filterVal, final long fromTime) {
		int unReadCount = 0;

		String sql = "";
		Cursor cu = null;

		for (int i = 0; i < lstTable.size(); i++) {
			if ((lstTable.get(i).getBitVal() & filterVal) == 0) {
				continue;
			}

			sql = "select * " + " from " + lstTable.get(i).getName() + " where " + lstTable.get(i).getName() + "."
					+ MsgInfo.COL_STATUS + " != " + MsgInfo.STATE_READ + " and " + lstTable.get(i).getName() + "."
					+ MsgInfo.COL_ISSEND + " = " + MsgInfo.RECEIVER + " and " + lstTable.get(i).getName() + "."
					+ MsgInfo.COL_CREATETIME + " > " + fromTime;

			// Log.d(TAG, "getUnReadCount : sql = " + sql);
			cu = db.rawQuery(sql, null);
			unReadCount += cu.getCount();
			cu.close();
		}

		return unReadCount;
	}

	public void setErrorOldSendMsg() {
		Assert.assertTrue(lstTable != null);

		final long cOldMsgPeriod = 10 * 60 * 1000;
		long tOld = Util.nowMilliSecond() - cOldMsgPeriod;

		HashSet<String> setTalker = new HashSet<String>();
		for (int i = 0; i < lstTable.size(); i++) {
			String sql = "select " + MsgInfo.COL_TALKER + " from " + lstTable.get(i).getName() + " where "
					+ MsgInfo.COL_CREATETIME + "<" + tOld + " and " + MsgInfo.COL_STATUS + "=" + MsgInfo.STATE_SENDING;
			Cursor cu = db.rawQuery(sql, null);

			if (cu.moveToFirst()) {
				while (!cu.isAfterLast()) {
					String t = cu.getString(0);
					cu.moveToNext();
					if (!Util.isNullOrNil(t)) {
						setTalker.add(t);
					}
				}
			}
			cu.close();
			sql = "update " + lstTable.get(i).getName() + " set " + MsgInfo.COL_STATUS + "=" + MsgInfo.STATE_FAILED
					+ " where " + MsgInfo.COL_CREATETIME + "<" + tOld + " and " + MsgInfo.COL_STATUS + "="
					+ MsgInfo.STATE_SENDING;
			cu = db.rawQuery(sql, null);

			// Log.d(TAG, "sql [" + sql + "] cu:" + cu.getCount() + " " + cu.getColumnCount());
			cu.close();
		}
		for (Iterator<String> t = setTalker.iterator(); t.hasNext();) {
			doNotify();
			addEvent(new NotifyInfo(t.next(), "update", null));
		}
	}

	// 当前正在发送中的几条消息
	public List<MsgInfo> getLastSendingMsgList() {
		setErrorOldSendMsg();
		final List<MsgInfo> listMsg = new ArrayList<MsgInfo>();
		Assert.assertTrue(lstTable != null);

		Cursor cu = null;
		for (int i = 0; i < lstTable.size(); i++) {
			cu = db.query(lstTable.get(i).getName(), null, MsgInfo.COL_STATUS + "=" + MsgInfo.STATE_SENDING + " and "
					+ MsgInfo.COL_ISSEND + "=" + MsgInfo.SENDER, null, null, null, MsgInfo.COL_CREATETIME + " DESC ");
			if (cu.moveToFirst()) {
				while (!cu.isAfterLast()) {
					final MsgInfo msg = new MsgInfo();
					msg.convertFrom(cu);
					cu.moveToNext();
					if (msg.isText() || msg.isFriendCard() || msg.isLocation() || msg.isBrandQAMsg()) {
						listMsg.add(msg);
					}
				}
			}

			cu.close();
		}
		return listMsg;
	}

	public List<MsgInfo> getLastSendingEmojiMsgList() {
		setErrorOldSendMsg();
		final List<MsgInfo> listMsg = new ArrayList<MsgInfo>();
		Assert.assertTrue(lstTable != null);

		Cursor cu = null;
		for (int i = 0; i < lstTable.size(); i++) {
			cu = db.query(lstTable.get(i).getName(), null, MsgInfo.COL_STATUS + "=" + MsgInfo.STATE_SENDING + " and "
					+ MsgInfo.COL_ISSEND + "=" + MsgInfo.SENDER, null, null, null, MsgInfo.COL_CREATETIME + " DESC ");
			if (cu.moveToFirst()) {
				while (!cu.isAfterLast()) {
					final MsgInfo msg = new MsgInfo();
					msg.convertFrom(cu);
					cu.moveToNext();
					if (msg.isEmoji()) {
						listMsg.add(msg);
					}
				}
			}

			cu.close();
		}

		return listMsg;
	}

	public List<MsgInfo> getLastRecvMsg(String talker, int limit) {
		final List<MsgInfo> listMsg = new ArrayList<MsgInfo>();
		Assert.assertTrue(lstTable != null);

		Cursor cu = null;
		final String sql = "SELECT * FROM " + getTableNameByTalker() + " WHERE " + MsgInfo.COL_TALKER + " = '"
				+ Util.escapeSqlValue(talker) + "' " + " AND " + MsgInfo.COL_ISSEND + " = " + MsgInfo.RECEIVER
				+ " ORDER BY " + MsgInfo.COL_CREATETIME + " DESC LIMIT " + limit;

		cu = db.rawQuery(sql, null);
		if (cu.moveToFirst()) {
			while (!cu.isAfterLast()) {
				final MsgInfo msg = new MsgInfo();
				msg.convertFrom(cu);
				cu.moveToNext();
				if (msg.isText()) {
					listMsg.add(msg);
				}
			}
		}
		cu.close();
		return listMsg;
	}

	/**
	 * 可以参考com.tencent.mm.modelmulti.NetSceneSendMsg类
	 * final MsgInfo msg = new MsgInfo();</br>
	 * msg.setStatus(MsgInfo.STATE_SENDING);</br>
	 * msg.setTalker(toUserName);</br>
	 * msg.setCreateTime(MsgInfoStorageLogic.fixSendMsgCreateTime(toUserName));</br>
	 * msg.setIsSend(MsgInfo.SENDER);</br>
	 * msg.setContent(content);</br>
	 * msg.setType(ContactStorageLogic.getTypeTextFromUserName(toUserName));</br>
	 * @return 本地自动生产的id
	 */
	public long insert(final MsgInfo message) {

		// 插入一条消息时，可能发送人还没有(创建多人会话时)，故要先进行判断.
		if (message.getTalker() != null && message.getTalker().length() > 0) {

			MsgTable table = getTableByTalker();
			Assert.assertTrue(table != null);
			message.setMsgId(table.getMsgLocalId());
			table.incMsgLocalId(); // 插入一条新消息，必须自增msg local id

			message.setConvertFlag(MsgInfo.FLAG_ALL);
			final ContentValues values = message.convertTo();
			Log.v(TAG, "dkevent insert: talker=" + message.getTalker() + " localId=" + message.getMsgId() + " thr:"
					+ Thread.currentThread().getId());
			final long result = db.insert(table.getName(), MsgInfo.COL_MSGID, values);
			if (result != -1) {
				if (lockForSync) {
					NotifyInfo info = null;
					if (mapNotifyInfo.containsKey(message.getTalker())) {
						info = mapNotifyInfo.get(message.getTalker());
					}
					if (info == null) {
						info = new NotifyInfo(message.getTalker(), "insert", message);
					} else {
						info.msgList.add(message);
					}
					if (info.shouldAddConversationUnread(message)) {
						info.insertCount++;
					}
					mapNotifyInfo.put(message.getTalker(), info);
				} else {
					NotifyInfo info = new NotifyInfo(message.getTalker(), "insert", message);
					if (info.shouldAddConversationUnread(message)) {
						info.insertCount = 1;
					}
					doNotify();
					addEvent(info);
				}
				return message.getMsgId();
			}
		}
		return -1;
	}

	public int deleteByID(final long id) {
		final String talker = getById(id).getTalker();
		final int result = db.delete(getTableNameByLocalId(id), MsgInfo.COL_MSGID + "=?", new String[] { "" + id });
		if (result != 0) {
			doNotify();
			addEvent(new NotifyInfo(talker, "delete", null));
		}
		return result;
	}

	public void batchDeleteMsg(List<Long> msgIdList) {
		if (msgIdList == null || msgIdList.size() == 0) {
			return;
		}

		TimeLogger logger = new TimeLogger(TAG, "batchDeleteMsg");
		logger.addSplit("transation begin");
		long ticket = db.beginTransaction(Thread.currentThread().getId());

		for (int i = 0; i < msgIdList.size(); i++) {
			deleteByID(msgIdList.get(i));
		}
		db.endTransaction(ticket);
		logger.addSplit("transation end");
		logger.dumpToLog();
	}

	public List<MsgInfo> getAllMessage(String table) {
		Cursor cu = db.rawQuery("select * from " + table, null);
		if (cu == null) {
			return null;
		}
		int count = cu.getCount();
		if (count == 0) {
			cu.close();
			return null;
		}
		List<MsgInfo> lst = new ArrayList<MsgInfo>();
		for (int i = 0; i < count; i++) {
			cu.moveToPosition(i);
			MsgInfo info = new MsgInfo();
			info.convertFrom(cu);
			lst.add(info);
		}
		cu.close();
		return lst;
	}

	/**
	 * Note：返回的List包含三种图片类型，普通图片、qq离线消息图片和私信图片
	 */
	public List<MsgInfo> getImgMessage(final String talker, long msgLocalId, int limit, boolean forward) {
		if (talker == null || talker.length() == 0 || limit <= 0) {
			Log.e(TAG, "getImgMessage fail, argument is invalid, limit = " + limit);
			return null;
		}

		MsgInfo currentMsg = getById(msgLocalId);
		if (currentMsg == null || currentMsg.getMsgId() == 0) {
			Log.e(TAG, "getImgMessage fail, msg is null");
			return null;
		}

		long currentCreateTime = currentMsg.getCreateTime();
		List<MsgInfo> imgMsgList = new ArrayList<MsgInfo>();

		String sql = null;
		if (forward) {
			sql = "select * from " + getTableNameByTalker() + " where " + MsgInfo.COL_TALKER + " = '"
					+ Util.escapeSqlValue(talker) + "' AND "
					+ getImgTypeSql()
					+ " AND " + MsgInfo.COL_CREATETIME + " > " + currentCreateTime + "  order by "
					+ MsgInfo.COL_CREATETIME + " ASC limit " + limit;
		} else {
			sql = "select * from " + getTableNameByTalker() + " where " + MsgInfo.COL_TALKER + " = '"
					+ Util.escapeSqlValue(talker) + "' AND "
					+ getImgTypeSql()
					+ " AND " + MsgInfo.COL_CREATETIME + " < " + currentCreateTime + "  order by "
					+ MsgInfo.COL_CREATETIME + " DESC limit " + limit;
		}

		final Cursor cu = db.rawQuery(sql, null);
		if (cu.moveToFirst()) {
			while (!cu.isAfterLast()) {
				final MsgInfo msg = new MsgInfo();
				msg.convertFrom(cu);
				cu.moveToNext();

				if (forward) {
					imgMsgList.add(msg);
				} else {
					imgMsgList.add(0, msg);
				}
			}
		}

		cu.close();
		return imgMsgList;
	}

	public void deleteAllMessage(String table) {
		final boolean result = db.execSQL(table, "delete from " + table);
		if (result) {
			doNotify();
		}
	}

	// 根据服务器id删除
	public int deleteBySvrID(final String talker, final int uid) {
		final int result = db.delete(getTableNameByTalker(), MsgInfo.COL_MSGSVRID + "=?", new String[] { "" + uid });
		if (result != 0) {
			doNotify();
			addEvent(new NotifyInfo(talker, "delete", null));
		}

		return result;
	}

	// 根据本地talker删除 (仅供ConversationStg.delContactMsg or delChatContact调用)
	public int deleteByTalker(final String talker) {
		final int result = db.delete(getTableNameByTalker(), MsgInfo.COL_TALKER + "=?", new String[] { ""
				+ talker });
		if (result != 0) {
			doNotify();
			addEvent(new NotifyInfo(talker, "delete", null));
		}
		return result;
	}

	public int deleteByTalkerFrom(final String talker, final int maxMsgId) {
		final MsgInfo msg = getBySvrId(talker, maxMsgId);
		Assert.assertTrue(talker.equals(msg.getTalker()));
		final int result = db.delete(getTableNameByTalker(), MsgInfo.COL_CREATETIME + "<=? AND "
				+ MsgInfo.COL_TALKER + "=?", new String[] { "" + msg.getCreateTime(), talker });
		if (result != 0) {
			doNotify();
			addEvent(new NotifyInfo(talker, "delete", null));
		}
		return result;
	}

	public boolean deleteMessageEndByName(String nameTag) {
		final boolean result = db.execSQL(getTableNameByTalker(), "delete from " + getTableNameByTalker()
				+ " where talker like '%" + nameTag + "'");
		if (result) {
			doNotify();
		}
		return result;
	}

	public Cursor getByTalkFrom(final String talker, final int maxMsgId) {
		final MsgInfo msg = getBySvrId(talker, maxMsgId);
		Assert.assertTrue(talker.equals(msg.getTalker()));
		return db.query(getTableNameByTalker(), null, MsgInfo.COL_CREATETIME + "<=? AND " + MsgInfo.COL_TALKER
				+ "=?", new String[] { "" + msg.getCreateTime() }, null, null, null);
	}

	public void deleteFolderTable() {
		Assert.assertTrue(lstTable != null);
		for (int i = 0; i < lstTable.size(); i++) {
			db.drop(lstTable.get(i).getName());
		}
	}

	/**
	 * 通过ID更新消息
	 * @param id
	 * @param msg
	 */
	public void updateById(final long id, final MsgInfo msg) {
		final int result = db.update(getTableNameByLocalId(id), msg.convertTo(), MsgInfo.COL_MSGID + "=?",
				new String[] { "" + id });
		if (result != 0) {
			doNotify();
			addEvent(new NotifyInfo(msg.getTalker(), "update", msg));
		}
	}
	
	/**
	 * setMsgReadedById() 将消息设为已读。
	 * 要保证参数msg的state为未读，此方法msg的状态设为MsgInfo.STATE_READ并更新数据库
	 * @param msg
	 */
	public void setMsgReadedById(MsgInfo msg) {
		if (msg == null || msg.getStatus() == MsgInfo.STATE_READ) {
			return;
		}
		msg.setStatus(MsgInfo.STATE_READ);
		String tableName = getTableNameByLocalId(msg.getMsgId());
		if (tableName != null && tableName.length()>0) {			
			final int result = db.update(tableName, msg.convertTo(), MsgInfo.COL_MSGID + "=?",
					new String[] { "" + msg.getMsgId() });
			if (result != 0) {
				doNotify();
				addEvent(new NotifyInfo(msg.getTalker(), "update", msg, -1));
			}
		}
	}

	public void updateBySvrId(final int uid, final MsgInfo msg) {
		Assert.assertTrue("no talker set when update by svrid", Util.nullAsNil(msg.getTalker()).length() > 0);
		final int result = db.update(getTableNameByTalker(), msg.convertTo(), MsgInfo.COL_MSGSVRID + "=?",
				new String[] { "" + uid });
		if (result != 0) {
			doNotify();
			addEvent(new NotifyInfo(msg.getTalker(), "update", msg));
		}
	}

	public int updateUnreadByTalker(final String talker) {
		final ContentValues values = new ContentValues();
		values.put(MsgInfo.COL_STATUS, MsgInfo.STATE_READ);
		final int result = db.update(getTableNameByTalker(), values, MsgInfo.COL_TALKER + "=? AND "
				+ MsgInfo.COL_ISSEND + "=? AND " + MsgInfo.COL_STATUS + "!=? ", new String[] { talker,
				"" + MsgInfo.RECEIVER, "" + MsgInfo.STATE_READ });
		if (result != 0) {
			doNotify();
			addEvent(new NotifyInfo(talker, "update", null));
		}

		return result;
	}

	public Cursor getCursor() {
		// return db.query("message", null, null, null, null, MsgInfo.COL_CREATE_TIME + " ASC ");
		return db.query("message", null, null, null, null, null, null);
	}

	public Cursor getCursor(final String talker) {
		return db.query(getTableNameByTalker(), null, MsgInfo.COL_TALKER + "=?", new String[] { talker }, null,
				null, MsgInfo.COL_CREATETIME + " ASC ");
	}
	
	public Cursor getCursor(final String talker, final String filter){
		String sql = "SELECT * FROM " + getTableNameByTalker() + " WHERE " + MsgInfo.COL_TALKER + "= '" + Util.escapeSqlValue(talker) 
				+ "' AND " + MsgInfo.COL_CONTENT + " LIKE '%" + filter + "%' AND " + MsgInfo.COL_TYPE + " = 1";
		sql += " ORDER BY "  + MsgInfo.COL_CREATETIME + " ASC";		
		return db.rawQuery(sql, null);
	}

	public Cursor getCursor(final String talker, final int from) {
		final String sql = "SELECT * FROM " + getTableNameByTalker() + " WHERE " + MsgInfo.COL_TALKER + "= '"
				+ Util.escapeSqlValue(talker) + "' ORDER BY " + MsgInfo.COL_CREATETIME + " ASC LIMIT -1 OFFSET " + from;

		Log.d(TAG, "getCursor talk:" + talker + " from:" + from + " [" + sql + "]");
		return db.rawQuery(sql, null);
	}
	
	public Cursor getCursor(final String talker, final long msgLocalId, final long lastMsgId, final int limit, final int from, final boolean up){
		String sql = "SELECT * FROM " + getTableNameByTalker() + " WHERE "  + MsgInfo.COL_TALKER + "= '"
				+Util.escapeSqlValue(talker) + "' AND ";
		
		if (up) {
			sql += MsgInfo.COL_MSGID + " <= " + msgLocalId;
		}else {
			sql += MsgInfo.COL_MSGID + " >= " + msgLocalId + " AND " + MsgInfo.COL_MSGID +" <= " + lastMsgId ;
		}
		sql += " ORDER BY " + MsgInfo.COL_MSGID + " ASC LIMIT "+ limit + " OFFSET " + from;
		Log.d(TAG, "get cursor: " + sql);
		
		return db.rawQuery(sql, null);
	}

	// 取出在旧微信表中的数据
	public Cursor getFilterCursor(final String talkerSuffix) {
		final String sql = "SELECT * FROM " + MsgInfo.MMSG_TABLE + " WHERE " + MsgInfo.COL_TALKER + " like '%"
				+ Util.escapeSqlValue(talkerSuffix) + "' ORDER BY " + MsgInfo.COL_MSGID + " ASC";
		return db.rawQuery(sql, null);
	}

	public Cursor getCursorUnread(final String talker) {
		return db.query(getTableNameByTalker(), null, MsgInfo.COL_ISSEND + "=? AND " + MsgInfo.COL_TALKER
				+ "=? AND " + MsgInfo.COL_STATUS + "!=?", new String[] { "" + MsgInfo.RECEIVER, talker,
				"" + MsgInfo.STATE_READ }, null, null, null);
	}
	
	public Cursor getCursorUnread(final String talker, final int limitCount) {
		return db.query(getTableNameByTalker(), null, MsgInfo.COL_ISSEND + "=? AND " + MsgInfo.COL_TALKER
				+ "=? AND " + MsgInfo.COL_STATUS + "!=?" + " limit " + limitCount, new String[] { "" + MsgInfo.RECEIVER, talker,
			"" + MsgInfo.STATE_READ }, null, null, null);
	}
	
	public static final String COL_UNREAD_COUNT = "unReadCount";
	public Cursor getCursorUnreadMMSGGroupByUser() {
		return db.query( MsgInfo.MMSG_TABLE, new String[]{MsgInfo.COL_TALKER, "count(*) as " + COL_UNREAD_COUNT},
				MsgInfo.COL_ISSEND + "=? AND " + MsgInfo.COL_STATUS + "!=?", new String[] { "" + MsgInfo.RECEIVER, 
			"" + MsgInfo.STATE_READ }, MsgInfo.COL_TALKER, null, null);
	}
	
	public Cursor getCursorUnread(final String talker, final long afterTime) {
		return db.query(getTableNameByTalker(), null, MsgInfo.COL_ISSEND + "=? AND " + MsgInfo.COL_TALKER
				+ "=? AND " + MsgInfo.COL_STATUS + "!=? AND " + MsgInfo.COL_CREATETIME + ">=?", new String[] { "" + MsgInfo.RECEIVER, talker,
				"" + MsgInfo.STATE_READ, "" + afterTime }, null, null, null);
	}

	public MsgInfo.MailContent getMailContent(final String text) {
		MsgInfo.MailContent mail = cachesForMail.get(text.hashCode());
		if (mail == null) {
			mail = MsgInfo.MailContent.parse(text);
			cachesForMail.update(text.hashCode(), mail);
		}
		return mail;
	}

	public MsgInfo.VerifyContent getVerifyContent(final String text) {
		MsgInfo.VerifyContent verify = cachesForVerify.get(text.hashCode());
		if (verify == null) {
			verify = MsgInfo.VerifyContent.parse(text);
			cachesForVerify.update(text.hashCode(), verify);
		}
		return verify;
	}

	public MsgInfo.FriendContent getFriendContent(final String text) {
		MsgInfo.FriendContent friend = cachesForFriend.get(text.hashCode());
		if (friend == null) {
			friend = MsgInfo.FriendContent.parse(text);
			cachesForFriend.update(text.hashCode(), friend);
		}
		return friend;
	}

	public MsgInfo.LocationContent getLocationContent(final String text) {
		MsgInfo.LocationContent location = cachesForLocation.get(text.hashCode());
		if (location == null) {
			location = MsgInfo.LocationContent.parse(text);
			cachesForLocation.update(text.hashCode(), location);
		}
		return location;
	}

	public Cursor getEmptyCursor() {
		Assert.assertTrue(lstTable.size() > 0);
		return db.query(lstTable.get(0).getName(), null, MsgInfo.COL_MSGID + "=?", new String[] { "-1" }, null, null, null);
	}

	public int getMsgCount(String talker) {
		final String sql = "SELECT COUNT(*) FROM " + getTableNameByTalker() + " WHERE " + MsgInfo.COL_TALKER
				+ "='" + Util.escapeSqlValue(talker) + "'";
		final Cursor cu = db.rawQuery(sql, null);
		int count = 0;
		if (cu.moveToLast()) {
			count = cu.getInt(0);
		}
		cu.close();
		return count;
	}

	public int getMsgCount(String talker, int msgType) {
		final String sql = "SELECT COUNT(*) FROM " + getTableNameByTalker() + " WHERE " + MsgInfo.COL_TALKER
				+ "='" + Util.escapeSqlValue(talker) + "' AND " + MsgInfo.COL_TYPE + " = " + msgType;
		final Cursor cu = db.rawQuery(sql, null);
		int count = 0;
		if (cu.moveToLast()) {
			count = cu.getInt(0);
		}
		cu.close();
		return count;
	}
	
	public int getMsgCount(String talker, long localMsgId, boolean up){
		String sql = "SELECT COUNT(*) FROM " + getTableNameByTalker() + " WHERE " + MsgInfo.COL_TALKER
				+ "='" + Util.escapeSqlValue(talker) + "' AND " + MsgInfo.COL_MSGID;
		if (up) {
			sql += " <=" + localMsgId;
		}else {
			sql += " >=" + localMsgId;
		}
		final Cursor cu = db.rawQuery(sql, null);
		int count = 0;
		if (cu.moveToLast()) {
			count = cu.getInt(0);
		}
		cu.close();
		return count;
	}
	
	/**
	 * Note：返回的List包含三种图片类型，普通图片、qq离线消息图片和私信图片
	 */
	public int getImgMsgCount(String talker) {
		final String sql = "SELECT COUNT(*) FROM " + getTableNameByTalker() + " WHERE "
				+ MsgInfo.COL_TALKER + "='" + Util.escapeSqlValue(talker) + "' AND "
				+ getImgTypeSql();
				
		final Cursor cu = db.rawQuery(sql, null);
		int count = 0;
		if (cu.moveToLast()) {
			count = cu.getInt(0);
		}
		cu.close();
		return count;
	}

	/**
	 * Note：返回的List包含三种图片类型，普通图片、qq离线消息图片和私信图片
	 */
	public int getImgCountEarlyThan(String talker, long msgLocalId) {
		MsgInfo msg = getById(msgLocalId);
		if (msg == null || msg.getMsgId() == 0) {
			Log.e(TAG, "getCountEarlyThan fail, msg does not exist");
			return 0;
		}

		final String sql = "SELECT COUNT(*) FROM " + getTableNameByTalker() + " WHERE " + MsgInfo.COL_TALKER
				+ "='" + Util.escapeSqlValue(talker) + "' AND "
				+ getImgTypeSql()
				+ " AND " + MsgInfo.COL_CREATETIME + " < " + msg.getCreateTime();

		final Cursor cu = db.rawQuery(sql, null);
		int count = 0;
		if (cu.moveToLast()) {
			count = cu.getInt(0);
		}
		cu.close();
		return count;
	}
	
	private String getImgTypeSql() {
		return "(" + MsgInfo.COL_TYPE + " = " + ConstantsProtocal.MM_DATA_IMG
				+ " OR " + MsgInfo.COL_TYPE + " = " + ConstantsProtocal.MM_DATA_QQLIXIANMSG_IMG
				+ " OR " + MsgInfo.COL_TYPE + " = " + ConstantsProtocal.MM_DATA_PRIVATEMSG_IMG + ")";
	}

	public void deleteOldMsgByTalker(String talker, int secondLast, int leftMinCount) {
		/*
		 * String sql = "delete from " + MsgInfo.TABLE + " where " + MsgInfo.COL_ID + " in (" + "select " + MsgInfo.COL_ID + " from " + MsgInfo.TABLE + " where " + "( strftime(\"%s\",\"now\") - " + MsgInfo.COL_CREATE_TIME +"/1000  > " +
		 * secondLast +") " + "and talker=\"" + Util.escapeSqlValue(talker) + "\" order by " + MsgInfo.COL_CREATE_TIME + " desc limit -1 offset " + leftMinCount + ")  ";
		 */
		String sql = "select " + MsgInfo.COL_CREATETIME + " from " + getTableNameByTalker() + " where "
				+ "talker=\"" + Util.escapeSqlValue(talker) + "\" order by " + MsgInfo.COL_CREATETIME
				+ " desc limit -1 offset " + leftMinCount;

		// Log.d(TAG, "[" + sql + "]");
		final Cursor cu = db.rawQuery(sql, null);
		cu.moveToFirst();
		long max = 0;
		if (cu.moveToFirst()) {
			while (!cu.isAfterLast()) {
				if (max < cu.getLong(0)) {
					max = cu.getLong(0);
				}
				cu.moveToNext();
			}
		}
		cu.close();

		long l = (Util.nowMilliSecond() - secondLast * 1000);
		if (max > l) {
			max = l;
		}

		Log.d(TAG, "deleteOldMsgByTalker get max time :" + max);
		// sql = "delete from " + getTableNameByTalker() + " where ( talker=\"" + Util.escapeSqlValue(talker) + "\"" + ") and (" + MsgInfo.COL_CREATE_TIME + " < " + max + ");";
		// Log.d(TAG, "[" + sql + "]");

		int res = db.delete(getTableNameByTalker(), "( talker=\"" + Util.escapeSqlValue(talker) + "\""
				+ ") and (" + MsgInfo.COL_CREATETIME + " < " + max + ")", null);
		Log.d(TAG, "deleted message count:" + res);
	}

	private String getTableNameByTalker() {
		return getTableByTalker().getName();
	}

	private MsgTable getTableByTableName(final String tableName) {
		Assert.assertTrue(tableName != null && tableName.length() > 0);
		for (int i = 0; i < lstTable.size(); i++) {
			if (tableName.equals(lstTable.get(i).getName())) {
				return lstTable.get(i);
			}
		}
		Assert.assertTrue(false);
		return null;
	}

	private MsgTable getTableByTalker() {
		return getTableByTableName(MsgInfo.getTableByTalker());
	}
	
	public boolean isLocalIdInSession(final long id) {
		for (int i = 0; i < lstTable.size(); i++) {
			if (lstTable.get(i).isInSection(id)) {
				return true;
			}
		}
		return false;
	}

	private String getTableNameByLocalId(final long id) {
		for (int i = 0; i < lstTable.size(); i++) {
			if (lstTable.get(i).isInSection(id)) {
				return lstTable.get(i).getName();
			}
		}

		//Assert.assertTrue(false);
		return null;
	}

	// 获取前一封（更旧的）邮件对应的localId，-1表示不存在
	public long getPrevMailId(long currentId) {
		String sql = "select * from " + MsgInfo.MMSG_TABLE + " where " + MsgInfo.COL_TYPE + " = "
				+ ConstantsProtocal.MM_DATA_PUSHMAIL + " and " + MsgInfo.COL_MSGID + " < " + currentId + " order by "
				+ MsgInfo.COL_MSGID + " DESC limit 1";

		Cursor cu = db.rawQuery(sql, null);
		if (cu == null) {
			Log.e(TAG, "getPrevMailId fail, cursor is null, currentId = " + currentId);
			return -1;
		}

		if (cu.getCount() == 0) {
			Log.i(TAG, "getPrevMailId fail, cu getcount == 0");
			cu.close();
			return -1;
		}

		MsgInfo msg = new MsgInfo();
		if (cu.moveToFirst()) {
			msg.convertFrom(cu);
		}
		cu.close();

		return msg.getMsgId();
	}

	public int getIdxOfMail(long msgId) {
		String sql = "select count(*) from " + MsgInfo.MMSG_TABLE + " where " + MsgInfo.COL_TYPE + " = "
				+ ConstantsProtocal.MM_DATA_PUSHMAIL + " and " + MsgInfo.COL_MSGID + " >= " + msgId + " order by "
				+ MsgInfo.COL_MSGID + " ASC";

		Cursor cu = db.rawQuery(sql, null);
		if (cu == null) {
			Log.e(TAG, "getIdxOfMail, cursor is null");
			return -1;
		}

		int count = 0;
		if (cu.moveToLast()) {
			count = cu.getInt(0);
		}
		cu.close();
		return count;
	}

	// 获取后一封（更新的）邮件对应的localId，-1表示不存在
	public long getNextMailId(long currentId) {
		String sql = "select * from " + MsgInfo.MMSG_TABLE + " where " + MsgInfo.COL_TYPE + " = "
				+ ConstantsProtocal.MM_DATA_PUSHMAIL + " and " + MsgInfo.COL_MSGID + " > " + currentId + " order by "
				+ MsgInfo.COL_MSGID + " ASC limit 1";

		Cursor cu = db.rawQuery(sql, null);
		if (cu == null) {
			Log.e(TAG, "getNextMailId fail, cursor is null, currentId = " + currentId);
			return -1;
		}

		if (cu.getCount() == 0) {
			Log.i(TAG, "getNextMailId fail, cu getcount == 0");
			cu.close();
			return -1;
		}

		MsgInfo msg = new MsgInfo();
		if (cu.moveToFirst()) {
			msg.convertFrom(cu);
		}
		cu.close();

		return msg.getMsgId();
	}
	
	public long getFirstMessageId(final String talker){
		final String sql = "select " + MsgInfo.COL_MSGID + " from " + MsgInfo.MMSG_TABLE + " where " + MsgInfo.COL_TALKER + "='" + talker + "' " + " order by " + MsgInfo.COL_MSGID + " LIMIT 1 OFFSET 0";
		Log.d(TAG, "get first message id " + sql);
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor == null) {
			Log.e(TAG, "get first message id failed: " + talker);
			return -1;
		}
		
		if (cursor.getCount() == 0) {
			Log.i(TAG, "get first messaga id failed, get count == 0:" + talker);
			cursor.close();
			return -1;
		}
		
		int colIdx = cursor.getColumnIndex(MsgInfo.COL_MSGID);
		if (colIdx != -1 && cursor.moveToFirst()) {
			long msgId = cursor.getLong(colIdx);
			cursor.close();
			return msgId;
		}
		
		cursor.close();
		return -1;
	}
	
	public long getLastMessageId(final String talker, final int offset){
		final String sql = "select " + MsgInfo.COL_MSGID + " from " + MsgInfo.MMSG_TABLE + " where " + MsgInfo.COL_TALKER + "='" + talker + "'" + " order by " + MsgInfo.COL_MSGID + " LIMIT 1 OFFSET " + offset;
		Log.d(TAG, "get last message id " + sql);
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor == null) {
			Log.e(TAG, "get last message id failed " + talker + "offset:" + offset);
			return -1;
		}
		
		if (cursor.getCount() == 0) {
			Log.d(TAG, "get laset message id failed, getcount == 0 " + talker + "offset: " + offset);
			cursor.close();
			return -1;
		}
		
		int colIdx = cursor.getColumnIndex(MsgInfo.COL_MSGID);
		if (colIdx != -1 && cursor.moveToFirst()) {
			long msgId = cursor.getLong(colIdx);
			cursor.close();
			return msgId;
		}
		
		cursor.close();
		return -1;
	}

	private static class MsgTable {
		private long msgLocalId;
		private long minMsgLocalId;
		private long maxMsgLocalId;
		private int bitVal;
		private String name;

		public MsgTable(final int bitVal, final String name, long minLocalId, long maxLocalId) {
			Assert.assertTrue(name != null && name.length() > 0);
			Assert.assertTrue(maxLocalId >= minLocalId);
			this.name = name;
			this.msgLocalId = minLocalId;
			this.minMsgLocalId = minLocalId;
			this.maxMsgLocalId = maxLocalId;
			this.bitVal = bitVal;
		}

		public String getName() {
			return this.name;
		}

		public long getMsgLocalId() {
			return this.msgLocalId;
		}

		public void setMsgLocalId(int msgLocalId) {
			this.msgLocalId = msgLocalId;
		}

		public void incMsgLocalId() {
			msgLocalId += 1;
			Assert.assertTrue(msgLocalId >= minMsgLocalId && msgLocalId <= maxMsgLocalId);
		}

		public boolean isInSection(long id) {
			return id >= minMsgLocalId && id <= maxMsgLocalId;
		}

		public int getBitVal() {
			return this.bitVal;
		}
	}

	public long insertForPlugin(ContentValues values) {
		if (values == null) {
			return -1;
		}
		String talker = values.getAsString(MsgInfo.COL_TALKER);
		if (Util.isNullOrNil(talker)) {
			return -1;
		}
		MsgTable table = getTableByTalker();
		Assert.assertTrue(table != null);
		long msgId = table.getMsgLocalId();
		table.incMsgLocalId(); // 插入一条新消息，必须自增msg local id
		values.put(MsgInfo.COL_MSGID, msgId);
		final long result = db.insert(table.getName(), MsgInfo.COL_MSGID, values);
		if (result == -1) {
			return -1;
		}
		MsgInfo message = getById(msgId);

		if (lockForSync) {
			NotifyInfo info = null;
			if (mapNotifyInfo.containsKey(message.getTalker())) {
				info = mapNotifyInfo.get(message.getTalker());
			}
			if (info == null) {
				info = new NotifyInfo(message.getTalker(), "insert", message);
			} else {
				info.msgList.add(message);
			}
			if (info.shouldAddConversationUnread(message)) {
				info.insertCount++;
			}
			mapNotifyInfo.put(message.getTalker(), info);
		} else {
			NotifyInfo info = new NotifyInfo(message.getTalker(), "insert", message);
			if (info.shouldAddConversationUnread(message)) {
				info.insertCount = 1;
			}
			doNotify();
			addEvent(info);
		}

		return message.getMsgId();
	}

	public int deleteForPlugin(final String whereClause, final String[] whereArgs) {
		if (whereClause == null || (!whereClause.startsWith(MsgInfo.COL_MSGID))) {
			return -1;
		}
		if (whereArgs == null || whereArgs.length != 1) {
			return -1;
		}
		long msgId = Util.getLong(whereArgs[0], -1);
		return deleteByID(msgId);
	}

	public int updateForPlugin(final ContentValues values, final String whereClause, final String[] whereArgs) {
		if (whereClause == null || (!whereClause.startsWith(MsgInfo.COL_MSGID))) {
			return -1;
		}
		if (whereArgs == null || whereArgs.length != 1) {
			return -1;
		}
		long msgId = Util.getLong(whereArgs[0], -1);
		final int result = db.update(getTableNameByLocalId(msgId), values, MsgInfo.COL_MSGID + "=?", new String[] { ""
				+ msgId });
		if (result != 0) {
			doNotify();
			MsgInfo message = getById(msgId);
			addEvent(new NotifyInfo(message.getTalker(), "update", message));
		}
		return result;
	}

	public Cursor queryForPlugin(final String[] projection, final String selection, final String[] selectionArgs,
			final String sortOrder) {
		if (selection == null) {
			return null;
		}
		if (selectionArgs == null || selectionArgs.length != 1) {
			return null;
		}
		if (selection.startsWith(MsgInfo.COL_MSGID)) {
			long id = Util.getLong(selectionArgs[0], -1);
			return db.query(getTableNameByLocalId(id), null, MsgInfo.COL_MSGID + "=?", new String[] { "" + id }, null,
					null, sortOrder);
		} else if (selection.startsWith(MsgInfo.COL_TALKER)) {
			String talker = selectionArgs[0];
			return db.query(getTableNameByTalker(), null, MsgInfo.COL_TALKER + "=?",
					new String[] { "" + talker }, null, null, sortOrder);
		}
		return null;
	}
	
	// used for data transfer from msginfo to fmessagemsginfo
	public MsgInfo[] getLastMsgList(final String talker, final int limit) {
		if (talker == null || talker.length() == 0 || limit <= 0) {
			Log.e(TAG, "getLastMsgList, invalid argument, talker = " + talker + ", limit = " + limit);
			return null;
		}
		
		final String sql = "select * from " + getTableNameByTalker() + " where " + MsgInfo.COL_TALKER + " = '"
				+ Util.escapeSqlValue(talker) + "'  order by " + MsgInfo.COL_CREATETIME + " DESC limit " + limit;
		
		Cursor cursor = db.rawQuery(sql, null);
		final int count = cursor.getCount(); 
		Log.d(TAG, "getLastMsgList, talker = " + talker + ", limit = " + limit + ", count = " + count);
		
		if (count == 0) {
			Log.w(TAG, "getLastMsgList, cursor is empty");
			cursor.close();
			return null;
		}
		
		MsgInfo[] result = new MsgInfo[count];
		for (int i = 0; i < count; i++) {
			cursor.moveToPosition(i);
			result[count - i - 1] = new MsgInfo();
			result[count - i - 1].convertFrom(cursor);
		}
		
		cursor.close();
		return result;
	}

}
