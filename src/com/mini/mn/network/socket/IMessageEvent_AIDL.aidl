package com.mini.mn.network.socket;

import com.mini.mn.network.socket.IMessageListener_AIDL;
import com.mini.mn.task.socket.IAsyncCallBack_AIDL;

interface IMessageEvent_AIDL {

	/**
	 * ע����Ϣ����
	 * 
	 * @param listener
	 */
	void registerMessageListener(IMessageListener_AIDL listener);
	
	/**
	 * �Ƴ���Ϣ����
	 * 
	 * @param listener
	 */
	void removeMessageListener(IMessageListener_AIDL listener);
	
	/**
	 * ��������
	 * 
	 * @param body
	 *            SentBody�ṹ������{@link AbstractRequest}
	 */
	void send(in byte[] message);
	
	/**
	 * �ر�����
	 *
	 */
	void destroy();
	
	/**
	 * �Ƿ�Ͽ�����
	 * 
	 * @return
	 */
	boolean isConnected();
	
	/**
	 * ������Host
	 * 
	 * @return
	 */
	String getServerHost();
	
	/**
	 * �������˿�
	 * 
	 * @return
	 */
	int getServerPort();
	
	/**
	 * ��ӻص�����
	 * 
	 * @param msgId
	 *            ��ϢId
	 * @param asyncCallBackMap
	 *            �ص�����
	 */
	void putAsyncCallBack(long msgId,IAsyncCallBack_AIDL asyncCallBack);
	
	/**
	 * �Ƴ�msgId�Ļص�
	 * 
	 * @param msgId
	 *            ��ϢId
	 */
	IAsyncCallBack_AIDL removeAsyncCallBack(long msgId);
	
	/**
	 * ����CookieValue
	 * @param cookieValue
	 */
	void setCookieValue(String cookieValue);
	
	/**
	 * ȡ��cookieValue
	 * @return
	 */
	String getCookieValue();
	
	/**
	 * ȡ��Device Id
	 * @return
	 */
	String getDeviceId();
 }