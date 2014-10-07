package com.mini.mn.db.message;

import com.mini.mn.db.autogen.table.BaseMsgInfo;

public class RMsgInfo extends BaseMsgInfo {
	
	protected static MAutoDBInfo info = BaseMsgInfo.initAutoDBInfo(RMsgInfo.class);
	
	@Override
	protected MAutoDBInfo getDBInfo() {
		return info;
	}

	public RMsgInfo() {
	}

	public RMsgInfo(final long msgId) {
		this.field_msgId = msgId;
	}

	public RMsgInfo(final String talker) {
		this.field_talker = talker;
	}

}
