package com.koudai.udc.utils;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class HeadUrlUtil {

	private static final Logger LOGGER = Logger.getLogger(HeadUrlUtil.class);

	public static JSONObject getUserInfo(String userId, String userInfoUrl) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(S.USER_ID, userId);
		JSONObject userInfoObject = JsonParseUtil.parseJsonToObject(userInfoUrl, parameters);
		LOGGER.info("getUserInfo result >>" + (userInfoObject == null ? "null object" : userInfoObject.toString()));
		return userInfoObject;
	}

	public static void updateUserBasicInfo(String userId, String headUrlFromThirdParty, String updateBasicInfoUrl) {
		JSONObject dataObject = new JSONObject();
		dataObject.put(S.THIRD_HEADURL, headUrlFromThirdParty);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(S.USER_ID, userId);
		jsonObject.put(S.DATA, dataObject.toString());
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("user_basic_info", jsonObject.toString());
		JSONObject updateUserBasicInfoObject = JsonParseUtil.parseJsonToObject(updateBasicInfoUrl, requestParameters);
		LOGGER.info("updateUserBasicInfo result >>" + (updateUserBasicInfoObject == null ? "null object" : updateUserBasicInfoObject.toString()));
	}

}
