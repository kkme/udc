package com.koudai.udc.domain;

public enum ConfirmStatus {

	YES(1), NO(0);

	private int code;

	private ConfirmStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
