 
package com.mini.mn.booter;

import com.mini.mn.constant.Constants;
import com.mini.mn.network.socket.MessageConnectorManager;
import com.mini.mn.util.Log;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;


/**
 * 与服务端连接服务
 * 同时处理业务逻辑
 * 
 * @version 1.0.0
 * @date 2014-2-13
 * @author S.Kei.Cheung
 */
public class CoreService extends Service {

	private static final String TAG = "MiniMsg.CoreService";
	
    MessageConnectorManager manager;
    
    @Override
    public void onCreate() {
       
    	manager = MessageConnectorManager.getManager();
    	manager.createExceptionCaughtHandler();
    	manager.createMessageReceivedHandler();
    	manager.createMessageSentFailedHandler();
    	manager.createMessageSentSuccessfulHandler();
    	manager.createReplyReceivedHandler();
    	manager.createSessionClosedHandler();
    	manager.createSessionCreatedHandler();
    	manager.createConnectionFailedHandler();
    }

    @Override
    public void onStart(Intent intent, int startId) {
    	if(intent!=null)
    	{
	    	String host = Constants.MESSAGE_SERVER_HOST;
	    	int port = Constants.MESSAGE_SERVER_PORT;
	    	if(host!=null)
	    	{
	    		connect(host,port);
	    	}
    	}
    }
   
    public void connect(String host,int port)
    {
    	manager.connect(host,port);
    }

    @Override
    public void onDestroy() {
    	try {
			manager.destroy();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }

    public void stop() {
    	this.stopSelf();
    }
    
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind~~~ threadID:" + Thread.currentThread());
		return manager;
	}

	@Override
	public void onRebind(Intent intent) {
		Log.d(TAG, "onRebind~~~ threadID:" + Thread.currentThread());
		super.onRebind(intent);
	}
	
    public class LocalBinder extends Binder{
    	
    	public CoreService getService()
    	{
            return CoreService.this;
        }
    }
	
 
}
