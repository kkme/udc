package com.koudai.udc.action;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserActivation;
import com.koudai.udc.domain.UserTaobaoInfo;
import com.koudai.udc.domain.factory.UserActivationFactory;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserActivationDAO;
import com.koudai.udc.persistence.UserTaobaoInfoDAO;
import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.koudai.udc.utils.UserActivationKey;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;
import com.taobao.api.ApiException;

public class IsUserYellowDiamondAction extends ActionSupport {

	private static final long serialVersionUID = 9125487429399211472L;

	private static final Logger LOGGER = Logger.getLogger(IsUserYellowDiamondAction.class);

	private String userYellowDiamondIn;

	private UserActivationDAO userActivationDAOW;

	private UserTaobaoInfoDAO userTaobaoInfoDAOW;

	private UserActivationFactory userActivationFactory;

	private TaobaoConfiguration taobaoConfiguration;

	private JSONObject result;

	@Override
	public String execute() throws Exception {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(userYellowDiamondIn)) {
				throw new IncorrectInputParameterException("is_user_yellow_diamond_in is null or empty");
			}
			JSONObject content = new JSONObject(userYellowDiamondIn);
			LOGGER.info("IsUserYellowDiamond request is : " + userYellowDiamondIn);
			JSONObject inObject = content.getJSONObject(UserActivationKey.CONTENT_KEY);
			final String userIDMain = inObject.optString(UserActivationKey.USERID_MAIN, null);
			final String userIDSub = inObject.optString(UserActivationKey.USERID_SUB, null);
			final String platform = inObject.optString(UserActivationKey.PLATFORM, LoginPlatform.mobile.toString());
			if (S.isInvalidValue(userIDMain) || !S.isRealUser(userIDMain)) {
				throw new IncorrectInputParameterException("User main id is invalid");
			}
			if (S.isInvalidValue(userIDSub) || !S.isTaobaoUser(userIDSub)) {
				throw new IncorrectInputParameterException("userIDSub is invalid");
			}
			getBuyerCreditAndCheckLevel(userIDMain, userIDSub, platform);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("isUserYellowDiamond cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	private void getBuyerCreditAndCheckLevel(final String userIDMain, final String userIDSub, final String platform) throws IncorrectInputParameterException, IOException, JSONException, ApiException {
		int isYellowDiamond = 1;
		String appKey = null;
		String appSecret = null;
		if (LoginPlatform.mobile.toString().equals(platform)) {
			appKey = taobaoConfiguration.getTaobaoMobileAppKey();
			appSecret = taobaoConfiguration.getTaobaoMobileAppSecret();
		} else {
			appKey = taobaoConfiguration.getTaobaoMeituAppKey();
			appSecret = taobaoConfiguration.getTaobaoMeituAppSecret();
		}
		Long buyerCreditLevel = TaobaoApiUtil.getBuyerCreditLevel(taobaoConfiguration.getTaobaoApiUrl(), appKey, appSecret, userIDSub, getBuyerToken(appKey, appSecret, platform, userIDSub));
		if (buyerCreditLevel >= S.TAOBAO_YELLOW_DIAMONDS_LEVEL) {
			UserActivation userActivation = userActivationDAOW.getUserActivationByUserId(userIDMain);
			if (userActivation == null) {
				userActivationDAOW.save(userActivationFactory.newInstance(userIDMain, true));
			} else {
				userActivation.setYellowDiamond(true);
			}
			isYellowDiamond = 0;
		}
		result = getSuccessfulResult(userIDMain, userIDSub, isYellowDiamond);
	}

	private String getBuyerToken(String appKey, String appSecret, String platform, final String userIDSub) throws IncorrectInputParameterException, IOException, JSONException {
		UserTaobaoInfo userTaobaoInfo = userTaobaoInfoDAOW.getUserTaobaoInfoByUserIdAndPlatform(userIDSub, LoginPlatform.valueOf(platform));
		if (userTaobaoInfo == null) {
			throw new IncorrectInputParameterException("Taobao buyer < " + userIDSub + " > must be logged in");
		}
		if (userTaobaoInfo.getToken() == null) {
			throw new IncorrectInputParameterException("Taobao buyer < " + userIDSub + " > must be logged in");
		}
		final Date currentDate = new Date();
		if (isTokenExpire(currentDate, userTaobaoInfo) && isRefreshTokenExpire(currentDate, userTaobaoInfo)) {
			throw new IncorrectInputParameterException("Token and refreshToken are all expired with user id < " + userIDSub + " >");
		}
		if (isTokenExpire(currentDate, userTaobaoInfo)) {
			final String tokenRefresh = TaobaoApiUtil.refreshToken(taobaoConfiguration.getTaobaoRefreshTokenUrl(), appKey, appSecret, userTaobaoInfo.getRefreshToken());
			userTaobaoInfo.setTokenCreateTime(currentDate);
			userTaobaoInfo.setToken(tokenRefresh);
		}
		return userTaobaoInfo.getToken();
	}

	private boolean isTokenExpire(Date currentDate, UserTaobaoInfo userTaobaoInfo) {
		return DateUtil.isAfter(currentDate, userTaobaoInfo.getExpire(), userTaobaoInfo.getTokenCreateTime());
	}

	private boolean isRefreshTokenExpire(Date currentDate, UserTaobaoInfo userTaobaoInfo) {
		return DateUtil.isAfter(currentDate, userTaobaoInfo.getRefreshExpire(), userTaobaoInfo.getRefreshTokenCreateTime());
	}

	public JSONObject getSuccessfulResult(String userIDMain, String suerIDSub, int isYellowDiamond) {
		try {
			JSONObject result = new JSONObject();
			JSONObject content = new JSONObject();
			content.put("status_code", S.SUCCESS_CODE);
			content.put("status_reason", S.EMPTY_STR);
			result.put("status", content);
			JSONObject userYellowDiamondInfo = new JSONObject();
			userYellowDiamondInfo.put(UserActivationKey.USERID_MAIN, userIDMain);
			userYellowDiamondInfo.put(UserActivationKey.USERID_SUB, suerIDSub);
			userYellowDiamondInfo.put(UserActivationKey.IS_YELLOW_DIAMOND, String.valueOf(isYellowDiamond));
			result.put("useryellowdiamondinfo", userYellowDiamondInfo);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	private JSONObject getFailedResult(String reason) {
		try {
			JSONObject result = new JSONObject();
			JSONObject content = new JSONObject();
			content.put("status_code", S.ERROR_CODE);
			content.put("status_reason", reason);
			result.put("status", content);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setIs_user_yellow_diamond_in(String is_user_yellow_diamond_in) {
		this.userYellowDiamondIn = is_user_yellow_diamond_in;
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

	public void setUserActivationDAOW(UserActivationDAO userActivationDAOW) {
		this.userActivationDAOW = userActivationDAOW;
	}

	public void setUserTaobaoInfoDAOW(UserTaobaoInfoDAO userTaobaoInfoDAOW) {
		this.userTaobaoInfoDAOW = userTaobaoInfoDAOW;
	}

	public void setUserActivationFactory(UserActivationFactory userActivationFactory) {
		this.userActivationFactory = userActivationFactory;
	}

	public JSONObject getResult() {
		return result;
	}

}
