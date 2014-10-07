package com.mini.mn.app;

import android.app.Application;
import android.content.Context;

import com.mini.mn.task.http.TaskObserver;

/**
 * 自定义Application.
 * 
 * @version 2.0.0
 * @date 2014-09-15
 * @author S.Kei.Cheueng
 */
public class MiniApplication extends Application {

	/** 应用上下文 */
	private static Context mContext;
	/** 异步任务管理器 */
	private static TaskObserver mTaskObserver;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		MiniApplicationContext.setContext(mContext);
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
	
}
