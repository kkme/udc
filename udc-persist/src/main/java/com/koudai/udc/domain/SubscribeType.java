package com.koudai.udc.domain;

public enum SubscribeType {

	Curve(0), Shelf(1);

	private int code;

	private SubscribeType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
