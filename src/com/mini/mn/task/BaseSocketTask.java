package com.mini.mn.task;

import java.io.IOException;
import java.util.Map;

import com.mini.mn.app.MiniCore;
import com.mini.mn.constant.Constants;
import com.mini.mn.model.AbstractRequest;
import com.mini.mn.task.socket.IAsyncCallBack_AIDL;
import com.mini.mn.util.SerializerUtil;

/**
 * Socket通讯任务类
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheung
 */
public abstract class BaseSocketTask {

	private IAsyncCallBack_AIDL asyncCallBack;
	// 发送指令
	private String mCmd;
	// 消息类型
	private long mMsgId;
	// 数据
	private Map<String,Object> mData;
	// Default set to MIN_PRIORITY 任务等级
	private int priority = 0;
	
	public BaseSocketTask(IAsyncCallBack_AIDL asyncCallBack){
		this.asyncCallBack = asyncCallBack;
	}
	
	/**
	 * 记录回调接口
	 * @param msgId
	 */
	protected void putAsyncCallBack(long msgId){
		MiniCore.getMessageEvent().putAsyncCallBack(
				msgId, asyncCallBack);
	}
	
	/**
	 * 文本类命令传送
	 */
	public void setValue(String cmd, long msgId, Map<String, Object> data) {
		this.mCmd = cmd;
		this.mMsgId = msgId;
		this.mData = data;
	}
	
	/**
	 * 执行
	 * 只用于NetSceneQueue队列中执行,正常调用则是</br>
	 * {@link commit(String cmd, long msgId, Map<String, Object> data)}
	 */
	public void commit(){
		AbstractRequest _AbstractRequest = new AbstractRequest();
		_AbstractRequest.setCmd(this.mCmd);
		_AbstractRequest.setMsgId(this.mMsgId);
		_AbstractRequest.setData(this.mData);
		_AbstractRequest.setFrom(Constants.FROM_CLIENT);
		_AbstractRequest.setCookieValue(MiniCore.getMessageEvent().getCookieValue());
		_AbstractRequest.setDeviceId(MiniCore.getMessageEvent().getDeviceId());
		// 插入回调接口
		putAsyncCallBack(_AbstractRequest.getMsgId());
		// 消息发送
		sendMessage(_AbstractRequest);
	}
	
	/**
	 * 发送消息
	 * @param message
	 */
	private void sendMessage(AbstractRequest message){
		try {
			MiniCore.getMessageEvent().send(SerializerUtil.serialize(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPriority() {
		return priority;
	}

	/**
	 * 设置任务等级
	 * @param priority </br>
	 * 
     * The maximum priority value allowed for a thread. </br>
     *
     * public static final int MAX_PRIORITY = 10; </br>
     *
     * The minimum priority value allowed for a thread. </br>
     *
     * public static final int MIN_PRIORITY = 1; </br>
	 *
     * The normal (default) priority value assigned to threads. </br>
     *
     * public static final int NORM_PRIORITY = 5; </br>
	 */
	public void setPriority(final int priority) {
		this.priority = priority;
	}
	
}
