package com.mini.mn.exception;

/**
 * ����ͼƬ�ļ��������쳣.
 * 
 * @version 1.0.0
 * @date 2014-1-27
 * @author S.Kei.Cheung
 */
public class ImageFileNotFoundException extends Exception {

	private static final long serialVersionUID = 5230344671343779968L;

	public ImageFileNotFoundException() {
		super();
	}

	public ImageFileNotFoundException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ImageFileNotFoundException(String detailMessage) {
		super(detailMessage);
	}

	public ImageFileNotFoundException(Throwable throwable) {
		super(throwable);
	}

}
