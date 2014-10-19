package com.mini.mn.task.socket;


/**
 * 网络连接异步监听抽象接口
 * 
 * @version 1.0.0
 * @date 2014-10-19
 * @author S.Kei.Cheung
 */
public abstract interface IAsyncCallBack {
	/**
	 * 数据发送成功(Object类型)
	 * @param message 发送数据
	 */
	void onMessageSentSuccessful(Object message);
	
	/**
	 * 数据发送失败(Exception Object类型)
	 * @param e
	 * @param message	发送数据
	 */
	void onMessageSentFailed(Exception exception, Object message);
	
	/**
	 * 服务器回调数据(Object类型)
	 * @param session	会话
	 * @param message	接收数据	
	 * @throws Exception
	 */
	void onMessageReceived(Object receivedMessage);
	
	/**(Object类型)
	 * 当客户端发送请求得到回复时调用(成功 200)
	 * @param reply	回复数据
	 */
	void onReplyReceived_OK(Object replyMessage);
	
	/**(Object类型)
	 * 当客户端发送请求得到回复时调用(失败)
	 * @param reply	回复数据
	 */
	void onReplyReceived_ERROR(Object replyMessage);
	
}
