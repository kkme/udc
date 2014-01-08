package com.koudai.udc.statis.exception;

public class InvalidInputParameterException extends Exception {

	private static final long serialVersionUID = 1339552564626857616L;

	public InvalidInputParameterException() {
	}

	public InvalidInputParameterException(String msg) {
		super(msg);
	}

	public InvalidInputParameterException(Throwable e) {
		super(e);
	}

	public InvalidInputParameterException(String msg, Throwable e) {
		super(msg, e);
	}

}
