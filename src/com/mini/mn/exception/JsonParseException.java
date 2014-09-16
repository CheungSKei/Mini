package com.mini.mn.exception;

/**
 * JSON���ݽ����쳣.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class JsonParseException extends Exception {

	private static final long serialVersionUID = -2605379079837974612L;

	public JsonParseException() {
		super();
	}

	public JsonParseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public JsonParseException(String detailMessage) {
		super(detailMessage);
	}

	public JsonParseException(Throwable throwable) {
		super(throwable);
	}
	
}
