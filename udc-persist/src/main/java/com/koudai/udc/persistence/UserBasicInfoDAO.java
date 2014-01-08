package com.koudai.udc.persistence;

import com.koudai.udc.domain.UserBasicInfo;

public interface UserBasicInfoDAO {

	void save(UserBasicInfo userBasicInfo);

	UserBasicInfo getUserBasicInfoByThirdPartyId(String thirdPartyId);

}
