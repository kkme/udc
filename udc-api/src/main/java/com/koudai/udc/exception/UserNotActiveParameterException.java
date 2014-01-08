package com.koudai.udc.exception;

public class UserNotActiveParameterException extends Exception {

	private static final long serialVersionUID = -6421523356064915698L;

	public UserNotActiveParameterException() {
		super();
	}

	public UserNotActiveParameterException(String msg, Throwable e) {
		super(msg, e);
	}

	public UserNotActiveParameterException(String msg) {
		super(msg);
	}

	public UserNotActiveParameterException(Throwable e) {
		super(e);
	}

}
