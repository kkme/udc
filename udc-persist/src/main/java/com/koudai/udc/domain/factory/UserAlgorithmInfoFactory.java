package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.UserAlgorithmInfo;

public interface UserAlgorithmInfoFactory {

	UserAlgorithmInfo newInstance(String userId, int algorithmVersion, String sessionVersion, String userStyle);

}
