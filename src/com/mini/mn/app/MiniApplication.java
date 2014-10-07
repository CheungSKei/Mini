package com.mini.mn.app;

import android.app.Application;
import android.content.Context;

import com.mini.mn.task.http.TaskObserver;

/**
 * �Զ���Application.
 * 
 * @version 2.0.0
 * @date 2014-09-15
 * @author S.Kei.Cheueng
 */
public class MiniApplication extends Application {

	/** Ӧ�������� */
	private static Context mContext;
	/** �첽��������� */
	private static TaskObserver mTaskObserver;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		MiniApplicationContext.setContext(mContext);
	}
	
	/**
	 * @return Ӧ��������
	 */
	public static Context getContext() {
		return mContext;
	}
	
	/**
	 * @return �첽���������
	 */
	public static TaskObserver getTaskObserver() {
		if (mTaskObserver == null) {
			mTaskObserver = new TaskObserver();
		}
		return mTaskObserver;
	}
	
}
