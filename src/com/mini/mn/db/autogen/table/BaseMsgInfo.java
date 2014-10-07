package com.mini.mn.db.autogen.table;

import java.lang.reflect.Field;

import com.mini.mn.db.storage.IAutoDBItem;
import com.mini.mn.platformtools.LVBuffer;
import com.mini.mn.util.Log;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class BaseMsgInfo extends IAutoDBItem {

	private static final String TAG = "Mini.BaseMsgInfo";
	public static final String TABLE_NAME = "MsgInfo";
	public static final String[] INDEX_CREATE = new String[0];
	public static final String COL_MSGID = "msgId";
	public static final String COL_MSGSVRID = "msgSvrId";
	public static final String COL_TYPE = "type";
	public static final String COL_STATUS = "status";
	public static final String COL_ISSEND = "isSend";
	public static final String COL_ISSHOWTIMER = "isShowTimer";
	public static final String COL_CREATETIME = "createTime";
	public static final String COL_TALKER = "talker";
	public static final String COL_CONTENT = "content";
	public static final String COL_IMGPATH = "imgPath";
	public static final String COL_RESERVED = "reserved";
	public static final String COL_LVBUFFER = "lvbuffer";
	public long field_msgId;
	public int field_msgSvrId;
	public int field_type;
	public int field_status;
	public int field_isSend;
	public int field_isShowTimer;
	public long field_createTime;
	public String field_talker;
	public String field_content;
	public String field_imgPath;
	public String field_reserved;
	public byte[] field_lvbuffer;
	public String commentUrl;
	public int msgFlag;
	
	public static IAutoDBItem.MAutoDBInfo initAutoDBInfo(Class<?> clazz)
	{
		IAutoDBItem.MAutoDBInfo info = new IAutoDBItem.MAutoDBInfo();
		info.fields = new Field[12];
		info.columns = new String[13];
		StringBuilder sb = new StringBuilder();
		info.columns[0] = "msgId";
		info.colsMap.put("msgId", "LONG");
		sb.append(" msgId LONG PRIMARY KEY ");
		sb.append(", ");
		info.primaryKey = "msgId";
		info.columns[1] = "msgSvrId";
		info.colsMap.put("msgSvrId", "INTEGER");
		sb.append(" msgSvrId INTEGER");
		sb.append(", ");
		info.columns[2] = "type";
		info.colsMap.put("type", "INTEGER");
		sb.append(" type INTEGER");
		sb.append(", ");
		info.columns[3] = "status";
		info.colsMap.put("status", "INTEGER");
		sb.append(" status INTEGER");
		sb.append(", ");
		info.columns[4] = "isSend";
		info.colsMap.put("isSend", "INTEGER");
		sb.append(" isSend INTEGER");
		sb.append(", ");
		info.columns[5] = "isShowTimer";
		info.colsMap.put("isShowTimer", "INTEGER");
		sb.append(" isShowTimer INTEGER");
		sb.append(", ");
		info.columns[6] = "createTime";
		info.colsMap.put("createTime", "LONG");
		sb.append(" createTime LONG");
		sb.append(", ");
		info.columns[7] = "talker";
		info.colsMap.put("talker", "TEXT");
		sb.append(" talker TEXT");
		sb.append(", ");
		info.columns[8] = "content";
		info.colsMap.put("content", "TEXT");
		sb.append(" content TEXT default '' ");
		sb.append(", ");
		info.columns[9] = "imgPath";
		info.colsMap.put("imgPath", "TEXT");
		sb.append(" imgPath TEXT");
		sb.append(", ");
		info.columns[10] = "reserved";
		info.colsMap.put("reserved", "TEXT");
		sb.append(" reserved TEXT");
		sb.append(", ");
		info.columns[11] = "lvbuffer";
		info.colsMap.put("lvbuffer", "BLOB");
		sb.append(" lvbuffer BLOB");
		info.columns[12] = "rowid";
		info.sql = sb.toString();
		
		return info;
	}
	
	@Override
	public void convertFrom(Cursor cu)
	{
		int colIdx = -1;
		colIdx = cu.getColumnIndex("msgId");
		if (colIdx >= 0) {
		this.field_msgId = cu.getLong(colIdx);
		}
		colIdx = cu.getColumnIndex("msgSvrId");
		if (colIdx >= 0) {
		this.field_msgSvrId = cu.getInt(colIdx);
		}
		colIdx = cu.getColumnIndex("type");
		if (colIdx >= 0) {
		this.field_type = cu.getInt(colIdx);
		}
		colIdx = cu.getColumnIndex("status");
		if (colIdx >= 0) {
		this.field_status = cu.getInt(colIdx);
		}
		colIdx = cu.getColumnIndex("isSend");
		if (colIdx >= 0) {
		this.field_isSend = cu.getInt(colIdx);
		}
		colIdx = cu.getColumnIndex("isShowTimer");
		if (colIdx >= 0) {
		this.field_isShowTimer = cu.getInt(colIdx);
		}
		colIdx = cu.getColumnIndex("createTime");
		if (colIdx >= 0) {
		this.field_createTime = cu.getLong(colIdx);
		}
		colIdx = cu.getColumnIndex("talker");
		if (colIdx >= 0) {
		this.field_talker = cu.getString(colIdx);
		}
		colIdx = cu.getColumnIndex("content");
		if (colIdx >= 0) {
		this.field_content = cu.getString(colIdx);
		}
		colIdx = cu.getColumnIndex("imgPath");
		if (colIdx >= 0) {
		this.field_imgPath = cu.getString(colIdx);
		}
		colIdx = cu.getColumnIndex("reserved");
		if (colIdx >= 0) {
		this.field_reserved = cu.getString(colIdx);
		}
		colIdx = cu.getColumnIndex("lvbuffer");
		if (colIdx >= 0) {
		this.field_lvbuffer = cu.getBlob(colIdx);
		}
		colIdx = cu.getColumnIndex("rowid");
		if (colIdx >= 0) {
		this.systemRowid = cu.getLong(colIdx);
		}
		parseBuff();
	}
	
	@Override
	public ContentValues convertTo()
	{
		buildBuff();
		ContentValues values = new ContentValues();
		values.put("msgId", Long.valueOf(this.field_msgId));
		values.put("msgSvrId", Integer.valueOf(this.field_msgSvrId));
		values.put("type", Integer.valueOf(this.field_type));
		values.put("status", Integer.valueOf(this.field_status));
		values.put("isSend", Integer.valueOf(this.field_isSend));
		values.put("isShowTimer", Integer.valueOf(this.field_isShowTimer));
		values.put("createTime", Long.valueOf(this.field_createTime));
		values.put("talker", this.field_talker);
		if (this.field_content == null) {
			this.field_content = "";
		}
		values.put("content", this.field_content);
		values.put("imgPath", this.field_imgPath);
		values.put("reserved", this.field_reserved);
		values.put("lvbuffer", this.field_lvbuffer);
		if (this.systemRowid > 0L) {
			values.put("rowid", Long.valueOf(this.systemRowid));
		}
		return values;
	}
	
	public void reset() {}
	
	protected final void parseBuff()
	{
		try
			{
			LVBuffer lvbuf = new LVBuffer();
			int ret = lvbuf.initParse(this.field_lvbuffer);
			if (ret != 0)
			{
				Log.e("MicroMsg.SDK.BaseMsgInfo", "parse LVBuffer error:" + ret);
				return;
			}
			if (!lvbuf.checkGetFinish()) {
				this.commentUrl = lvbuf.getString();
			}
			if (!lvbuf.checkGetFinish()) {
				this.msgFlag = lvbuf.getInt();
			}
		}
		catch (Exception e)
		{
			Log.e("MicroMsg.SDK.BaseMsgInfo", "get value failed");
			e.printStackTrace();
			return;
		}
	}
	
	protected final void buildBuff()
	{
		try
		{
			LVBuffer lvbuf = null;
			lvbuf = new LVBuffer();
			lvbuf.initBuild();
			lvbuf.putString(this.commentUrl);
			lvbuf.putInt(this.msgFlag);
			this.field_lvbuffer = lvbuf.buildFinish();
		}
		catch (Exception e)
		{
			Log.e("MicroMsg.SDK.BaseMsgInfo", "get value failed");
			e.printStackTrace();
		}
	}

}
