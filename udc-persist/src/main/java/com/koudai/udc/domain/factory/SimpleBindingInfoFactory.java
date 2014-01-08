package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.BindingInfo;

public class SimpleBindingInfoFactory implements BindingInfoFactory {

	@Override
	public BindingInfo newInstance(String machineId, String userId) {
		return new BindingInfo(machineId, userId);
	}

}
