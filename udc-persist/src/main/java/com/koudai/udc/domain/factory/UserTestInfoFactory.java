package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.UserTestInfo;

public interface UserTestInfoFactory {

	UserTestInfo newInstance(String userId, String questionId, String answerId);

}
