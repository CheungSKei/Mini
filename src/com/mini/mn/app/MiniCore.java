package com.mini.mn.app;

import java.util.ArrayList;
import java.util.List;

import com.mini.mn.network.socket.IDispatcher;
import com.mini.mn.network.socket.IMessageListener_AIDL;

public class MiniCore {

	private static IDispatcher mMessageEvent_dispatcher;
	
	/**
	 * 消息监听(全局消息,当有消息自动推送过来,则进行处理,与单一请求不同)
	 */
	private static List<IMessageListener_AIDL> messageListeners = new ArrayList<IMessageListener_AIDL>();
	
	/**
	 * 设置消息事件对象
	 * @param dispatcher
	 */
	public static void setMessageEvent(IDispatcher dispatcher){
		mMessageEvent_dispatcher = dispatcher;
		if(mMessageEvent_dispatcher!=null){
			for(IMessageListener_AIDL iml:messageListeners)
				mMessageEvent_dispatcher.registerMessageListener(iml);
			messageListeners.clear();
		}
	}
	
	/**
	 * 获取消息事件对象
	 * @param dispatcher
	 */
	public static IDispatcher getMessageEvent(){
		return mMessageEvent_dispatcher;
	}
	
	public static List<IMessageListener_AIDL> getMessageListener(){
		return messageListeners;
	}
}
