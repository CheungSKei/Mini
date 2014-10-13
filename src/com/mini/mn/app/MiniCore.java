package com.mini.mn.app;

import com.mini.mn.network.socket.IDispatcher;

public class MiniCore {

	private static IDispatcher mMessageEvent_dispatcher;
	
	/**
	 * 设置消息事件对象
	 * @param dispatcher
	 */
	public static void setMessageEvent(IDispatcher dispatcher){
		mMessageEvent_dispatcher = dispatcher;
	}
	
	/**
	 * 获取消息事件对象
	 * @param dispatcher
	 */
	public static IDispatcher getMessageEvent(){
		return mMessageEvent_dispatcher;
	}
}
