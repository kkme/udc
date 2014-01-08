package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductCollectedCount;

public class SimpleProductCollectedCountFactory implements ProductCollectedCountFactory {

	@Override
	public ProductCollectedCount newInstance(String productId) {
		return new ProductCollectedCount(productId);
	}

}
