package com.mini.mn.task.socket;


/**
 * �첽���ݻص�
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheung
 */
public interface AsyncCallBack {

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
