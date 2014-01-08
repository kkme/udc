package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserBasicInfo;
import com.koudai.udc.domain.UserTaobaoInfo;
import com.koudai.udc.domain.factory.UserBasicInfoFactory;
import com.koudai.udc.domain.factory.UserTaobaoInfoFactory;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserBasicInfoDAO;
import com.koudai.udc.persistence.UserTaobaoInfoDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UserBasicInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class ThridPartyUserLoginAction extends ActionSupport {

	private static final long serialVersionUID = -8623083649853844337L;

	private static final Logger LOGGER = Logger.getLogger(ThridPartyUserLoginAction.class);

	private String loginUserinfoIn;

	private UserBasicInfoDAO userBasicInfoDAOW;

	private UserTaobaoInfoDAO userTaobaoInfoDAOW;

	private UserBasicInfoFactory userBasicInfoFactory;

	private UserTaobaoInfoFactory userTaobaoInfoFactory;

	private JSONObject result;

	@Override
	public String execute() throws Exception {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(loginUserinfoIn)) {
				throw new IncorrectInputParameterException("login_userinfo_in is null or empty");
			}
			LOGGER.info("Third party user login request is : " + loginUserinfoIn);
			JSONObject content = new JSONObject(loginUserinfoIn);
			final String thirdPartyId = content.optString(UserBasicInfoKey.THIRD_PARTY_ID, null);
			final String token = content.optString(UserBasicInfoKey.TOKEN, null);
			final int expire = content.optInt(UserBasicInfoKey.EXPIRE, 0);
			final String refreshToken = content.optString(UserBasicInfoKey.REFRESH_TOKEN, null);
			final int refreshExpire = content.optInt(UserBasicInfoKey.REFRESH_EXPIRE, 0);
			final String platform = content.optString(UserBasicInfoKey.PLATFORM, LoginPlatform.meitu.toString());
			if (S.isInvalidValue(thirdPartyId)) {
				throw new IncorrectInputParameterException("Third party id is null or empty");
			}
			if (S.isInvalidValue(token)) {
				throw new IncorrectInputParameterException("token is null or empty");
			}
			if (S.isInvalidValue(refreshToken)) {
				throw new IncorrectInputParameterException("refreshToken is null or empty");
			}
			initUserBasicInfoAndResult(thirdPartyId);
			initUserTaobaoInfo(thirdPartyId, token, expire, refreshToken, refreshExpire, platform);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("thridPartyUserLogin cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	private void initUserBasicInfoAndResult(String thirdPartyId) {
		UserBasicInfo userBasicInfo = userBasicInfoDAOW.getUserBasicInfoByThirdPartyId(thirdPartyId);
		if (userBasicInfo != null) {
			userBasicInfo.setLastLoginTime(new Date());
			result = getSuccessfulResult(S.USER_HAS_LOGIN_CODE, thirdPartyId, userBasicInfo.getName());
			return;
		}
		Date currentDate = new Date();
		final String name = thirdPartyId.split(":")[1];
		userBasicInfoDAOW.save(userBasicInfoFactory.newInstance(null, thirdPartyId, null, null, name, S.EMPTY_STR, S.EMPTY_STR, '1', null, null, currentDate, null, null, null, currentDate));
		result = getSuccessfulResult(S.USER_FIRST_LOGIN_CODE, thirdPartyId, name);
	}

	private void initUserTaobaoInfo(String thirdPartyId, String token, int expire, String refreshToken, int refreshExpire, String platform) {
		LoginPlatform loginPlatform = LoginPlatform.valueOf(platform);
		UserTaobaoInfo userTaobaoInfo = userTaobaoInfoDAOW.getUserTaobaoInfoByUserIdAndPlatform(thirdPartyId, loginPlatform);
		if (userTaobaoInfo == null) {
			userTaobaoInfoDAOW.save(userTaobaoInfoFactory.newInstance(thirdPartyId, null, token, expire, refreshToken, refreshExpire, loginPlatform));
			return;
		}
		Date currentDate = new Date();
		userTaobaoInfo.setToken(token);
		userTaobaoInfo.setExpire(expire);
		userTaobaoInfo.setTokenCreateTime(currentDate);
		userTaobaoInfo.setRefreshToken(refreshToken);
		userTaobaoInfo.setRefreshExpire(refreshExpire);
		userTaobaoInfo.setRefreshTokenCreateTime(currentDate);
	}

	private JSONObject getSuccessfulResult(int successCode, String thirdPartyId, String name) {
		try {
			JSONObject result = new JSONObject();
			JSONObject statusContent = new JSONObject();
			statusContent.put("status_code", successCode);
			statusContent.put("status_reason", S.EMPTY_STR);
			result.put("status", statusContent);
			JSONObject userContent = new JSONObject();
			userContent.put("thirdPartyID", thirdPartyId);
			if (name != null) {
				userContent.put("name", name);
			}
			result.put("user_info", userContent);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	private JSONObject getFailedResult(String reason) {
		try {
			JSONObject result = new JSONObject();
			JSONObject statusContent = new JSONObject();
			statusContent.put("status_code", S.USER_ERROR_CODE);
			statusContent.put("status_reason", reason);
			result.put("status", statusContent);
			JSONObject userContent = new JSONObject();
			result.put("user_info", userContent);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setLogin_userinfo_in(String login_userinfo_in) {
		this.loginUserinfoIn = login_userinfo_in;
	}

	public void setUserBasicInfoDAOW(UserBasicInfoDAO userBasicInfoDAOW) {
		this.userBasicInfoDAOW = userBasicInfoDAOW;
	}

	public void setUserTaobaoInfoDAOW(UserTaobaoInfoDAO userTaobaoInfoDAOW) {
		this.userTaobaoInfoDAOW = userTaobaoInfoDAOW;
	}

	public void setUserBasicInfoFactory(UserBasicInfoFactory userBasicInfoFactory) {
		this.userBasicInfoFactory = userBasicInfoFactory;
	}

	public void setUserTaobaoInfoFactory(UserTaobaoInfoFactory userTaobaoInfoFactory) {
		this.userTaobaoInfoFactory = userTaobaoInfoFactory;
	}

	public JSONObject getResult() {
		return result;
	}

}
