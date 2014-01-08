package com.koudai.udc.persistence;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserQQInfo;

public interface UserQQInfoDAO {

	void save(UserQQInfo userQQInfo);

	UserQQInfo getUserQQInfoByUserIdAndPlaform(String userId, LoginPlatform platform);

}
