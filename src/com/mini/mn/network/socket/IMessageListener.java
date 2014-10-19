package com.mini.mn.network.socket;

import android.net.NetworkInfo;

/**
 * �������Ӽ�������ӿ�
 * 
 * @version 1.0.0
 * @date 2014-10-19
 * @author S.Kei.Cheung
 */
public abstract interface IMessageListener {
	/**
	 * ���ͻ��˺ͷ��������ʧ��ʱ����
	 */
	void onConnectionFailed(Exception exception);
	/**
	 * ���ͻ��˺ͷ����ͨ�Ź����г����쳣����
	 */
	void onExceptionCaught(Throwable throwable);
	/**
	 * ���ͻ����յ���Ϣ����ã�ͨ��{@link #onReplyReceived(ReplyBody reply)}ȡ��(Object����)
	 */
	void onMessageReceived(Object message);
	/**
	 * ���ͻ����յ�����Ļظ�ʱ����(Object����)
	 */
	void onReplyReceived(Object reply);
	/**
	 * ���ͻ��˷�������ɹ�ʱ(Object����)
	 */
	void onSentSuccessful(Object message);
	/**
	 * ���ͻ��˷�������ʧ��ʱ
	 * @params message:Obejct
	 */
	void onSentFailed(Exception exception,Object message);
	
	/**
	 * ���ֻ�����״̬�����仯(NetworkInfo����)
	 */
	void onNetworkChanged(NetworkInfo info);
	
}
