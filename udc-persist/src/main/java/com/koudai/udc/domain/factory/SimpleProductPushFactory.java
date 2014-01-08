package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductPush;

public class SimpleProductPushFactory implements ProductPushFactory {

	@Override
	public ProductPush newInstance(String machineId, String productId) {
		return new ProductPush(machineId, productId);
	}

}
