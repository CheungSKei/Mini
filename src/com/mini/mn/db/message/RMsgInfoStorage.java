package com.mini.mn.db.message;

import com.mini.mn.db.ISQLiteDatabase;
import com.mini.mn.db.storage.MStorage;
import com.mini.mn.util.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class RMsgInfoStorage  extends MStorage {

	public static final String AUTHORITY = "com.tencent.ibg.mm.sdk.msginfo.provider";

	public static final String PRIMARY_KEY = RMsgInfo.COL_MSGID;

	public static RMsgInfoStorage create(Context context) {
		final ISQLiteDatabase db = new RMsgInfoDB(context);
		return new RMsgInfoStorage(context, db);
	}

	ISQLiteDatabase db = null;
	
	
	private RMsgInfoStorage(Context context, ISQLiteDatabase db) {
		this.db = db;
	}


	public long doInsert(RMsgInfo msg) {
		if(msg == null || Util.isNullOrNil(msg.field_talker) ){
			return -1;
		}
		ContentValues values = msg.convertTo();
		long id = db.insert(RMsgInfoDB.TABLE, RMsgInfo.COL_MSGID, values);
		if(id > 0){
			notify();
		}
		return id;
	}

	public int doDelete( final long id ) {
		int ret = db.delete(RMsgInfoDB.TABLE, RMsgInfo.COL_MSGID + "=?", new String[] { "" + id });
		if(ret > 0){
			notify();
		}
		return ret;
	}

	public int doUpdate(final long id  , RMsgInfo msg) {
		if(msg == null || Util.isNullOrNil(msg.field_talker) ){
			return -1;
		}
		ContentValues values = msg.convertTo();
		int ret = db.update(RMsgInfoDB.TABLE, values, RMsgInfo.COL_MSGID + "=?", new String[] { "" + id });
		if(ret > 0){
			notify();
		}
		return ret;
	}
	
	public RMsgInfo getMsgById(final long id){
		Cursor cu = db.query(RMsgInfoDB.TABLE,  null, RMsgInfo.COL_MSGID + "=?", new String[] { "" + id }, null, null, null);
		if(cu == null){
			return null;
		}
		if(cu.getCount() == 0){
			cu.close();
			return null;
		}
		RMsgInfo info = new RMsgInfo();
		info.convertFrom(cu);
		return info;		
	}
	
	public Cursor getMsgByTalker(String talker){
		return db.query(RMsgInfoDB.TABLE,  null, RMsgInfo.COL_TALKER + "=?", new String[] { "" + talker }, null, null, null);
	}
	
	
}
