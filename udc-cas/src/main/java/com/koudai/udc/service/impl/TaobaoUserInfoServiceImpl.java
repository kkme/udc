package com.koudai.udc.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.koudai.udc.service.ThirdPartyUserInfoService;
import com.koudai.udc.utils.HeadUrlUtil;
import com.koudai.udc.utils.JsonParseUtil;

public class TaobaoUserInfoServiceImpl implements ThirdPartyUserInfoService {

	private static final Logger LOGGER = Logger.getLogger(TaobaoUserInfoServiceImpl.class);

	@Override
	public Map<String, String> getUserInfo(String code) {
		return null;
	}

	@Override
	public JSONObject uploadUserInfo(Map<String, String> infos, String uploadUrl) {
		JSONObject userObject = new JSONObject();
		Iterator<Entry<String, String>> it = infos.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> next = it.next();
			String key = next.getKey();
			String value = next.getValue();
			userObject.put(key, value);
		}
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("userinfo_in", userObject.toString());
		JSONObject resultObject = JsonParseUtil.parseJsonToObject(uploadUrl, requestParameters);
		LOGGER.info("Upload taobao user info result is: " + resultObject);
		return resultObject;
	}

	@Override
	public void updateBasicInfo(String userId, String headUrlFromThirdParty, String userInfoUrl, String updateBasicInfoUrl) {
		HeadUrlUtil.updateUserBasicInfo(userId, headUrlFromThirdParty, updateBasicInfoUrl);
	}

}
