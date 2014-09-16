 
package com.mini.mn.network.socket;

import android.os.Handler;

/**
 * ��Ϣ��������ui�̴߳�����Ϣ�¼�
 * 
 * @version 1.0.0
 * @date 2014-2-13
 * @author S.Kei.Cheung
 */
public abstract class MessageEventHandlerContainer {

	/** ��Ϣ����Handler */
	protected Handler messageReceivedHandler;

	/** �ظ�����Handler */
	protected Handler replyReceivedHandler;

	/** �ɹ�������ϢHandler */
	protected Handler messageSentSuccessfulHandler;

	/** ����׽Handler */
	protected Handler exceptionCaughtHandler;

	/** Session�ر�Handler */
	protected Handler sessionClosedHandler;

	/** Session����Handler */
	protected Handler sessionCreatedHandler;

	/** ��Ϣ����ʧ��Handler */
	protected Handler messageSentFailedHandler;

	/** ����ʧ��Handler */
	protected Handler connectionFailedHandler;
	
	/** ����ı�Handler */
	protected Handler networkChangedHandler;

	/** ������Ϣ����Handler */
	public abstract void createMessageReceivedHandler();

	/** ��������ʧ��Handler */
	public abstract void createConnectionFailedHandler();

	/** �����ظ�����Handler */
	public abstract void createReplyReceivedHandler();

	/** ������Ϣ�ɹ�����Handler */
	public abstract void createMessageSentSuccessfulHandler();

	/** ��������׽Handler */
	public abstract void createExceptionCaughtHandler();

	/** ����Session�ر�Handler */
	public abstract void createSessionClosedHandler();

	/** ����Session����Handler */
	public abstract void createSessionCreatedHandler();

	/** ������Ϣ����ʧ��Handler */
	public abstract void createMessageSentFailedHandler();
	

}
