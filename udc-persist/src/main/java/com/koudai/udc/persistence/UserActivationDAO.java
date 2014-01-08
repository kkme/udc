package com.koudai.udc.persistence;

import com.koudai.udc.domain.UserActivation;

public interface UserActivationDAO {

	void save(UserActivation userActivation);

	UserActivation getUserActivationByUserId(String userId);

}
