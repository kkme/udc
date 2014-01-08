package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserBasicInfo;
import com.koudai.udc.domain.UserSinaInfo;
import com.koudai.udc.domain.factory.UserBasicInfoFactory;
import com.koudai.udc.domain.factory.UserSinaInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserBasicInfoDAO;
import com.koudai.udc.persistence.UserSinaInfoDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UserSinaInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadSinaWeiboUserInfoAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 1341395014829512367L;

	private static final Logger LOGGER = Logger.getLogger(UploadSinaWeiboUserInfoAction.class);

	private String userinfoIn;

	private UserBasicInfoDAO userBasicInfoDAOW;

	private UserSinaInfoDAO userSinaInfoDAOW;

	private UserBasicInfoFactory userBasicInfoFactory;

	private UserSinaInfoFactory userSinaInfoFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(userinfoIn)) {
			throw new IncorrectInputParameterException("userinfo_in is null or empty");
		}
		LOGGER.info("Upload sina weibo user info request is : " + userinfoIn);
		JSONObject content = new JSONObject(userinfoIn);
		final String userId = content.optString(UserSinaInfoKey.USER_ID, null);
		final String token = content.optString(UserSinaInfoKey.TOKEN, null);
		final int expire = content.optInt(UserSinaInfoKey.TOKEN_EXPIRE, 0);
		final String nick = content.optString(UserSinaInfoKey.USER_NICK, null);
		final String platform = content.optString(UserSinaInfoKey.PLATFORM, null);
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
		initUserSinaInfo(userId, token, expire, nick, platform);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadSinaWeiboUserInfo cost>>>" + costTime);
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

	private void initUserSinaInfo(String userId, String token, int expire, String nick, String platform) {
		LoginPlatform loginPlatform = LoginPlatform.valueOf(platform);
		UserSinaInfo userSinaInfo = userSinaInfoDAOW.getUserSinaInfoByUserIdAndPlatform(userId, loginPlatform);
		if (userSinaInfo == null) {
			userSinaInfoDAOW.save(userSinaInfoFactory.newInstance(userId, token, expire, nick, loginPlatform));
			return;
		}
		Date currentDate = new Date();
		userSinaInfo.setToken(token);
		userSinaInfo.setExpire(expire);
		if (S.isNeedToUpdateNick(nick)) {
			userSinaInfo.setNick(nick);
		}
		userSinaInfo.setCreateTime(currentDate);
	}

	public void setUserinfo_in(String userinfo_in) {
		this.userinfoIn = userinfo_in;
	}

	public void setUserBasicInfoDAOW(UserBasicInfoDAO userBasicInfoDAOW) {
		this.userBasicInfoDAOW = userBasicInfoDAOW;
	}

	public void setUserSinaInfoDAOW(UserSinaInfoDAO userSinaInfoDAOW) {
		this.userSinaInfoDAOW = userSinaInfoDAOW;
	}

	public void setUserBasicInfoFactory(UserBasicInfoFactory userBasicInfoFactory) {
		this.userBasicInfoFactory = userBasicInfoFactory;
	}

	public void setUserSinaInfoFactory(UserSinaInfoFactory userSinaInfoFactory) {
		this.userSinaInfoFactory = userSinaInfoFactory;
	}

}
