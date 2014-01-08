package com.koudai.udc.domain.factory;

import java.util.Date;

import com.koudai.udc.domain.UserBasicInfo;

public class SimpleUserBasicInfoFactory implements UserBasicInfoFactory {

	@Override
	public UserBasicInfo newInstance(String email, String thirdId, String phoneNumber, String koudaiId, String name, String machineId, String password, char gender, Date birthday, String location, Date registTime, String qq, String msn, String introduction, Date lastLoginTime) {
		return new UserBasicInfo(email, thirdId, phoneNumber, koudaiId, name, machineId, password, gender, birthday, location, registTime, qq, msn, introduction, lastLoginTime);
	}

}
