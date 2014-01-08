package com.koudai.udc.exception;

public class ActionErrorDispatchException extends RuntimeException {

	private static final long serialVersionUID = 6256706756158106072L;

	private String resultCode;

	public ActionErrorDispatchException(String resultCode, Throwable cause) {
		super("Problem occured for which the transaction of an action must be rolled back", cause);
		this.resultCode = resultCode;
	}

	public String getResultCode() {
		return resultCode;
	}

}
