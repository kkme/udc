package com.koudai.udc.exception;

public class TransactionCallbackException extends Exception {

	private static final long serialVersionUID = 1407599288196896638L;

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
