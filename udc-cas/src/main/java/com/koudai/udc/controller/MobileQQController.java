package com.koudai.udc.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.AbstractController;

import com.koudai.udc.exception.DomainException;
import com.koudai.udc.service.ThirdPartyUserInfoService;
import com.koudai.udc.service.impl.QQConfiguration;
import com.koudai.udc.utils.QQKey;
import com.koudai.udc.utils.S;

public abstract class MobileQQController extends AbstractController {

	private ThirdPartyUserInfoService qqUserInfoService;

	protected QQConfiguration qqConfiguration;

	private String updateBasicInfoUrl;

	private String userInfoUrl;

	private static final Logger LOGGER = Logger.getLogger(MobileQQController.class);

	protected void uploadUserQQInfo(final String token, final String expiresIn, final String nick, final String openId) throws DomainException {
		final String userId = new StringBuffer(QQKey.USER_PREFIX).append(openId).toString();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(QQKey.USER_ID, userId);
		parameters.put(QQKey.TOKEN, token);
		parameters.put(QQKey.TOKEN_EXPIRE, expiresIn);
		parameters.put(QQKey.USER_NICK, nick);
		parameters.put(S.PLATFORM, S.MOBILE_PLATFORM);
		S.verifyParametersNotNull(parameters);
		qqUserInfoService.uploadUserInfo(parameters, qqConfiguration.getUploadUrl());
	}

	protected void updateHeadUrlIfNecessary(String openId, String thirdHeadUrl) {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(thirdHeadUrl)) {
				return;
			}
			final String userId = new StringBuffer(QQKey.USER_PREFIX).append(openId).toString();
			qqUserInfoService.updateBasicInfo(userId, thirdHeadUrl, userInfoUrl, updateBasicInfoUrl);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("update qq head url cost>>>" + costTime);
		} catch (Exception e) {
			LOGGER.info("update qq head url error:" + e.getMessage());
		}
	}

	public void setQqUserInfoService(ThirdPartyUserInfoService qqUserInfoService) {
		this.qqUserInfoService = qqUserInfoService;
	}

	public void setQqConfiguration(QQConfiguration qqConfiguration) {
		this.qqConfiguration = qqConfiguration;
	}

	public void setUpdateBasicInfoUrl(String updateBasicInfoUrl) {
		this.updateBasicInfoUrl = updateBasicInfoUrl;
	}

	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}
}
