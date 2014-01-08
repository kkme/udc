package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.service.impl.SinaConfiguration;
import com.koudai.udc.utils.JsonParseUtil;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.SinaKey;
import com.koudai.udc.utils.UrlUtil;

public class VerifySinaTokenAction extends VerifyTokenAction {

	private static final Logger LOGGER = Logger.getLogger(VerifySinaTokenAction.class);

	private SinaConfiguration sinaConfiguration;

	private String key;

	private String secret;

	public Event getTokenAndParseNick(RequestContext context, String code, HttpServletRequest request, String platform) {
		Map<String, String> props = new HashMap<String, String>();
		props.put("grant_type", "authorization_code");
		props.put("code", code);
		props.put("client_id", key);
		props.put("client_secret", secret);
		props.put("redirect_uri", sinaConfiguration.getCodeReturnUrl());
		String token = null;
		String nick = null;
		String uid = null;
		JSONObject tokenObject = null;
		try {
			tokenObject = JsonParseUtil.parseJsonToObject(sinaConfiguration.getTokenUrl(), props);
			if (!tokenObject.has("error")) {
				token = tokenObject.optString("access_token", null);
				uid = tokenObject.optString("uid", null);
				nick = getNick(uid, token);
				LOGGER.info("sina user nick is : " + nick);
				setValueToFlowScope(context, nick, token, tokenObject, platform);
			} else {
				throw new ThirdPartyErrorReturnException("get sina access token error ,tokenObject is :" + tokenObject.toString());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return success();
	}

	protected void setKeyAndSecretByPlatform(String platform) {
		if (S.BIJIA_PLATFORM.equals(platform)) {
			key = sinaConfiguration.getBijiaAppKey();
			secret = sinaConfiguration.getBijiaAppSecret();
		} else {
			key = sinaConfiguration.getAppKey();
			secret = sinaConfiguration.getAppSecret();
		}
	}

	private void setValueToFlowScope(RequestContext context, String nick, String token, JSONObject tokenObject, String platform) {
		String expiresIn = tokenObject.optString("expires_in", null);
		String uid = tokenObject.optString("uid", null);
		final String userId = new StringBuffer(SinaKey.USER_PREFIX).append(uid).toString();
		context.getFlowScope().put(SinaKey.USER_ID, userId);
		context.getFlowScope().put(SinaKey.TOKEN, token);
		context.getFlowScope().put(SinaKey.TOKEN_EXPIRE, expiresIn);
		context.getFlowScope().put(SinaKey.USER_NICK, nick);
		setConstantValueToFlowscope(context, platform, userId, nick);
	}

	private String getNick(String uid, String token) throws ThirdPartyErrorReturnException {
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put(SinaKey.MOBILE_ACCESS_TOKEN, token);
		requestParameters.put(SinaKey.API_UID, uid);
		StringBuffer apiUrl = new StringBuffer(sinaConfiguration.getNickUrl());
		apiUrl.append("?");
		apiUrl.append(UrlUtil.getSortedContent(requestParameters));
		JSONObject jsonObject = JsonParseUtil.parseJsonToObjectByGet(apiUrl.toString(), S.JSON_CONTENT_TYPE);
		if (jsonObject == null) {
			throw new ThirdPartyErrorReturnException("get sina nick error when return json is null");
		}
		String nick = jsonObject.optString(SinaKey.API_SCREEN_NAME, null);
		if (S.isInvalidValue(nick)) {
			throw new ThirdPartyErrorReturnException("get sina nick error when return nick with null,jsonObject is :" + jsonObject.toString());
		}
		return nick;
	}

	protected boolean codeIsNull(HttpServletRequest request, String code) {
		LOGGER.info("code>>>" + code);
		if (S.isInvalidValue(code)) {
			final String errorCode = request.getParameter(SinaKey.MOBILE_ERROR_CODE);
			String errorDescription = request.getParameter("error_description");
			if (!S.isInvalidValue(errorCode) && SinaKey.ERROR_CODE_ACCESS_DENIED.equals(errorCode)) {
				LOGGER.info("sina user denies our app");
			} else {
				LOGGER.error("get sina code error return with null or empty,error:" + request.getParameter("error") + ",description:" + errorDescription);
			}
			return true;
		}
		return false;
	}

	public void setSinaConfiguration(SinaConfiguration sinaConfiguration) {
		this.sinaConfiguration = sinaConfiguration;
	}
}
