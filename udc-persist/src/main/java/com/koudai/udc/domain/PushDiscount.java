package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.List;

public class PushDiscount implements Serializable {

	private static final long serialVersionUID = -6817219366891856988L;

	private static final String COLON = ":";

	private static final String COMMA = ",";

	private String machineId;

	private String token;

	private List<DiscountProduct> discountProducts;

	public PushDiscount(String machineId, List<DiscountProduct> discountProducts) {
		this.machineId = machineId;
		this.discountProducts = discountProducts;
	}

	public PushDiscount(String machineId, String token, List<DiscountProduct> discountProducts) {
		this.machineId = machineId;
		this.token = token;
		this.discountProducts = discountProducts;
	}

	public String getMachineId() {
		return machineId;
	}

	public String getToken() {
		return token;
	}

	public List<DiscountProduct> getDiscountProducts() {
		return discountProducts;
	}

	public String getFirstProductName() {
		if (discountProducts != null && !discountProducts.isEmpty()) {
			return discountProducts.get(0).getName();
		}
		return null;
	}

	public String getProductIds() {
		StringBuffer ids = new StringBuffer();
		for (DiscountProduct discountProduct : discountProducts) {
			ids.append(discountProduct.getId()).append(COLON).append(String.valueOf(discountProduct.getType())).append(COMMA);
		}
		if (ids.length() > 0) {
			ids.deleteCharAt(ids.length() - 1);
		}
		return ids.toString();
	}

	public int getInfoType() {
		if (discountProducts != null && discountProducts.size() > 1) {
			return PushInfoType.MULTIPLE.getCode();
		}
		return PushInfoType.SINGLE.getCode();
	}
}
