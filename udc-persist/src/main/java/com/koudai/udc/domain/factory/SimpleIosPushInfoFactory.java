package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.IosPushInfo;
import com.koudai.udc.domain.Platform;

public class SimpleIosPushInfoFactory implements IosPushInfoFactory {

	@Override
	public IosPushInfo newInstance(String machineId, String firstProductName, String productIds, int infoType, String token, Platform platform) {
		return new IosPushInfo(machineId, firstProductName, productIds, infoType, token, platform);
	}

}
