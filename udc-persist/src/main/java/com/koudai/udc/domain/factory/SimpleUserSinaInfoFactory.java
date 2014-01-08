package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserSinaInfo;

public class SimpleUserSinaInfoFactory implements UserSinaInfoFactory {

	@Override
	public UserSinaInfo newInstance(String userId, String token, int expire, String nick, LoginPlatform platform) {
		return new UserSinaInfo(userId, token, expire, nick, platform);
	}

}
