package com.koudai.udc.domain.factory;

import java.util.Date;

import com.koudai.udc.domain.ProductCollect;

public class SimpleProductCollectFactory implements ProductCollectFactory {

	@Override
	public ProductCollect newInstance(String productId, Date collectTime, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		return new ProductCollect(productId, collectTime, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId);
	}

	@Override
	public ProductCollect newInstance(String productId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		return new ProductCollect(productId, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId);
	}

}
