package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.UserActivation;

public interface UserActivationFactory {

	UserActivation newInstance(String userID);

	UserActivation newInstance(String userId, boolean yellowDiamond);

}
