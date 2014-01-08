package com.koudai.udc.statis.factory.impl;

import com.koudai.udc.statis.domain.ProductType;
import com.koudai.udc.statis.factory.ProductTypeFactory;

public class SimpleProductTypeFactory implements ProductTypeFactory {

	@Override
	public ProductType newInstance(String productId, String typeId) {
		return new ProductType(productId, typeId);
	}

}
