package com.mini.mn.app;

import java.util.Locale;

import junit.framework.Assert;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.IBinder;

import com.mini.mn.booter.CoreService;
import com.mini.mn.booter.CoreServiceHelper;
import com.mini.mn.network.socket.IMessageEvent_AIDL;
import com.mini.mn.network.socket.RDispatcher;
import com.mini.mn.platformtools.StrictModeHelper;
import com.mini.mn.util.Log;

/**
 * 工作线程
 * 
 * @version 1.0.0
 * @date 2014-10-11
 * @author S.Kei.Cheueng
 */
final class WorkerProfile extends MiniApplication.Profile{
	private static final String TAG = "MiniMsg.WorkerProfile";
	public static final String PROCESS_NAME = MiniApplicationContext.getPackageName() + "";
	protected Locale locale;
	

	public WorkerProfile(MiniApplication app) {
		super(app);
	}

	@Override
	public void onCreate() {

		// set up strict mode for debug
		StrictModeHelper.enableStrictMode(com.mini.mn.BuildConfig.DEBUG);

		bindCore(app);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

	}

	@Override
	public void onConfigurationChanged(Configuration config) {
		
	}

	@Override
	public String toString() {
		return PROCESS_NAME;
	}

	// CoreService
	private final ServiceConnection connCore = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder binder) {
			Log.w(TAG, "onServiceConnected 连接成功");

			if (binder == null) {
				Assert.assertTrue("WorkerProfile onServiceConnected binder == null", false);
			}
			final RDispatcher dispatcher = new RDispatcher(IMessageEvent_AIDL.Stub.asInterface(binder));
			MiniCore.setMessageEvent(dispatcher);
		}

		public void onServiceDisconnected(ComponentName name) {
			Log.w(TAG, "onServiceDisconnected 重新连接");
		}
	};
	
	public void bindCore(Context context) {
		CoreServiceHelper.ensureServiceInstance(app, CoreServiceHelper.TYPE_NOOP);

		Log.i(TAG, "prepare dispatcher / bind core service");
		final Intent intent = new Intent(context, CoreService.class);
		if (app.bindService(intent, connCore, Context.BIND_AUTO_CREATE)) {
			return;
		}

		Log.e(TAG, "bindService failed, may be caused by some crashes");
	}
}
