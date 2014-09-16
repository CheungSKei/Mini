package com.mini.mn.exception;

/**
 * ��ȡHTTP��Ӧ, �����JSON����ʱ�����쳣.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class HttpResponseException extends Exception {

	private static final long serialVersionUID = -3112863384642807239L;

	public HttpResponseException() {
		super();
	}

	public HttpResponseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public HttpResponseException(String detailMessage) {
		super(detailMessage);
	}

	public HttpResponseException(Throwable throwable) {
		super(throwable);
	}

}
