package com.koudai.udc.domain;

public enum PushType {

	AUTO(0), MANUAL(1);

	private int code;

	private PushType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
