package com.mini.mn.task.socket;


/**
 * ���������첽��������ӿ�
 * 
 * @version 1.0.0
 * @date 2014-10-19
 * @author S.Kei.Cheung
 */
public abstract interface IAsyncCallBack {
	/**
	 * ���ݷ��ͳɹ�(Object����)
	 * @param message ��������
	 */
	void onMessageSentSuccessful(Object message);
	
	/**
	 * ���ݷ���ʧ��(Exception Object����)
	 * @param e
	 * @param message	��������
	 */
	void onMessageSentFailed(Exception exception, Object message);
	
	/**
	 * �������ص�����(Object����)
	 * @param session	�Ự
	 * @param message	��������	
	 * @throws Exception
	 */
	void onMessageReceived(Object receivedMessage);
	
	/**(Object����)
	 * ���ͻ��˷�������õ��ظ�ʱ����(�ɹ� 200)
	 * @param reply	�ظ�����
	 */
	void onReplyReceived_OK(Object replyMessage);
	
	/**(Object����)
	 * ���ͻ��˷�������õ��ظ�ʱ����(ʧ��)
	 * @param reply	�ظ�����
	 */
	void onReplyReceived_ERROR(Object replyMessage);
	
}
