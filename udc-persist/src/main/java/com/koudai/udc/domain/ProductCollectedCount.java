package com.koudai.udc.domain;

import java.io.Serializable;

public class ProductCollectedCount implements Serializable {

	private static final long serialVersionUID = -8716403142213691837L;

	private Long id;

	private String productId;

	private int collectedCount;

	public ProductCollectedCount() {
	}

	public ProductCollectedCount(String productId) {
		this.productId = productId;
		this.collectedCount = 1;
	}

	public void increase() {
		this.collectedCount++;
	}

	public void decrease() {
		if (this.collectedCount > 0) {
			this.collectedCount--;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getCollectedCount() {
		return collectedCount;
	}

	public void setCollectedCount(int collectedCount) {
		this.collectedCount = collectedCount;
	}

}
