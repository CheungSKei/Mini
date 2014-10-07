package com.mini.mn.db.storage;

public class GroupInfo {
	private final String type;
	private final String name;
	private final String nick;
	private final String searchStr;
	private final boolean enable;
	private boolean notify;
	private int size;

	public GroupInfo() {
		this.type = "";
		this.name = "";
		this.nick = "";
		this.searchStr = "";
		this.enable = true;
	}

	public GroupInfo(final String type, final String name, final String nick, final String searchStr, final boolean enable, final boolean notify) {
		this.type = type;
		this.name = name;
		this.nick = nick;
		this.searchStr = searchStr;
		this.enable = enable;
		this.notify = notify;
	}

	public void setSize(final int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public String getType() {
		return type == null ? "" : type;
	}

	public String getName() {
		return name == null ? "" : name;
	}

	public String getNick() {
		return nick == null ? "" : nick;
	}

	public String getDisplayNick() {
		return nick == null ? "" : nick;
	}

	public String getSearchStr() {
		return searchStr == null ? "" : searchStr;
	}

	public boolean isEnable() {
		return enable;
	}

	public boolean isNotify() {
		return notify;
	}
}
