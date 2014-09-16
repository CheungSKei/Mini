package com.mini.mn.task.http;

import com.mini.mn.util.StringUtils;


/**
 * ����ִ�н��.
 * 
 * @param <T> ���񷵻ؽ������������
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class TaskResult<T> {
	
	/** ����ִ�гɹ� */
	public static final int OK = 1;
	/** �ѵ���ĩҳ */
	public static final int ENDPAGE = 11;
	/** ����ִ��ʧ�� */
	public static final int FAILED = -1;
	/** ����ִ�гɹ�, �����ؽ��Ϊ�� */
	public static final int EMPTY = -2;
	/** ����ִ���׳����� */
	public static final int ERROR = -3;
	/**�����쳣*/
	public static final int EXCEPTION = -11;
	
	/** ����ID */
	private int mTaskKey = -1;
	
	/** �б���ҳ�� */
	private int mTotalPage = 0;
	
	/** ����ִ����ɺ�Ľ���� */
	private int mCode = 0;
	
	/** ���ص����� */
	private T mData = null;
	
	/** ���������ص���ʾ��Ϣ(������ֵΪ 'OK' or 'FAILED', �Ӹ��ֶ�ȡ��Ϣ) */
	private String mMsg = "";
	
	/** ���������صĴ�����Ϣ(������ֵΪ 'ERROR', �Ӹ��ֶ�ȡ��Ϣ) */
	private String mErrorMsg = "";
	
	/** ������ִ���׳��쳣ʱ, �����Բ�Ϊ�� */
	private Exception mException = null;

	/**
	 * @return ����ִ����ɺ�Ľ����
	 */
	public int getCode() {
		return mCode;
	}

	public void setCode(int code) {
		this.mCode = code;
	}
	
	/**
	 * @return ����ִ����ɺ��б���ҳ��
	 */
	public int getTotalPage() {
		return mTotalPage;
	}

	/**
	 * �ɰ汾�Ƽ�ҳ�ŷ���,�°汾ͳһ��data�ṹ����
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.mTotalPage = totalPage;
	}

	/**
	 * @return ���񷵻ص�����
	 */
	public T getData() {
		return mData;
	}

	public void setData(T data) {
		this.mData = data;
	}

	/**
	 * @return ������ִ���׳��쳣ʱ, �÷������ؽ����Ϊ��.
	 */
	public Exception getException() {
		return mException;
	}

	public void setException(Exception exception) {
		this.mException = exception;
	}

	public String getMsg() {
		return mMsg;
	}

	public void setMsg(String msg) {
		this.mMsg = msg;
	}

	public String getErrorMsg() {
		if (!StringUtils.isEmpty(mErrorMsg)) {
			return mErrorMsg;
		}
		return mException == null ? "" : mException.getMessage();
	}

	public void setErrorMsg(String errorMsg) {
		this.mErrorMsg = errorMsg;
	}

	public int getTaskKey() {
		return mTaskKey;
	}

	public void setTaskKey(int taskKey) {
		this.mTaskKey = taskKey;
	}
	
}
