package com.mini.mn.task.socket;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * �첽���ݻص�
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
	 * ���ݷ��ͳɹ�
	 * @param message ��������
	 */
	public void onMessageSentSuccessful(Object message);
	
	/**
	 * ���ݷ���ʧ��
	 * @param e
	 * @param message	��������
	 */
	public void onMessageSentFailed(Exception e, Object message);
	
	/**
	 * �������ص�����
	 * @param session	�Ự
	 * @param message	��������	
	 * @throws Exception
	 */
	public void onMessageReceived(Object receivedMessage);
	
	/**
	 * ���ͻ��˷�������õ��ظ�ʱ����(�ɹ� 200)
	 * @param reply	�ظ�����
	 */
	public void onReplyReceived_OK(Object replyMessage);
	
	/**
	 * ���ͻ��˷�������õ��ظ�ʱ����(ʧ��)
	 * @param reply	�ظ�����
	 */
	public void onReplyReceived_ERROR(Object replyMessage);
}
