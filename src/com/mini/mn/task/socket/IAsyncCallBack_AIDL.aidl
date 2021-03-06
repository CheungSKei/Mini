package com.mini.mn.task.socket;

interface IAsyncCallBack_AIDL {

	/**
	 * 数据发送成功(Object类型)
	 * @param message 发送数据
	 */
	void onMessageSentSuccessful(in byte[] message);
	
	/**
	 * 数据发送失败(Exception Object类型)
	 * @param e
	 * @param message	发送数据
	 */
	void onMessageSentFailed(in byte[] exception, in byte[] message);
	
	/**
	 * 服务器回调数据(Object类型)
	 * @param session	会话
	 * @param message	接收数据	
	 * @throws Exception
	 */
	void onMessageReceived(in byte[] receivedMessage);
	
	/**(Object类型)
	 * 当客户端发送请求得到回复时调用(成功 200)
	 * @param reply	回复数据
	 */
	void onReplyReceived_OK(in byte[] replyMessage);
	
	/**(Object类型)
	 * 当客户端发送请求得到回复时调用(失败)
	 * @param reply	回复数据
	 */
	void onReplyReceived_ERROR(in byte[] replyMessage);

}