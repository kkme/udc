package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserQQInfo;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserQQInfoDAO;
import com.koudai.udc.service.impl.QQConfiguration;
import com.koudai.udc.utils.HttpUtil;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UrlUtil;
import com.koudai.udc.utils.UserQQInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class IsUserFollowOfficialQZoneAction extends ActionSupport {

	private static final long serialVersionUID = -2387570638253540564L;

	private static final Logger LOGGER = Logger.getLogger(IsUserFollowOfficialQZoneAction.class);

	private UserQQInfoDAO userQQInfoDAOR;

	private QQConfiguration qqConfiguration;

	private String userId;

	private String platform;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(userId) || !S.isQQUser(userId)) {
				throw new IncorrectInputParameterException("User id is invalid");
			}
			if (S.isInvalidValue(platform) || !S.isWebPlatform(platform)) {
				throw new IncorrectInputParameterException("Platform is invalid");
			}
			LOGGER.info("Is user follow official qzone request and user id < " + userId + " > and platform < " + platform + " >");
			checkUserFollowOfficialQZone();
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("isUserFollowOfficialQZone cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getCheckFollowResult(S.CHECK_FOLLOW_QZONE_ERROR_CODE, e.getMessage());
			return ERROR;
		}
	}

	private void checkUserFollowOfficialQZone() throws JSONException, IncorrectInputParameterException {
		JSONObject responseObject = new JSONObject(HttpUtil.get(getRequestUrl(), S.JSON_CONTENT_TYPE));
		int ret = responseObject.optInt("ret");
		if (ret == 0) {
			int followStatus = getFollowStatus(responseObject);
			result = getCheckFollowResult(followStatus, S.EMPTY_STR);
			return;
		}
		final String errorMsg = responseObject.optString("msg");
		throw new IncorrectInputParameterException("error message from qq api < " + errorMsg + " >");
	}

	private String getRequestUrl() throws IncorrectInputParameterException {
		UserQQInfo userQQInfo = userQQInfoDAOR.getUserQQInfoByUserIdAndPlaform(userId, LoginPlatform.valueOf(platform));
		if (userQQInfo == null) {
			throw new IncorrectInputParameterException("User with id < " + userId + " > not login in platform < " + platform + " >");
		}
		final String token = userQQInfo.getToken();
		final String openId = userId.replace(S.QQ_PREFIX, "");
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put(UserQQInfoKey.FANS_ACCESS_TOKEN, token);
		requestParameters.put(UserQQInfoKey.FANS_OPEN_ID, openId);
		if (LoginPlatform.meitu.toString().equals(platform)) {
			requestParameters.put("oauth_consumer_key", qqConfiguration.getMeituAppKey());
			requestParameters.put("page_id", qqConfiguration.getMeituPageId());
		} else {
			requestParameters.put("oauth_consumer_key", qqConfiguration.getBijiaAppKey());
			requestParameters.put("page_id", qqConfiguration.getBijiaPageId());
		}
		return new StringBuffer(qqConfiguration.getCheckPageFansUrl()).append("?").append(UrlUtil.getSortedContent(requestParameters)).toString();
	}

	private int getFollowStatus(JSONObject responseObject) {
		String isFollow = responseObject.optString("isfans");
		if (UserQQInfoKey.FOLLOW_QZONE.equals(isFollow)) {
			return S.USER_FOLLOW_QZONE_CODE;
		}
		return S.USER_NOT_FOLLOW_QZONE_CODE;
	}

	private JSONObject getCheckFollowResult(int code, String reason) {
		try {
			JSONObject result = new JSONObject();
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			result.put("status", statusObject);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setUserQQInfoDAOR(UserQQInfoDAO userQQInfoDAOR) {
		this.userQQInfoDAOR = userQQInfoDAOR;
	}

	public void setQqConfiguration(QQConfiguration qqConfiguration) {
		this.qqConfiguration = qqConfiguration;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public JSONObject getResult() {
		return result;
	}

}
