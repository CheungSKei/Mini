package com.mini.mn.task.socket;

import java.util.HashMap;
import java.util.Map;

import com.mini.mn.constant.Constants;
import com.mini.mn.task.BaseSocketTask;

/**
 * IM文本类消息命令
 * 
 * @version 1.0.0
 * @date 2014-09-22
 * @author S.Kei.Cheueng
 * 
 */
public class IMMsgSocketTaskImpl extends BaseSocketTask {

	/**
	 * 构造函数
	 * @param asyncCallBack 接口回调
	 */
	public IMMsgSocketTaskImpl(IAsyncCallBack_AIDL asyncCallBack) {
		super(asyncCallBack);
	}

	/**
	 * 文本消息传送 {@link Constants.IMCmd.IM_TEXT_CMD}
	 * @param fromUserId	发送方用户id
	 * @param fromUsername	发送方用户昵称
	 * @param toUserId		接收方用户id
	 * @param toUsername	接收方用户昵称
	 * @param content		文本消息内容
	 * @param secretContent	加密后的文本消息内容
	 */
	public void commit(long msgId,long fromUserId,String fromUsername,long toUserId,String toUsername,String content,String secretContent) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("fromUserId", fromUserId);
		data.put("fromUsername", fromUsername);
		data.put("toUserId", toUserId);
		data.put("toUsername", toUsername);
		data.put("content", content);
		data.put("secretContent", secretContent);
		commit(Constants.IMCmd.IM_TEXT_CMD, msgId, data);
	}

}
