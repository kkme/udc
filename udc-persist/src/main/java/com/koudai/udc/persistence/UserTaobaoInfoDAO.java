package com.koudai.udc.persistence;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserTaobaoInfo;

public interface UserTaobaoInfoDAO {

	void save(UserTaobaoInfo userTaobaoInfo);

	UserTaobaoInfo getUserTaobaoInfoByUserIdAndPlatform(String userId, LoginPlatform platform);

}
