package com.mini.mn.model;

import java.util.Map;

import com.mini.mn.model.Entity.Builder;
/**
 * �������������ݽṹ
 *
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class AbstractResponse extends Entity implements Builder {

	private static final long serialVersionUID = 3229813618596480768L;

	/**
	 * ��������
	 */
	private String cmd;
	
	/**
	 * ���󷵻��� 200��404��500��502
	 * */
	private String code;
	
	/**
	 * ����˵��
	 */
	private String resMsg;

	/**
	 * ʱ���
	 */
	private long timestamp;
	
	//���ص�json����,ʵ��Ҳ��Map����ʽ����
	private Map<String,Object> data;
	
	// ��Ϣid
	private long msgId;
	
	// ��Ϣ������id
	private long msgSvrId;
	
	public AbstractResponse()
	{
		timestamp = System.currentTimeMillis();
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	
	public Map<String,Object> getData() {
		return data;
	}
	
	public void setData(Map<String,Object> data) {
		this.data = data;
	}
	
	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	
	public long getMsgSvrId() {
		return msgSvrId;
	}

	public void setMsgSvrId(long msgSvrId) {
		this.msgSvrId = msgSvrId;
	}
}
