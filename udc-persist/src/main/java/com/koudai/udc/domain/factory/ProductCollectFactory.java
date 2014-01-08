package com.koudai.udc.domain.factory;

import java.util.Date;

import com.koudai.udc.domain.ProductCollect;

public interface ProductCollectFactory {

	ProductCollect newInstance(String productId, Date collectTime, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId);

	ProductCollect newInstance(String productId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId);

}
