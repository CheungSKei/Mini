package com.mini.mn.task.http;

import java.util.Observable;

/**
 * �첽����ͳһ������.
 * ���е��첽����ʵ������Ҫ���� TaskObserver.addObserver() ��ӵ��˹������н���ͳһ����.
 * 
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class TaskObserver extends Observable {

	/**
	 * ȡ��,�ж�һ������ִ�е��쳣����.
	 * 
	 * @param taskKey ����ID
	 */
	public void cancelTask(Integer taskKey) {
		setChanged();
		notifyObservers(taskKey);
	}
	
}
