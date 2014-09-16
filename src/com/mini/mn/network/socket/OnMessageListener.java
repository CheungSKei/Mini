 
package com.mini.mn.network.socket;

import android.net.NetworkInfo;

/**
 * ��Ϣ�Ự�¼� �ӿ�
 *
 * @version 1.0.0
 * @date 2014-2-13
 * @author S.Kei.Cheung
 */
public interface OnMessageListener {

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
