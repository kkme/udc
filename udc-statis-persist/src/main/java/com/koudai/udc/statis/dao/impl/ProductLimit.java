package com.koudai.udc.statis.dao.impl;

import java.io.Serializable;

public class ProductLimit implements Serializable {

	private static final long serialVersionUID = -5399035224103808321L;

	private String productId;

	private String startTime;

	private String endTime;

	public ProductLimit() {
	}

	public ProductLimit(String productId, String startTime, String endTime) {
		this.productId = productId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
