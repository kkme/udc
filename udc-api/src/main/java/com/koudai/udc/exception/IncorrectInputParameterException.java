package com.koudai.udc.exception;

public class IncorrectInputParameterException extends Exception {

	private static final long serialVersionUID = 4870579614298729849L;

	public IncorrectInputParameterException() {
		super();
	}

	public IncorrectInputParameterException(String msg, Throwable e) {
		super(msg, e);
	}

	public IncorrectInputParameterException(String msg) {
		super(msg);
	}

	public IncorrectInputParameterException(Throwable e) {
		super(e);
	}

}
