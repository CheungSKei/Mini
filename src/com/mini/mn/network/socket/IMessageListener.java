package com.mini.mn.network.socket;

import android.net.NetworkInfo;

/**
 * 网络连接监听抽象接口
 * 
 * @version 1.0.0
 * @date 2014-10-19
 * @author S.Kei.Cheung
 */
public abstract interface IMessageListener {
	/**
	 * 当客户端和服务端连接失败时调用
	 */
	void onConnectionFailed(Exception exception);
	/**
	 * 当客户端和服务端通信过程中出现异常调用
	 */
	void onExceptionCaught(Throwable throwable);
	/**
	 * 当客户端收到消息后调用，通过{@link #onReplyReceived(ReplyBody reply)}取得(Object类型)
	 */
	void onMessageReceived(Object message);
	/**
	 * 当客户端收到请求的回复时调用(Object类型)
	 */
	void onReplyReceived(Object reply);
	/**
	 * 当客户端发送请求成功时(Object类型)
	 */
	void onSentSuccessful(Object message);
	/**
	 * 当客户端发送请求失败时
	 * @params message:Obejct
	 */
	void onSentFailed(Exception exception,Object message);
	
	/**
	 * 当手机网络状态发生变化(NetworkInfo类型)
	 */
	void onNetworkChanged(NetworkInfo info);
	
}
