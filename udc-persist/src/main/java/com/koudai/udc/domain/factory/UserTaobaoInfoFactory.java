package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserTaobaoInfo;

public interface UserTaobaoInfoFactory {

	UserTaobaoInfo newInstance(String userId, String taobaoUserId, String token, int expire, String refreshToken, int refreshExpire, LoginPlatform platform);

	UserTaobaoInfo newInstance(String userId, LoginPlatform platform);

}
