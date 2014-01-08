package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserTaobaoInfo;

public class SimpleUserTaobaoInfoFactory implements UserTaobaoInfoFactory {

	@Override
	public UserTaobaoInfo newInstance(String userId, String taobaoUserId, String token, int expire, String refreshToken, int refreshExpire, LoginPlatform platform) {
		return new UserTaobaoInfo(userId, taobaoUserId, token, expire, refreshToken, refreshExpire, platform);
	}

	@Override
	public UserTaobaoInfo newInstance(String userId, LoginPlatform platform) {
		return new UserTaobaoInfo(userId, platform);
	}

}
