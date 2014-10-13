package com.mini.mn.task.socket;

import java.util.HashMap;
import java.util.Map;

import com.mini.mn.constant.Constants;
import com.mini.mn.task.BaseSocketTask;

/**
 * �ǳ�����
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheueng
 * 
 */
public class LogoutSocketTaskImpl extends BaseSocketTask {

	/**
	 * ���캯��
	 * @param asyncCallBack �ӿڻص�
	 */
	public LogoutSocketTaskImpl(IAsyncCallBack_AIDL asyncCallBack) {
		super(asyncCallBack);
	}

	/**
	 * �ı���Ϣ���� {@link Constants.IMCmd.IM_LOGOUT_CMD}
	 */
	public void commit(long msgId) {
		Map<String, Object> data = new HashMap<String, Object>();
		commit(Constants.IMCmd.IM_LOGOUT_CMD, msgId, data);
	}

}
