package com.mini.mn.model;

import java.lang.reflect.Field;
import java.util.Map;

import android.os.Parcel;

import com.mini.mn.model.Entity.Builder;

/**
 * �ͻ��˷������ݽṹ
 *
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class AbstractRequest extends Entity implements Builder {

	private static final long serialVersionUID = -8086345000697246490L;
	// ������Ϣ��ʱ���
	private long timestamp;
	// ��Ϣ����
	private String cmd;
	//��Ϣ������
	private String from;
	// ��Ϣid
	private long msgId;
	// ������Ϣ�Զ�������
	private Map<String,Object> data;
	// cookie
	private String cookieValue;
	// deviceId
	private String deviceId;

	public AbstractRequest() {
		timestamp = System.currentTimeMillis();
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

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	
	public Map<String,Object> getData() {
		return data;
	}
	
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	
	public String getCookieValue() {
		return cookieValue;
	}

	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(timestamp);
		dest.writeString(cmd);
		dest.writeString(from);
		dest.writeLong(msgId);
		dest.writeMap(data);
		dest.writeString(cookieValue);
		dest.writeString(deviceId);
		super.writeToParcel(dest, flags);
	}
}
