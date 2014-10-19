package com.mini.mn.ui;

import com.mini.mn.app.MiniCore;
import com.mini.mn.network.socket.IMessageListener_AIDL;

import android.support.v7.app.ActionBarActivity;

/**
 * Activity基类
 * 
 * @version 1.0.0
 * @date 2014-10-19
 * @author S.Kei.Cheung
 */
public class BaseAcivity extends ActionBarActivity {

	/**
	 * 注册消息监听
	 * @param iMessageListener_AIDL
	 */
	protected void registerMessageListener(IMessageListener_AIDL iMessageListener_AIDL){
		if(MiniCore.getMessageEvent()!=null){
			MiniCore.getMessageEvent().registerMessageListener(iMessageListener_AIDL);
		}else{
			if(!MiniCore.getMessageListener().contains(iMessageListener_AIDL)){
				MiniCore.getMessageListener().add(iMessageListener_AIDL);
			}
		}
	}
	
	/**
	 * 注销消息监听
	 * @param iMessageListener_AIDL
	 */
	protected void removeMessageListener(IMessageListener_AIDL iMessageListener_AIDL){
		if(MiniCore.getMessageEvent()!=null){
			MiniCore.getMessageEvent().removeMessageListener(iMessageListener_AIDL);
		}else{
			if(MiniCore.getMessageListener().contains(iMessageListener_AIDL)){
				MiniCore.getMessageListener().remove(iMessageListener_AIDL);
			}
		}
	}
	
}
