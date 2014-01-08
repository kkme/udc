package com.koudai.udc.persistence;

import java.util.List;

import com.koudai.udc.domain.UserTestInfo;

public interface UserTestInfoDAO {

	void save(UserTestInfo userTestInfo);

	UserTestInfo getUserTestInfoByUserIdAndQuestionId(String userId, String questionId);

	List<UserTestInfo> getUserTestInfoListByUserId(String userId);

}
