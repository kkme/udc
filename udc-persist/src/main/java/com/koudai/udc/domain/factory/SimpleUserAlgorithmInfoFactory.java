package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.UserAlgorithmInfo;

public class SimpleUserAlgorithmInfoFactory implements UserAlgorithmInfoFactory {

	@Override
	public UserAlgorithmInfo newInstance(String userId, int algorithmVersion, String sessionVersion, String userStyle) {
		return new UserAlgorithmInfo(userId, algorithmVersion, sessionVersion, userStyle);
	}

}
