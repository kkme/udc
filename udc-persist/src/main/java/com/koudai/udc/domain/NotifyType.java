package com.koudai.udc.domain;

public enum NotifyType {

	Email(0), Shelf(1), Both(2);

	private int code;

	private NotifyType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
