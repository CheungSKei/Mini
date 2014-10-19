package com.mini.mn.app;

import java.util.ArrayList;
import java.util.List;

import com.mini.mn.network.socket.IDispatcher;
import com.mini.mn.network.socket.IMessageListener_AIDL;

public class MiniCore {

	private static IDispatcher mMessageEvent_dispatcher;
	
	/**
	 * ��Ϣ����(ȫ����Ϣ,������Ϣ�Զ����͹���,����д���,�뵥һ����ͬ)
	 */
	private static List<IMessageListener_AIDL> messageListeners = new ArrayList<IMessageListener_AIDL>();
	
	/**
	 * ������Ϣ�¼�����
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
	 * ��ȡ��Ϣ�¼�����
	 * @param dispatcher
	 */
	public static IDispatcher getMessageEvent(){
		return mMessageEvent_dispatcher;
	}
	
	public static List<IMessageListener_AIDL> getMessageListener(){
		return messageListeners;
	}
}
