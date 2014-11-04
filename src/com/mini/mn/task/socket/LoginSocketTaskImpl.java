package com.mini.mn.task.socket;

import java.util.HashMap;
import java.util.Map;

import com.mini.mn.constant.Constants;
import com.mini.mn.task.BaseSocketTask;

/**
 * �ı�����Ϣ����
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheueng
 * 
 */
public class LoginSocketTaskImpl extends BaseSocketTask {

	/**
	 * ���캯��
	 * @param asyncCallBack �ӿڻص�
	 */
	public LoginSocketTaskImpl(IAsyncCallBack_AIDL asyncCallBack) {
		super(asyncCallBack);
	}

	/**
	 * �ı���Ϣ���� {@link Constants.IMCmd.IM_LOGIN_CMD}
	 * @param username �û���
	 * @param password ����
	 * @param confirmPassword ��֤����
	 */
	public void commit(long msgId,String username,String password,String confirmPassword) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("username", username);
		data.put("password", password);
		data.put("confirmPassword", confirmPassword);
		setValue(Constants.IMCmd.IM_LOGIN_CMD, msgId, data);
	}

}
