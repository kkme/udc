package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.BindingInfo;

public interface BindingInfoFactory {

	BindingInfo newInstance(String machineId, String userId);

}
