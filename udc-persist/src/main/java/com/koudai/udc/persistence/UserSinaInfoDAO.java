package com.koudai.udc.persistence;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserSinaInfo;

public interface UserSinaInfoDAO {

	void save(UserSinaInfo userSinaInfo);

	UserSinaInfo getUserSinaInfoByUserIdAndPlatform(String userId, LoginPlatform platform);

}
