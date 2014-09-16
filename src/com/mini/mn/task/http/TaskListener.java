package com.mini.mn.task.http;

import java.util.HashMap;

import com.mini.mn.app.MiniApplication;
import com.mini.mn.task.BaseHttpTask;
import com.mini.mn.util.ToastUtils;

import android.os.AsyncTask;
import android.widget.Toast;

/**
 * ���������.
 * 
 * @param <T> ����ִ�н���ķ�������
 * @version 1.0.0
 * @date 2012-9-21
 * @author DengZhaoyong
 */
@SuppressWarnings("all")
public abstract class TaskListener<T> {
	
	/** �������첽�����б� */	
	protected HashMap<Integer, BaseHttpTask> mTaskMap;

	public TaskListener(HashMap<Integer, BaseHttpTask> taskMap) {
		this.mTaskMap = taskMap;
	}
	
	/**
	 * ��ǰ����ǰ����ӵ�����۲���������.
	 * 
	 * @param task ʵ����Observer�ӿڵ�����ʵ��
	 * @param taskKey �����ʶ
	 * @return ��������·���true, �������false, Ӧ����ֹ������.
	 */
	@SuppressWarnings("unchecked")
	public boolean preExecute(BaseHttpTask<T> task, Integer taskKey) {
		
		boolean flag = true;
		
		/*
		 * �����ǰActivity�����б��Ѵ��ڶ�Ӧ������Cancel֮ǰ���������������
		 * ���� falseʱȡ������
		 */
		if (mTaskMap != null) {
			BaseHttpTask<T> taskInMap = mTaskMap.get(taskKey);
			
			if (taskInMap != null){
				taskInMap.cancel(true);
				MiniApplication.getTaskObserver().cancelTask(taskKey);
				mTaskMap.remove(taskKey);
			}
			// ��������ӵ�TaskObserver����ͳһ����
			MiniApplication.getTaskObserver().addObserver(task);
			// ��������ӵ��첽�����б���
			mTaskMap.put(taskKey, task);
		}
		
		return flag;
	}
	
	/**
	 * ���񷵻غ�Ҫִ�еĲ���.
	 * 
	 * @param result
	 */
	public void onResult(TaskResult<T> result) {
		switch (result.getCode()) {
		case TaskResult.ERROR:
			ToastUtils.showMessage(MiniApplication.getContext(),
					result.getErrorMsg());
			break;
		}
	}
	
	/**
	 * �ͷ���Դ.
	 */
	public void release() {
		this.mTaskMap = null;
	}
	
}
