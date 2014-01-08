package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductCollectBijia;

public class SimpleProductCollectBijiaFactory implements ProductCollectBijiaFactory {

	@Override
	public ProductCollectBijia newInstance(String productId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId, int priceReduction) {
		return new ProductCollectBijia(productId, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId, priceReduction);
	}

}
