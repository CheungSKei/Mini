package com.mini.mn.task;

import java.util.Map;

import com.mini.mn.constant.Constants;
import com.mini.mn.model.AbstractRequest;
import com.mini.mn.task.socket.AsyncCallBack;
import com.mini.mn.network.socket.MessageConnectorManager;

/**
 * SocketͨѶ������
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheung
 */
public abstract class BaseSocketTask {

	private AsyncCallBack asyncCallBack;
	
	public BaseSocketTask(AsyncCallBack asyncCallBack){
		this.asyncCallBack = asyncCallBack;
	}
	
	/**
	 * ��¼�ص��ӿ�
	 * @param msgId
	 */
	protected void putAsyncCallBack(long msgId){
		MessageConnectorManager.getManager().putAsyncCallBack(
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
		_AbstractRequest.setCookieValue(MessageConnectorManager.getManager().getCookieValue());
		_AbstractRequest.setDeviceId(MessageConnectorManager.getManager().getDeviceId());
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
		MessageConnectorManager.getManager().send(message);
	}
	
}
