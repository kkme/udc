package com.koudai.udc.domain;

import java.io.Serializable;

public class ProductPush implements Serializable {

	private static final long serialVersionUID = 4907218917923066338L;

	private Long id;

	private String machineId;

	private String productId;

	public ProductPush() {
	}

	public ProductPush(String machineId, String productId) {
		this.machineId = machineId;
		this.productId = productId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
