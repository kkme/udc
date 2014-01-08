package com.koudai.udc.statis.action;

import java.io.Serializable;

import com.koudai.udc.statis.domain.ProductStatis;

public class EditorProductStatis implements Serializable {

	private static final long serialVersionUID = 2497601896178554920L;

	private String userId;

	private ProductStatis statis;

	public EditorProductStatis(String userId, ProductStatis statis) {
		this.userId = userId;
		this.statis = statis;
	}

	public String getUserId() {
		return userId;
	}

	public ProductStatis getStatis() {
		return statis;
	}

}
