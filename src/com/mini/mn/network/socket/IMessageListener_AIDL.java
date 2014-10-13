package com.mini.mn.network.socket;

import android.net.NetworkInfo;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * ��Ϣ�Ự�¼� �ӿ�
 *
 * @version 1.0.0
 * @date 2014-2-13
 * @author S.Kei.Cheung
 */
public interface IMessageListener_AIDL extends Parcelable{

	public static final Parcelable.Creator<IMessageListener_AIDL> CREATOR = new Parcelable.Creator<IMessageListener_AIDL>() {  
		  
        @Override  
        public IMessageListener_AIDL[] newArray(int size) {  
            return new IMessageListener_AIDL[size];  
        }  
  
        @Override  
        public IMessageListener_AIDL createFromParcel(Parcel source) {  
            // TODO Auto-generated method stub  
            return null;  
        }  
    };
	
	/**
	 * ���ͻ��˺ͷ�������ӶϿ�ʱ����	 
	 */
	public void onConnectionClosed();
	/**
	 * ���ͻ��˺ͷ�������ӳɹ�ʱ����
	 */
	public void onConnectionSuccessful();
	/**
	 * ���ͻ��˺ͷ��������ʧ��ʱ����
	 */
	public void onConnectionFailed(Exception e);
	/**
	 * ���ͻ��˺ͷ����ͨ�Ź����г����쳣����
	 */
	public void onExceptionCaught(Throwable throwable);
	/**
	 * ���ͻ����յ���Ϣ����ã�ͨ��{@link #onReplyReceived(ReplyBody reply)}ȡ��
	 */
	public void onMessageReceived(Object message);
	/**
	 * ���ͻ����յ�����Ļظ�ʱ����
	 */
	public void onReplyReceived(Object reply);
	/**
	 * ���ͻ��˷�������ɹ�ʱ
	 */
	public void onSentSuccessful(Object message);
	/**
	 * ���ͻ��˷�������ʧ��ʱ
	 */
	public void onSentFailed(Exception e,Object message);
	
	/**
	 * ���ֻ�����״̬�����仯
	 */
	public void onNetworkChanged(NetworkInfo info);
}
