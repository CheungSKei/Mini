package com.mini.mn.task.socket;

import java.util.HashMap;
import java.util.Map;

import com.mini.mn.constant.Constants;
import com.mini.mn.task.BaseSocketTask;

/**
 * 登出命令
 * 
 * @version 1.0.0
 * @date 2014-03-06
 * @author S.Kei.Cheueng
 * 
 */
public class LogoutSocketTaskImpl extends BaseSocketTask {

	/**
	 * 构造函数
	 * @param asyncCallBack 接口回调
	 */
	public LogoutSocketTaskImpl(IAsyncCallBack_AIDL asyncCallBack) {
		super(asyncCallBack);
	}

	/**
	 * 文本消息传送 {@link Constants.IMCmd.IM_LOGOUT_CMD}
	 */
	public void commit(long msgId) {
		Map<String, Object> data = new HashMap<String, Object>();
		commit(Constants.IMCmd.IM_LOGOUT_CMD, msgId, data);
	}

}
