package com.koudai.udc.domain;

public enum ShopStatus {

	Normal(0), Cancel(1);

	private int code;

	private ShopStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
