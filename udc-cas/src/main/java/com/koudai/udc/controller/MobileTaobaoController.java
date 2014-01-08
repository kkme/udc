package com.koudai.udc.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.AbstractController;

import com.koudai.udc.exception.DomainException;
import com.koudai.udc.service.ThirdPartyUserInfoService;
import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.HeadUrlUtil;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.koudai.udc.utils.TaobaoKey;

public abstract class MobileTaobaoController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(MobileTaobaoV2Controller.class);

	private ThirdPartyUserInfoService taobaoUserInfoService;

	private TaobaoConfiguration taobaoConfiguration;

	private String updateBasicInfoUrl;

	private String userInfoUrl;

	protected void uploadUserTaobaoInfo(final String token, final String expiresIn, final String refreshToken, final String refreshTokenExpire, final String nick, final String taobaoUserId) throws DomainException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(TaobaoKey.USER_ID, new StringBuffer(TaobaoKey.USER_PREFIX).append(nick).toString());
		parameters.put(TaobaoKey.TAOBAO_USER_ID, taobaoUserId);
		parameters.put(TaobaoKey.TOKEN, token);
		parameters.put(TaobaoKey.TOKEN_EXPIRE, expiresIn);
		parameters.put(TaobaoKey.REFRESH_TOKEN, refreshToken);
		parameters.put(TaobaoKey.REFRESH_TOKEN_EXPIRE, refreshTokenExpire);
		parameters.put(S.PLATFORM, S.MOBILE_PLATFORM);
		S.verifyParametersNotNull(parameters);
		taobaoUserInfoService.uploadUserInfo(parameters, taobaoConfiguration.getUploadUrl());
	}

	protected void updateHeadUrlIfNecessary(String token, String nick) {
		try {
			long beginTime = System.currentTimeMillis();
			final String userId = new StringBuffer(TaobaoKey.USER_PREFIX).append(nick).toString();
			JSONObject userInfoObject = HeadUrlUtil.getUserInfo(userId, userInfoUrl);
			if (userInfoObject != null) {
				JSONObject resultObject = userInfoObject.getJSONObject(S.RESULT);
				final int uploadStatus = resultObject.optInt(S.HAS_UPLOAD_HEAD_KEY, S.HAS_UPLOAD_HEAD);
				if (uploadStatus != S.HAS_UPLOAD_HEAD) {
					final String headUrlFromUserInfo = resultObject.optString(S.THIRD_HEADURL, null);
					final String thirdHeadUrl = TaobaoApiUtil.getHeadPictureUrl(taobaoConfiguration.getServerUrl(), taobaoConfiguration.getMobileAppKey(), taobaoConfiguration.getMobileAppSecret(), token);
					LOGGER.info("thirdHeadUrl>>" + thirdHeadUrl);
					if (S.isInvalidValue(thirdHeadUrl) || thirdHeadUrl.contains(TaobaoKey.DEFAULT_HEAD_URL) || thirdHeadUrl.equals(headUrlFromUserInfo)) {
						return;
					}
					taobaoUserInfoService.updateBasicInfo(userId, thirdHeadUrl, userInfoUrl, updateBasicInfoUrl);
				}
			}
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("update taobao picture url cost>>>" + costTime);
		} catch (Exception e) {
			LOGGER.info("update taobao head url error:" + e.getMessage());
		}
	}

	public void setTaobaoUserInfoService(ThirdPartyUserInfoService taobaoUserInfoService) {
		this.taobaoUserInfoService = taobaoUserInfoService;
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

	public void setUpdateBasicInfoUrl(String updateBasicInfoUrl) {
		this.updateBasicInfoUrl = updateBasicInfoUrl;
	}

	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}

}
