package com.koudai.udc.domain;

public enum ProductTopStatus {

	daily_top_ten(0), all_top_thirty(1), daily_top_one_thousand(2);

	private int code;

	private ProductTopStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
