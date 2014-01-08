package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserBasicInfo;
import com.koudai.udc.domain.UserQQInfo;
import com.koudai.udc.domain.factory.UserBasicInfoFactory;
import com.koudai.udc.domain.factory.UserQQInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserBasicInfoDAO;
import com.koudai.udc.persistence.UserQQInfoDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UserQQInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadQQUserInfoAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -3354307767490929808L;

	private static final Logger LOGGER = Logger.getLogger(UploadQQUserInfoAction.class);

	private String userinfoIn;

	private UserBasicInfoDAO userBasicInfoDAOW;

	private UserQQInfoDAO userQQInfoDAOW;

	private UserBasicInfoFactory userBasicInfoFactory;

	private UserQQInfoFactory userQQInfoFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(userinfoIn)) {
			throw new IncorrectInputParameterException("userinfo_in is null or empty");
		}
		LOGGER.info("Upload qq user info request is : " + userinfoIn);
		JSONObject content = new JSONObject(userinfoIn);
		final String userId = content.optString(UserQQInfoKey.USER_ID, null);
		final String token = content.optString(UserQQInfoKey.TOKEN, null);
		final int expire = content.optInt(UserQQInfoKey.TOKEN_EXPIRE, 0);
		final String nick = content.optString(UserQQInfoKey.USER_NICK, null);
		final String platform = content.optString(UserQQInfoKey.PLATFORM, null);
		if (S.isInvalidValue(userId)) {
			throw new IncorrectInputParameterException("User id is null or empty");
		}
		if (S.isInvalidValue(token)) {
			throw new IncorrectInputParameterException("Token is null or empty");
		}
		if (S.isInvalidValue(nick)) {
			throw new IncorrectInputParameterException("Nick is null or empty");
		}
		if (S.isInvalidValue(platform)) {
			throw new IncorrectInputParameterException("Platform is null or empty");
		}
		initUserBasicInfo(userId, nick);
		initUserQQInfo(userId, token, expire, nick, platform);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadQQUserInfo cost>>>" + costTime);
	}

	private void initUserBasicInfo(String userId, String nick) {
		Date currentDate = new Date();
		UserBasicInfo userBasicInfo = userBasicInfoDAOW.getUserBasicInfoByThirdPartyId(userId);
		if (userBasicInfo != null) {
			if (S.isNeedToUpdateNick(nick)) {
				userBasicInfo.setName(nick);
			}
			userBasicInfo.setLastLoginTime(currentDate);
			return;
		}
		userBasicInfoDAOW.save(userBasicInfoFactory.newInstance(null, userId, null, null, nick, S.EMPTY_STR, S.EMPTY_STR, '1', null, null, currentDate, null, null, null, currentDate));
	}

	private void initUserQQInfo(String userId, String token, int expire, String nick, String platform) {
		LoginPlatform loginPlatform = LoginPlatform.valueOf(platform);
		UserQQInfo userQQInfo = userQQInfoDAOW.getUserQQInfoByUserIdAndPlaform(userId, loginPlatform);
		if (userQQInfo == null) {
			userQQInfoDAOW.save(userQQInfoFactory.newInstance(userId, token, expire, nick, loginPlatform));
			return;
		}
		Date currentDate = new Date();
		userQQInfo.setToken(token);
		userQQInfo.setExpire(expire);
		if (S.isNeedToUpdateNick(nick)) {
			userQQInfo.setNick(nick);
		}
		userQQInfo.setCreateTime(currentDate);
	}

	public void setUserinfo_in(String userinfo_in) {
		this.userinfoIn = userinfo_in;
	}

	public void setUserBasicInfoDAOW(UserBasicInfoDAO userBasicInfoDAOW) {
		this.userBasicInfoDAOW = userBasicInfoDAOW;
	}

	public void setUserQQInfoDAOW(UserQQInfoDAO userQQInfoDAOW) {
		this.userQQInfoDAOW = userQQInfoDAOW;
	}

	public void setUserBasicInfoFactory(UserBasicInfoFactory userBasicInfoFactory) {
		this.userBasicInfoFactory = userBasicInfoFactory;
	}

	public void setUserQQInfoFactory(UserQQInfoFactory userQQInfoFactory) {
		this.userQQInfoFactory = userQQInfoFactory;
	}

}
