package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.AndroidPushInfo;

public class SimpleAndroidPushInfoFactory implements AndroidPushInfoFactory {

	@Override
	public AndroidPushInfo newInstance(String machineId, String firstProductName, String productIds, int infoType) {
		return new AndroidPushInfo(machineId, firstProductName, productIds, infoType);
	}

	@Override
	public AndroidPushInfo newInstance(String machineId, String firstProductName, String productIds, int infoType, int pushType, String manualId) {
		return new AndroidPushInfo(machineId, firstProductName, productIds, infoType, pushType, manualId);
	}

}
