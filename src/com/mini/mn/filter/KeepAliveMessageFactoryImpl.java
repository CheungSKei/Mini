package com.mini.mn.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.mini.mn.constant.Constants;
import com.mini.mn.model.HeartBeatRequest;
import com.mini.mn.model.HeartBeatResponse;
import com.mini.mn.util.StringUtils;

/**
 * ��������ʵ��
 * 
 * @version 1.0.0
 * @date 2014-02-18
 * @author S.Kei.Cheueng
 */
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {

	/*
	 * �������������������� non-null(����)
	 * 
	 * @see
	 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getRequest
	 * (org.apache.mina.core.session.IoSession)
	 */
	@Override
	public Object getRequest(IoSession session) {
		//ֱ�ӷ�����Ҫ���͵�������Ϣ�����ݰ�
		HeartBeatRequest request = new HeartBeatRequest();
		request.setCmd(Constants.IMCmd.IM_HEARTBEAT_CMD);
		request.setFrom(Constants.FROM_CLIENT);
		request.setType(Constants.REQUEST_TYPE);
		return request;
	}

	/*
	 * ������������ͻ��˷����������󣬿ͻ��˵�ȻҲ���÷���  �÷�������null(����)
	 * 
	 * @see
	 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getResponse
	 * (org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public Object getResponse(IoSession session, Object request) {
		System.out.println("�õ�����");
		return null;
	}

	/*
	 * ������������ͻ��˷������������˲���ע�������ֱ�ӷ���false(��ȡ)
	 * 
	 * @see
	 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isRequest
	 * (org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public boolean isRequest(IoSession session, Object message) {
		boolean rtnBoolean = false;
		if(message != null && message instanceof HeartBeatRequest){
			
			HeartBeatRequest request = (HeartBeatRequest) message;
			//����Ƿ���������Ϣ
			String cmd = request.getCmd();
			String from = request.getFrom();
			String type = request.getType();
			if(StringUtils.isNotBlank(cmd)
					&& Constants.IMCmd.IM_HEARTBEAT_CMD.equals(cmd)
					&& StringUtils.isNotBlank(from)
					&& Constants.FROM_CLIENT.equals(from)
					&& StringUtils.isNotBlank(type)
					&& Constants.REQUEST_TYPE.equals(type)){
				System.out.println("��������");
				rtnBoolean = true; 
			}
		}
		
		return rtnBoolean;
	}

	/*
	 * �ͻ��˹�ע������������ж�mesaage�Ƿ��Ƿ�����(��ȡ)
	 * 
	 * @see
	 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isResponse
	 * (org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public boolean isResponse(IoSession session, Object message) {
		boolean rtnBoolean = false;
		if(message != null && message instanceof HeartBeatResponse){
			HeartBeatResponse response = (HeartBeatResponse) message;
			String cmd = response.getCmd();
			String from = response.getFrom();
			String type = response.getType();
			if(StringUtils.isNotBlank(cmd)
					&& Constants.IMCmd.IM_HEARTBEAT_CMD.equals(cmd)
					&& StringUtils.isNotBlank(from)
					&& Constants.FROM_SERVER.equals(from)
					&& StringUtils.isNotBlank(type)
					&& Constants.RESPONSE_TYPE.equals(type)){
				
				//��ʾ�����Է���˵�������Ϣ����Ӧ���ݰ�
				System.out.println("�����ظ�");
				rtnBoolean = true;
			}
		}
		
		return rtnBoolean;
	}

}
