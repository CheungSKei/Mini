package com.mini.mn.app;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

import com.mini.mn.platformtools.SyncTask;
import com.mini.mn.task.http.TaskObserver;
import com.mini.mn.util.Log;

/**
 * 自定义Application.
 * 
 * @version 2.0.0
 * @date 2014-09-15
 * @author S.Kei.Cheueng
 */
public class MiniApplication extends Application {

	private static final String TAG = "MiniMsg.MiniApplication";
	
	// 包名
	private static String pkgName = "com.mini.mn";
	
	/** 应用上下文 */
	private static Context mContext;
	/** 异步任务管理器 */
	private static TaskObserver mTaskObserver;
	// 资料--解决多进程多次执行Application问题
	public abstract static class Profile {

		protected final MiniApplication app;

		public Profile(MiniApplication app) {
			this.app = app;
		}

		public abstract void onCreate();

		public abstract void onConfigurationChanged(Configuration config);

		public void onTerminate() {
			// do nothing
		}
	}

	private Profile profile = null;
	
	@Override
	public void onCreate() {
		mContext = getApplicationContext();
		MiniApplicationContext.setContext(mContext);
		
		super.onCreate();

		// start a new thread
		final HandlerThread t = new HandlerThread("startup");
		t.start();

		new SyncTask<Integer>(2000, 0) {

			@Override
			protected Integer run() {
				while (!updateProcessName()) {
					try {
						Thread.sleep(100);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return null;
			}

		}.exec(new Handler(t.getLooper()));

		// exit now
		t.getLooper().quit();

		if (profile != null) {
			// 初始化个人信息onCreate,不在工作进程中onCreate
			profile.onCreate();
		} else {
			
		}
	}
	
	// 更新当前进程名称
	private boolean updateProcessName() {
		if (profile != null) {
			Log.e(TAG, "skipped update process name, already setup as " + profile);
			return true;
		}

		final String process = getProcessName();

		if (process == null) {
			Log.e(TAG, "get process name failed, retry later");
			return false;
		}

		if (process.equals(WorkerProfile.PROCESS_NAME)) {
			// 工作进程
			profile = new WorkerProfile(this);
		}else if (process.equals(PusherProfile.PROCESS_NAME)) {
			// 网络进程
			profile = new PusherProfile(this);
		}else {
			return false;
		}

		Log.w(TAG, "application started, profile = %s", profile.toString());

		return true;
	}
	
	private String getProcessName() {
		final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (final RunningAppProcessInfo i : am.getRunningAppProcesses()) {
			if (i.pid == Process.myPid()) {
				return i.processName;
			}
		}
		return null;
	}
	
	/**
	 * @return 应用上下文
	 */
	public static Context getContext() {
		return mContext;
	}
	
	/**
	 * @return 异步任务管理器
	 */
	public static TaskObserver getTaskObserver() {
		if (mTaskObserver == null) {
			mTaskObserver = new TaskObserver();
		}
		return mTaskObserver;
	}
	
	
	
	public static String getDefaultPreferencePath() {
		return pkgName + "_preferences";
	}
}
