package com.mini.mn.app;

import com.mini.mn.network.socket.IDispatcher;

public class MiniCore {

	private static IDispatcher mMessageEvent_dispatcher;
	
	/**
	 * ������Ϣ�¼�����
	 * @param dispatcher
	 */
	public static void setMessageEvent(IDispatcher dispatcher){
		mMessageEvent_dispatcher = dispatcher;
	}
	
	/**
	 * ��ȡ��Ϣ�¼�����
	 * @param dispatcher
	 */
	public static IDispatcher getMessageEvent(){
		return mMessageEvent_dispatcher;
	}
}
