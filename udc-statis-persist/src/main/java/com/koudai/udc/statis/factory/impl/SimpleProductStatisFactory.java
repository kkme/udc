package com.koudai.udc.statis.factory.impl;

import com.koudai.udc.statis.domain.ProductStatis;
import com.koudai.udc.statis.factory.ProductStatisFactory;

public class SimpleProductStatisFactory implements ProductStatisFactory {

	@Override
	public ProductStatis newInstance(String productId) {
		return new ProductStatis(productId);
	}

}
