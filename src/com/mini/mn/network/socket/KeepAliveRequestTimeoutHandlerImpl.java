package com.mini.mn.network.socket;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

import com.mini.mn.util.Log;

/**
 * ������ʱ����
 * 
 * @version 1.0.0
 * @date 2014-02-18
 * @author S.Kei.Cheueng
 */
public class KeepAliveRequestTimeoutHandlerImpl implements
		KeepAliveRequestTimeoutHandler {
	
	private static final String TAG = "MicroMsg.KeepAliveRequestTimeoutHandlerImpl";

	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter filter,
			IoSession session) throws Exception {
		Log.i(TAG,"������ʱ��");
	}

}
