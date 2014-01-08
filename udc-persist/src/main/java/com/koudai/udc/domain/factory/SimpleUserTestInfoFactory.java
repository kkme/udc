package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.UserTestInfo;

public class SimpleUserTestInfoFactory implements UserTestInfoFactory {

	@Override
	public UserTestInfo newInstance(String userId, String questionId, String answerId) {
		return new UserTestInfo(userId, questionId, answerId);
	}

}
