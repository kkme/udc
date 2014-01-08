package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserQQInfo;

public class SimpleUserQQInfoFactory implements UserQQInfoFactory {

	@Override
	public UserQQInfo newInstance(String userId, String token, int expire, String nick, LoginPlatform platform) {
		return new UserQQInfo(userId, token, expire, nick, platform);
	}

}
