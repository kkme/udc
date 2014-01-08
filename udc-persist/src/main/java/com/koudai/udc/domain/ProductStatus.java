package com.koudai.udc.domain;

public enum ProductStatus {

	Normal(0), Cancel(1);

	private int code;

	private ProductStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
