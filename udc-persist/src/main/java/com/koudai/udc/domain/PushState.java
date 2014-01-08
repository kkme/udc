package com.koudai.udc.domain;

public enum PushState {

	NO(0), YES(1);

	private int code;

	private PushState(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
