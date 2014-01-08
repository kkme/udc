package com.koudai.udc.exception;

public class UserNotAnswerParameterException extends Exception {

	private static final long serialVersionUID = -700923260964353941L;

	public UserNotAnswerParameterException() {
		super();
	}

	public UserNotAnswerParameterException(String msg, Throwable e) {
		super(msg, e);
	}

	public UserNotAnswerParameterException(String msg) {
		super(msg);
	}

	public UserNotAnswerParameterException(Throwable e) {
		super(e);
	}

}
