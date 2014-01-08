package com.koudai.udc.service;

import java.util.Map;

import net.sf.json.JSONObject;

public interface ThirdPartyUserInfoService {

	Map<String, String> getUserInfo(String code);

	JSONObject uploadUserInfo(Map<String, String> infos, String uploadUrl);

	void updateBasicInfo(String userId, String thirdHeadUrl, String userInfoUrl, String updateBasicInfoUrl);

}
