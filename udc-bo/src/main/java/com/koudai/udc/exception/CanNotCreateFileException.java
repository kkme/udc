package com.koudai.udc.exception;

public class CanNotCreateFileException extends Exception {

	private static final long serialVersionUID = -7637066900951247547L;

	public CanNotCreateFileException() {
	}

	public CanNotCreateFileException(String message, Throwable e) {
		super(message, e);
	}

	public CanNotCreateFileException(String message) {
		super(message);
	}

	public CanNotCreateFileException(Throwable e) {
		super(e);
	}

}
