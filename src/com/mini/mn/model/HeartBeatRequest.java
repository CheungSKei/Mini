package com.mini.mn.model;

import android.os.Parcel;

import com.mini.mn.model.Entity.Builder;

/**
 * heartbeat������Ϣ������Ϣ��
 * @author S.Kei.Cheung
 *
 */
public class HeartBeatRequest extends Entity implements Builder{
	
	private static final long serialVersionUID = 1L;
	// ��Ϣ����
	private String cmd;
	// ��Ϣ������
	private String from;
	// ��Ϣ����
	private String type;
	// cookie
	private String cookieValue;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getCookieValue() {
		return cookieValue;
	}
	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(cmd);
		dest.writeString(from);
		dest.writeString(type);
		dest.writeString(cookieValue);
		super.writeToParcel(dest, flags);
	}
}
