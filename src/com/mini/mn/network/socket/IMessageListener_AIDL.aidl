package com.mini.mn.network.socket;

import android.net.NetworkInfo;

interface IMessageListener_AIDL {
	/**
	 * ���ͻ��˺ͷ�������ӶϿ�ʱ����	 
	 */
	void onConnectionClosed();
	/**
	 * ���ͻ��˺ͷ�������ӳɹ�ʱ����
	 */
	void onConnectionSuccessful();
	/**
	 * ���ͻ��˺ͷ��������ʧ��ʱ����
	 */
	void onConnectionFailed(in byte[] exception);
	/**
	 * ���ͻ��˺ͷ����ͨ�Ź����г����쳣����
	 */
	void onExceptionCaught(in byte[] throwable);
	/**
	 * ���ͻ����յ���Ϣ����ã�ͨ��{@link #onReplyReceived(ReplyBody reply)}ȡ��(Object����)
	 */
	void onMessageReceived(in byte[] message);
	/**
	 * ���ͻ����յ�����Ļظ�ʱ����(Object����)
	 */
	void onReplyReceived(in byte[] reply);
	/**
	 * ���ͻ��˷�������ɹ�ʱ(Object����)
	 */
	void onSentSuccessful(in byte[] message);
	/**
	 * ���ͻ��˷�������ʧ��ʱ
	 * @params message:Obejct
	 */
	void onSentFailed(in byte[] exception,in byte[] message);
	
	/**
	 * ���ֻ�����״̬�����仯(NetworkInfo����)
	 */
	void onNetworkChanged(in NetworkInfo info);
}