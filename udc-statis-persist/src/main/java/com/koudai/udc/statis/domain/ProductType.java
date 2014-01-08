package com.koudai.udc.statis.domain;

import java.io.Serializable;

public class ProductType implements Serializable {

	private static final long serialVersionUID = -8030644100410656191L;

	private Long id;

	private String productId;

	private String typeId;

	public ProductType() {
	}

	public ProductType(String productId, String typeId) {
		this.productId = productId;
		this.typeId = typeId;
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
