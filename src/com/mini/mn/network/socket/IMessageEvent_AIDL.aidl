package com.mini.mn.network.socket;

import com.mini.mn.network.socket.IMessageListener_AIDL;
import com.mini.mn.task.socket.IAsyncCallBack_AIDL;
import com.mini.mn.model.Entity;

interface IMessageEvent_AIDL {

	/**
	 * 注册消息监听
	 * 
	 * @param listener
	 */
	void registerMessageListener(in IMessageListener_AIDL listener);
	
	/**
	 * 移除消息监听
	 * 
	 * @param listener
	 */
	void removeMessageListener(in IMessageListener_AIDL listener);
	
	/**
	 * 发送数据
	 * 
	 * @param body
	 *            SentBody结构体数据{@link AbstractRequest}
	 */
	void send(in Entity message);
	
	/**
	 * 关闭连接
	 *
	 */
	void destroy();
	
	/**
	 * 是否断开连接
	 * 
	 * @return
	 */
	boolean isConnected();
	
	/**
	 * 服务器Host
	 * 
	 * @return
	 */
	String getServerHost();
	
	/**
	 * 服务器端口
	 * 
	 * @return
	 */
	int getServerPort();
	
	/**
	 * 添加回调对象
	 * 
	 * @param msgId
	 *            消息Id
	 * @param asyncCallBackMap
	 *            回调对象
	 */
	void putAsyncCallBack(long msgId,in IAsyncCallBack_AIDL asyncCallBack);
	
	/**
	 * 移除msgId的回调
	 * 
	 * @param msgId
	 *            消息Id
	 */
	IAsyncCallBack_AIDL removeAsyncCallBack(long msgId);
	
	/**
	 * 设置CookieValue
	 * @param cookieValue
	 */
	void setCookieValue(String cookieValue);
	
	/**
	 * 取得cookieValue
	 * @return
	 */
	String getCookieValue();
	
	/**
	 * 取得Device Id
	 * @return
	 */
	String getDeviceId();
 }