package com.koudai.udc.domain;

import java.io.Serializable;

public class DiscountProduct implements Serializable {

	private static final long serialVersionUID = 1654544355661122120L;

	private String id;

	private String name;

	private int type;

	public DiscountProduct(String id, String name, int type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

}
