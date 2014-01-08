package com.koudai.udc.statis.exception;

public class TransactionCallbackException extends Exception {

	private static final long serialVersionUID = 2283278248253146399L;

	public TransactionCallbackException() {
	}

	public TransactionCallbackException(String msg) {
		super(msg);
	}

	public TransactionCallbackException(Throwable e) {
		super(e);
	}

	public TransactionCallbackException(String msg, Throwable e) {
		super(msg, e);
	}

}
