package com.mini.mn.task.socket;

import java.util.HashMap;
import java.util.Map;

import com.mini.mn.constant.Constants;
import com.mini.mn.task.BaseSocketTask;

/**
 * IM�ı�����Ϣ����
 * 
 * @version 1.0.0
 * @date 2014-09-22
 * @author S.Kei.Cheueng
 * 
 */
public class IMMsgSocketTaskImpl extends BaseSocketTask {

	/**
	 * ���캯��
	 * @param asyncCallBack �ӿڻص�
	 */
	public IMMsgSocketTaskImpl(IAsyncCallBack_AIDL asyncCallBack) {
		super(asyncCallBack);
	}

	/**
	 * �ı���Ϣ���� {@link Constants.IMCmd.IM_TEXT_CMD}
	 * @param fromUserId	���ͷ��û�id
	 * @param fromUsername	���ͷ��û��ǳ�
	 * @param toUserId		���շ��û�id
	 * @param toUsername	���շ��û��ǳ�
	 * @param content		�ı���Ϣ����
	 * @param secretContent	���ܺ���ı���Ϣ����
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
