package com.koudai.udc.persistence;

import com.koudai.udc.domain.UserAlgorithmInfo;

public interface UserAlgorithmInfoDAO {

	void save(UserAlgorithmInfo userAlgorithmInfo);

	UserAlgorithmInfo getUserAlgorithmInfoByUserId(String userId);

}
