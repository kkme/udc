package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductCollectBijia;

public interface ProductCollectBijiaFactory {

	ProductCollectBijia newInstance(String productId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId, int priceReduction);

}
