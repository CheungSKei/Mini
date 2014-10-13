package com.mini.mn.network.socket;

import android.os.RemoteException;

import com.mini.mn.model.Entity;
import com.mini.mn.task.socket.IAsyncCallBack_AIDL;

public class RDispatcher implements IDispatcher {

	private final IMessageEvent_AIDL dispatcher;

	public RDispatcher(IMessageEvent_AIDL dispather) {
		this.dispatcher = dispather;
	}

	@Override
	public void registerMessageListener(IMessageListener_AIDL listener) {
		try {
			dispatcher.registerMessageListener(listener);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeMessageListener(IMessageListener_AIDL listener) {
		try {
			dispatcher.removeMessageListener(listener);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(Entity message) {
		try {
			dispatcher.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		try {
			dispatcher.destroy();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isConnected() {
		try {
			return dispatcher.isConnected();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getServerHost() {
		try {
			return dispatcher.getServerHost();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getServerPort() {
		try {
			return dispatcher.getServerPort();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void putAsyncCallBack(long msgId, IAsyncCallBack_AIDL asyncCallBack) {
		try {
			dispatcher.putAsyncCallBack(msgId, asyncCallBack);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IAsyncCallBack_AIDL removeAsyncCallBack(long msgId) {
		try {
			dispatcher.removeAsyncCallBack(msgId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setCookieValue(String cookieValue) {
		try {
			dispatcher.setCookieValue(cookieValue);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getCookieValue() {
		try {
			return dispatcher.getCookieValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getDeviceId() {
		try {
			return dispatcher.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
