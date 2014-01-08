package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.UserActivation;

public class SimpleUserActivationFactory implements UserActivationFactory {

	@Override
	public UserActivation newInstance(String userId) {
		return new UserActivation(userId);
	}

	@Override
	public UserActivation newInstance(String userId, boolean yellowDiamond) {
		return new UserActivation(userId, yellowDiamond);
	}

}
