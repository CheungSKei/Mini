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
 * 异步任务基类, 通过实现Observer提供取消任务功能. 所有的业务处理任务都需继承此类, 
 * 当任务执行后需要将该任务 添加到{@link TaskObserver}进行统一管理.
 * 
 * @param <T>  返回结果实体类型
 * @version 2.0.0
 * @date 2013-11-17
 * @author S.Kei.Cheung
 */
public abstract class BaseHttpTask<T> extends
		AsyncTask<TaskParams, Object, TaskResult<T>> implements Observer {

	/** 任务ID */
	protected Integer mTaskKey = -1;
	/** 任务依附的context */
	protected Context mContext = null;
	/** 任务监听器 */
	protected TaskListener<T> mTaskListener = null;

	/**
	 * 构建异步任务实例.
	 * 
	 * @param activity 任务依附的Activity实例
	 * @param taskListener 任务监听器
	 * @param taskKey 任务ID
	 */
	public BaseHttpTask(Context context, TaskListener<T> taskListener,
			Integer taskKey) {
		this.mContext = context;
		this.mTaskListener = taskListener;
		this.mTaskKey = taskKey;
	}
	
	/**
	 * 获取Context实例.
	 * @return
	 */
	protected Context getContext() {
		return mContext;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// 如果返回false, 说明有相同的任务正在执行, 则取消此任务
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
		// 处理返回结果
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
