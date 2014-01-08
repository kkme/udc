package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.IosPushInfo;
import com.koudai.udc.domain.Platform;

public interface IosPushInfoFactory {

	IosPushInfo newInstance(String machineId, String firstProductName, String productIds, int infoType, String token, Platform platform);

}
