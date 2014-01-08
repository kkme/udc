package com.koudai.udc.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.AbstractController;

import com.koudai.udc.exception.DomainException;
import com.koudai.udc.service.ThirdPartyUserInfoService;
import com.koudai.udc.service.impl.SinaConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.SinaKey;

public abstract class MobileSinaWeiboController extends AbstractController {

	private ThirdPartyUserInfoService sinaWeiboUserInfoService;

	protected SinaConfiguration sinaConfiguration;

	private String updateBasicInfoUrl;

	private String userInfoUrl;

	private static final Logger LOGGER = Logger.getLogger(MobileSinaWeiboController.class);

	protected void uploadUserSinaInfo(final String token, final String expires, final String uid, final String nick) throws DomainException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(SinaKey.USER_ID, new StringBuffer(SinaKey.USER_PREFIX).append(uid).toString());
		parameters.put(SinaKey.TOKEN, token);
		parameters.put(SinaKey.TOKEN_EXPIRE, expires);
		parameters.put(SinaKey.USER_NICK, nick);
		parameters.put(S.PLATFORM, S.MOBILE_PLATFORM);
		S.verifyParametersNotNull(parameters);
		sinaWeiboUserInfoService.uploadUserInfo(parameters, sinaConfiguration.getUploadUrl());

	}

	protected void updateHeadUrlIfNecessary(String uid, String thirdHeadUrl) {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(thirdHeadUrl) || thirdHeadUrl.contains(SinaKey.DEFAULT_HEAD_URL)) {
				return;
			}
			final String userId = new StringBuffer(SinaKey.USER_PREFIX).append(uid).toString();
			sinaWeiboUserInfoService.updateBasicInfo(userId, thirdHeadUrl, userInfoUrl, updateBasicInfoUrl);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("update sina head url cost>>>" + costTime);
		} catch (Exception e) {
			LOGGER.info("update sina head url error:" + e.getMessage());
		}
	}

	public void setSinaWeiboUserInfoService(ThirdPartyUserInfoService sinaWeiboUserInfoService) {
		this.sinaWeiboUserInfoService = sinaWeiboUserInfoService;
	}

	public void setSinaConfiguration(SinaConfiguration sinaConfiguration) {
		this.sinaConfiguration = sinaConfiguration;
	}

	public void setUpdateBasicInfoUrl(String updateBasicInfoUrl) {
		this.updateBasicInfoUrl = updateBasicInfoUrl;
	}

	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}

}
