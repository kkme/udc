package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.List;

import com.koudai.udc.statis.domain.ProductRecommend;
import com.koudai.udc.statis.domain.ProductType;

public class Editor implements Serializable {

	private static final long serialVersionUID = -1274001492020282241L;

	private ProductRecommend productRecommend;

	private List<ProductType> productTypes;

	public Editor(ProductRecommend productRecommend, List<ProductType> productTypes) {
		this.productRecommend = productRecommend;
		this.productTypes = productTypes;
	}

	public ProductRecommend getProductRecommend() {
		return productRecommend;
	}

	public void setProductRecommend(ProductRecommend productRecommend) {
		this.productRecommend = productRecommend;
	}

	public List<ProductType> getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(List<ProductType> productTypes) {
		this.productTypes = productTypes;
	}

}
