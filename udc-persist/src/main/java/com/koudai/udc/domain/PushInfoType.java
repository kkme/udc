package com.koudai.udc.domain;

public enum PushInfoType {

	SINGLE(0), MULTIPLE(1);

	private int code;

	private PushInfoType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
