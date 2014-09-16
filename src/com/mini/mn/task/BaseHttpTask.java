package com.mini.mn.task;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.os.AsyncTask;

import com.mini.mn.app.MiniApplication;
import com.mini.mn.task.http.TaskListener;
import com.mini.mn.task.http.TaskObserver;
import com.mini.mn.task.http.TaskParams;
import com.mini.mn.task.http.TaskResult;

/**
 * �첽�������, ͨ��ʵ��Observer�ṩȡ��������. ���е�ҵ����������̳д���, 
 * ������ִ�к���Ҫ�������� ��ӵ�{@link TaskObserver}����ͳһ����.
 * 
 * @param <T>  ���ؽ��ʵ������
 * @version 2.0.0
 * @date 2013-11-17
 * @author S.Kei.Cheung
 */
public abstract class BaseHttpTask<T> extends
		AsyncTask<TaskParams, Object, TaskResult<T>> implements Observer {

	/** ����ID */
	protected Integer mTaskKey = -1;
	/** ����������context */
	protected Context mContext = null;
	/** ��������� */
	protected TaskListener<T> mTaskListener = null;

	/**
	 * �����첽����ʵ��.
	 * 
	 * @param activity ����������Activityʵ��
	 * @param taskListener ���������
	 * @param taskKey ����ID
	 */
	public BaseHttpTask(Context context, TaskListener<T> taskListener,
			Integer taskKey) {
		this.mContext = context;
		this.mTaskListener = taskListener;
		this.mTaskKey = taskKey;
	}
	
	/**
	 * ��ȡContextʵ��.
	 * @return
	 */
	protected Context getContext() {
		return mContext;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// �������false, ˵������ͬ����������ִ��, ��ȡ��������
		if (mTaskListener != null) {
			if (!mTaskListener.preExecute(this, mTaskKey)) {
				this.cancel(true);
			}
		}
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		if(isCancelled()) {
			return;
		}
		super.onProgressUpdate(values);
	}
	
	@Override
	protected TaskResult<T> doInBackground(TaskParams... params) {
		if(isCancelled()) return null;
		return doInBackgroundForChildren(params);
	}
	
	protected abstract TaskResult<T> doInBackgroundForChildren(TaskParams... params);
	
	@Override
	protected void onPostExecute(TaskResult<T> result) {
		super.onPostExecute(result);
		// �����ؽ��
		if (result != null) {
			result.setTaskKey(mTaskKey);
		}
		if (mTaskListener != null) {
			mTaskListener.onResult(result);
		}
		release();
	}
	
	@Override
	protected void onCancelled() {
		release();
		super.onCancelled();
	}

	@Override
	public void update(Observable observable, Object data) {
		if (mTaskKey == (Integer) data) {
			if (this.getStatus() == AsyncTask.Status.RUNNING) {
				this.cancel(true);
			}
		}
	}
	
	private void release() {
		mContext = null;
		mTaskListener = null;
		MiniApplication.getTaskObserver().deleteObserver(this);
	}

}
