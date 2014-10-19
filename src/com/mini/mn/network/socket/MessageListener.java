package com.mini.mn.network.socket;

import java.io.IOException;

import com.mini.mn.platformtools.MMHandlerThread;
import com.mini.mn.util.SerializerUtil;

import android.net.NetworkInfo;
import android.os.RemoteException;

/**
 * 网络连接监听抽象类
 * 
 * @version 1.0.0
 * @date 2014-10-19
 * @author S.Kei.Cheung
 */
public abstract class MessageListener extends IMessageListener_AIDL.Stub implements IMessageListener{

	@Override
	public void onConnectionClosed() throws RemoteException {
		
	}

	@Override
	public void onConnectionSuccessful() throws RemoteException {
		
	}

	@Override
	public void onConnectionFailed(byte[] exception) throws RemoteException {
		try {
			onConnectionFailed((Exception)SerializerUtil.deserialize(exception));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onExceptionCaught(byte[] throwable) throws RemoteException {
		try {
			onExceptionCaught((Throwable)SerializerUtil.deserialize(throwable));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessageReceived(byte[] message) throws RemoteException {
		final byte[] messages = message;
		MMHandlerThread.postToMainThread(new Runnable(){
			@Override
			public void run() {
				try {
					onMessageReceived(SerializerUtil.deserialize(messages));
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onReplyReceived(byte[] reply) throws RemoteException {
		final byte[] replays = reply;
		MMHandlerThread.postToMainThread(new Runnable(){
			@Override
			public void run() {
				try {
					onReplyReceived(SerializerUtil.deserialize(replays));
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onSentSuccessful(byte[] message) throws RemoteException {
		final byte[] messages = message;
		MMHandlerThread.postToMainThread(new Runnable(){
			@Override
			public void run() {
				try {
					onSentSuccessful(SerializerUtil.deserialize(messages));
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onSentFailed(byte[] exception, byte[] message)
			throws RemoteException {
		try {
			onSentFailed((Exception)SerializerUtil.deserialize(message),SerializerUtil.deserialize(message));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onNetworkChanged(byte[] info) throws RemoteException {
		try {
			onNetworkChanged((NetworkInfo)SerializerUtil.deserialize(info));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
