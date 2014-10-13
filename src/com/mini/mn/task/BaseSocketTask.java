package com.mini.mn.task;

import java.util.Map;

import com.mini.mn.app.MiniCore;
import com.mini.mn.constant.Constants;
import com.mini.mn.model.AbstractRequest;
import com.mini.mn.task.socket.IAsyncCallBack_AIDL;

/**
 * SocketͨѶ������
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheung
 */
public abstract class BaseSocketTask {

	private IAsyncCallBack_AIDL asyncCallBack;
	
	public BaseSocketTask(IAsyncCallBack_AIDL asyncCallBack){
		this.asyncCallBack = asyncCallBack;
	}
	
	/**
	 * ��¼�ص��ӿ�
	 * @param msgId
	 */
	protected void putAsyncCallBack(long msgId){
		MiniCore.getMessageEvent().putAsyncCallBack(
				msgId, asyncCallBack);
	}
	
	/**
	 * �ı��������
	 */
	public void commit(String cmd, long msgId, Map<String, Object> data) {
		AbstractRequest _AbstractRequest = new AbstractRequest();
		_AbstractRequest.setCmd(cmd);
		_AbstractRequest.setMsgId(msgId);
		_AbstractRequest.setData(data);
		_AbstractRequest.setFrom(Constants.FROM_CLIENT);
		_AbstractRequest.setCookieValue(MiniCore.getMessageEvent().getCookieValue());
		_AbstractRequest.setDeviceId(MiniCore.getMessageEvent().getDeviceId());
		// ����ص��ӿ�
		putAsyncCallBack(_AbstractRequest.getMsgId());
		// ��Ϣ����
		sendMessage(_AbstractRequest);
	}
	
	/**
	 * ������Ϣ
	 * @param message
	 */
	private void sendMessage(AbstractRequest message){
		MiniCore.getMessageEvent().send(message);
	}
	
}
