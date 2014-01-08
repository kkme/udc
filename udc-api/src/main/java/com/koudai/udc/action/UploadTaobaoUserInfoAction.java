package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserBasicInfo;
import com.koudai.udc.domain.UserTaobaoInfo;
import com.koudai.udc.domain.factory.UserBasicInfoFactory;
import com.koudai.udc.domain.factory.UserTaobaoInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserBasicInfoDAO;
import com.koudai.udc.persistence.UserTaobaoInfoDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UserTaobaoInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadTaobaoUserInfoAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 1681236282689783640L;

	private static final Logger LOGGER = Logger.getLogger(UploadTaobaoUserInfoAction.class);

	private String userinfoIn;

	private UserBasicInfoDAO userBasicInfoDAOW;

	private UserTaobaoInfoDAO userTaobaoInfoDAOW;

	private UserBasicInfoFactory userBasicInfoFactory;

	private UserTaobaoInfoFactory userTaobaoInfoFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(userinfoIn)) {
			throw new IncorrectInputParameterException("userinfo_in is null or empty");
		}
		LOGGER.info("Upload taobao user info request is : " + userinfoIn);
		JSONObject content = new JSONObject(userinfoIn);
		final String userId = content.optString(UserTaobaoInfoKey.USER_ID, null);
		final String taobaoUserId = content.optString(UserTaobaoInfoKey.TAOBAO_USER_ID, null);
		final String token = content.optString(UserTaobaoInfoKey.TOKEN, null);
		final String expire = content.optString(UserTaobaoInfoKey.TOKEN_EXPIRE, null);
		final String refreshToken = content.optString(UserTaobaoInfoKey.REFRESH_TOKEN, null);
		final String refreshExpire = content.optString(UserTaobaoInfoKey.REFRESH_TOKEN_EXPIRE, null);
		final String platform = content.optString(UserTaobaoInfoKey.PLATFORM, null);
		if (S.isInvalidValue(userId)) {
			throw new IncorrectInputParameterException("User id is null or empty");
		}
		if (S.isInvalidValue(platform)) {
			throw new IncorrectInputParameterException("Platform is null or empty");
		}
		LoginPlatform loginPlatform = LoginPlatform.valueOf(platform);
		if (!LoginPlatform.bijia.equals(loginPlatform)) {
			if (S.isInvalidValue(token)) {
				throw new IncorrectInputParameterException("Token is null or empty");
			}
			if (S.isInvalidValue(refreshToken)) {
				throw new IncorrectInputParameterException("RefreshToken is null or empty");
			}
		}
		initUserBasicInfo(userId);
		initUserTaobaoInfo(userId, taobaoUserId, token, expire, refreshToken, refreshExpire, loginPlatform);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadTaobaoUserInfo cost>>>" + costTime);
	}

	private void initUserBasicInfo(String userId) {
		Date currentDate = new Date();
		UserBasicInfo userBasicInfo = userBasicInfoDAOW.getUserBasicInfoByThirdPartyId(userId);
		if (userBasicInfo != null) {
			userBasicInfo.setLastLoginTime(currentDate);
			return;
		}
		final String nick = userId.split(":")[1];
		userBasicInfoDAOW.save(userBasicInfoFactory.newInstance(null, userId, null, null, nick, S.EMPTY_STR, S.EMPTY_STR, '1', null, null, currentDate, null, null, null, currentDate));
	}

	private void initUserTaobaoInfo(String userId, String taobaoUserId, String token, String expire, String refreshToken, String refreshExpire, LoginPlatform loginPlatform) {
		UserTaobaoInfo userTaobaoInfo = userTaobaoInfoDAOW.getUserTaobaoInfoByUserIdAndPlatform(userId, loginPlatform);
		if (userTaobaoInfo == null) {
			if (LoginPlatform.bijia.equals(loginPlatform)) {
				userTaobaoInfoDAOW.save(userTaobaoInfoFactory.newInstance(userId, loginPlatform));
			} else {
				userTaobaoInfoDAOW.save(userTaobaoInfoFactory.newInstance(userId, taobaoUserId, token, Integer.valueOf(expire), refreshToken, Integer.valueOf(refreshExpire), loginPlatform));
			}
		} else if (!LoginPlatform.bijia.equals(loginPlatform)) {
			Date currentDate = new Date();
			if (userTaobaoInfo.getTaobaoUserId() == null) {
				userTaobaoInfo.setTaobaoUserId(taobaoUserId);
			}
			userTaobaoInfo.setToken(token);
			userTaobaoInfo.setExpire(Integer.valueOf(expire));
			userTaobaoInfo.setTokenCreateTime(currentDate);
			userTaobaoInfo.setRefreshToken(refreshToken);
			userTaobaoInfo.setRefreshExpire(Integer.valueOf(refreshExpire));
			userTaobaoInfo.setRefreshTokenCreateTime(currentDate);
		}
	}

	public void setUserinfo_in(String userinfo_in) {
		this.userinfoIn = userinfo_in;
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

}
