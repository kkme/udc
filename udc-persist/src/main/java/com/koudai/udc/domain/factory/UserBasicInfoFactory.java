package com.koudai.udc.domain.factory;

import java.util.Date;

import com.koudai.udc.domain.UserBasicInfo;

public interface UserBasicInfoFactory {

	UserBasicInfo newInstance(String email, String thirdId, String phoneNumber, String koudaiId, String name, String machineId, String password, char gender, Date birthday, String location, Date registTime, String qq, String msn, String introduction, Date lastLoginTime);

}
