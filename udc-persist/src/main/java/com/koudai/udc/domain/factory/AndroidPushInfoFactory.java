package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.AndroidPushInfo;

public interface AndroidPushInfoFactory {

	AndroidPushInfo newInstance(String machineId, String firstProductName, String productIds, int infoType);

	AndroidPushInfo newInstance(String machineId, String firstProductName, String productIds, int infoType, int pushType, String manualId);

}
