package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserSinaInfo;

public interface UserSinaInfoFactory {

	UserSinaInfo newInstance(String userId, String token, int expire, String nick, LoginPlatform platform);

}
