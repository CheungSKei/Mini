package com.mini.mn.db;

public class AccountNotReadyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7766139994410539838L;

	public static final int DEFAULT_EXCEPTION = 0;
	public static final int DBVER_EXCEPTION = 1;

	private final int exceptionType;

	public AccountNotReadyException() {
		exceptionType = DEFAULT_EXCEPTION;
	}

	public AccountNotReadyException(final int exceptionType) {
		this.exceptionType = exceptionType;
	}

	public boolean isDBVerException() {
		if (exceptionType == DBVER_EXCEPTION) {
			return true;
		}
		return false;
	}

}
