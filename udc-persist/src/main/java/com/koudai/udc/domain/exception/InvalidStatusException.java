package com.koudai.udc.domain.exception;

public class InvalidStatusException extends Exception {

	private static final long serialVersionUID = 5023962410149267588L;

	public InvalidStatusException() {
		super();
	}

	public InvalidStatusException(String msg, Throwable e) {
		super(msg, e);
	}

	public InvalidStatusException(String msg) {
		super(msg);
	}

	public InvalidStatusException(Throwable e) {
		super(e);
	}

}
