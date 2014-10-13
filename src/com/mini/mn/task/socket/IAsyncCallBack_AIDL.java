package com.mini.mn.task.socket;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 异步数据回调
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheung
 */
public interface IAsyncCallBack_AIDL extends Parcelable{

	public static final Parcelable.Creator<IAsyncCallBack_AIDL> CREATOR = new Parcelable.Creator<IAsyncCallBack_AIDL>() {  
		  
        @Override  
        public IAsyncCallBack_AIDL[] newArray(int size) {  
            return new IAsyncCallBack_AIDL[size];  
        }  
  
        @Override  
        public IAsyncCallBack_AIDL createFromParcel(Parcel source) {  
            // TODO Auto-generated method stub  
            return null;  
        }  
    };
	
	/**
	 * 数据发送成功
	 * @param message 发送数据
	 */
	public void onMessageSentSuccessful(Object message);
	
	/**
	 * 数据发送失败
	 * @param e
	 * @param message	发送数据
	 */
	public void onMessageSentFailed(Exception e, Object message);
	
	/**
	 * 服务器回调数据
	 * @param session	会话
	 * @param message	接收数据	
	 * @throws Exception
	 */
	public void onMessageReceived(Object receivedMessage);
	
	/**
	 * 当客户端发送请求得到回复时调用(成功 200)
	 * @param reply	回复数据
	 */
	public void onReplyReceived_OK(Object replyMessage);
	
	/**
	 * 当客户端发送请求得到回复时调用(失败)
	 * @param reply	回复数据
	 */
	public void onReplyReceived_ERROR(Object replyMessage);
}
