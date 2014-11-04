package com.mini.mn.network.socket;

import android.net.NetworkInfo;

interface IMessageListener_AIDL {
	/**
	 * 当客户端和服务端连接断开时调用	 
	 */
	void onConnectionClosed();
	/**
	 * 当客户端和服务端连接成功时调用
	 */
	void onConnectionSuccessful();
	/**
	 * 当客户端和服务端连接失败时调用
	 */
	void onConnectionFailed(in byte[] exception);
	/**
	 * 当客户端和服务端通信过程中出现异常调用
	 */
	void onExceptionCaught(in byte[] throwable);
	/**
	 * 当客户端收到消息后调用，通过{@link #onReplyReceived(ReplyBody reply)}取得(Object类型)
	 */
	void onMessageReceived(in byte[] message);
	/**
	 * 当客户端收到请求的回复时调用(Object类型)
	 */
	void onReplyReceived(in byte[] reply);
	/**
	 * 当客户端发送请求成功时(Object类型)
	 */
	void onSentSuccessful(in byte[] message);
	/**
	 * 当客户端发送请求失败时
	 * @params message:Obejct
	 */
	void onSentFailed(in byte[] exception,in byte[] message);
	
	/**
	 * 当手机网络状态发生变化(NetworkInfo类型)
	 */
	void onNetworkChanged(in NetworkInfo info);
}