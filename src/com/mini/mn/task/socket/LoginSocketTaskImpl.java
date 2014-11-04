package com.mini.mn.task.socket;

import java.util.HashMap;
import java.util.Map;

import com.mini.mn.constant.Constants;
import com.mini.mn.task.BaseSocketTask;

/**
 * 文本类消息命令
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheueng
 * 
 */
public class LoginSocketTaskImpl extends BaseSocketTask {

	/**
	 * 构造函数
	 * @param asyncCallBack 接口回调
	 */
	public LoginSocketTaskImpl(IAsyncCallBack_AIDL asyncCallBack) {
		super(asyncCallBack);
	}

	/**
	 * 文本消息传送 {@link Constants.IMCmd.IM_LOGIN_CMD}
	 * @param username 用户名
	 * @param password 密码
	 * @param confirmPassword 验证密码
	 */
	public void commit(long msgId,String username,String password,String confirmPassword) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("username", username);
		data.put("password", password);
		data.put("confirmPassword", confirmPassword);
		setValue(Constants.IMCmd.IM_LOGIN_CMD, msgId, data);
	}

}
