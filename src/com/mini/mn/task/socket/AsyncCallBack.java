package com.mini.mn.task.socket;

import java.io.IOException;
import com.mini.mn.util.SerializerUtil;
import android.os.RemoteException;

/**
 * 网络连接异步监听抽象类
 * 
 * @version 1.0.0
 * @date 2014-10-19
 * @author S.Kei.Cheung
 */
public abstract class AsyncCallBack extends IAsyncCallBack_AIDL.Stub implements IAsyncCallBack{

	@Override
	public void onMessageSentSuccessful(byte[] message) throws RemoteException {
		try {
			onMessageSentSuccessful(SerializerUtil.deserialize(message));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessageSentFailed(byte[] exception, byte[] message)
			throws RemoteException {
		try {
			onMessageSentFailed((Exception)SerializerUtil.deserialize(exception),SerializerUtil.deserialize(message));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessageReceived(byte[] receivedMessage)
			throws RemoteException {
		try {
			onMessageReceived(SerializerUtil.deserialize(receivedMessage));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onReplyReceived_OK(byte[] replyMessage) throws RemoteException {
		try {
			onReplyReceived_OK(SerializerUtil.deserialize(replyMessage));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onReplyReceived_ERROR(byte[] replyMessage)
			throws RemoteException {
		try {
			onReplyReceived_ERROR(SerializerUtil.deserialize(replyMessage));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
