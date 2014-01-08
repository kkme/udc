package com.koudai.udc.action;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.koudai.udc.utils.TaobaoKey;

public class VerifyTaoBaoTokenAction extends VerifyTokenAction {

	private static final Logger LOGGER = Logger.getLogger(VerifyTaoBaoTokenAction.class);

	private String key;

	private String secret;

	private TaobaoConfiguration taobaoConfiguration;

	public Event getTokenAndParseNick(RequestContext context, String code, HttpServletRequest request, String platform) {
		try {
			JSONObject tokenObject = TaobaoApiUtil.getTokenObject(code, key, secret, taobaoConfiguration.getCodeReturnUrl(), taobaoConfiguration.getTokenUrl());
			final String taobaoUserId = tokenObject.optString("taobao_user_id", null);
			final String taobaoUserNick = tokenObject.optString("taobao_user_nick", null);
			final String decodedNick = URLDecoder.decode(taobaoUserNick, S.UTF_8);
			final int reExpiresIn = tokenObject.optInt("re_expires_in", 0);
			final int expiresIn = tokenObject.optInt("expires_in", 0);
			final String refreshToken = tokenObject.optString("refresh_token", null);
			final String token = tokenObject.optString("access_token", null);
			final String userId = new StringBuffer(TaobaoKey.USER_PREFIX).append(decodedNick).toString();
			LOGGER.info("TaoBao user nick is : " + decodedNick);

			context.getFlowScope().put(TaobaoKey.USER_ID, userId);
			context.getFlowScope().put(TaobaoKey.TAOBAO_USER_ID, taobaoUserId);
			context.getFlowScope().put(TaobaoKey.TOKEN, token);
			context.getFlowScope().put(TaobaoKey.REFRESH_TOKEN, refreshToken);
			context.getFlowScope().put(TaobaoKey.TOKEN_EXPIRE, String.valueOf(expiresIn));
			context.getFlowScope().put(TaobaoKey.REFRESH_TOKEN_EXPIRE, String.valueOf(reExpiresIn));
			setConstantValueToFlowscope(context, platform, userId, decodedNick);
		} catch (Exception e) {
			LOGGER.error("getTokenAndParseNick exception>>" + e.getMessage());
		}
		return success();
	}

	protected void setKeyAndSecretByPlatform(String platform) {
		if (S.BIJIA_PLATFORM.equals(platform)) {
			key = taobaoConfiguration.getBijiaAppKey();
			secret = taobaoConfiguration.getBijiaAppSecret();
		} else {
			key = taobaoConfiguration.getMeituAppKey();
			secret = taobaoConfiguration.getMeituAppSecret();
		}
	}

	protected boolean codeIsNull(HttpServletRequest request, String code) {
		LOGGER.info("code>>>" + code);
		if (S.isInvalidValue(code)) {
			String errorDescription = request.getParameter("error_description");
			if (!S.isInvalidValue(errorDescription)) {
				if (errorDescription.contains(TaobaoKey.ERROR_AUTHORIZE_REJECT)) {
					LOGGER.info("taobao user denies our app");
				} else if (errorDescription.contains(TaobaoKey.ERROR_SUBUSER)) {
					LOGGER.info("taobao subuser can't access our app");
				} else {
					LOGGER.error("get taobao code error when return is null or empty,error:" + request.getParameter("error") + ",description:" + errorDescription);
				}
				return true;
			}
		}
		return false;
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}
}
