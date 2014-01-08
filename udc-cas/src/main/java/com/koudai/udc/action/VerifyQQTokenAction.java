package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.exception.ThirdPartyErrorButValidReturnException;
import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.service.impl.QQConfiguration;
import com.koudai.udc.utils.HttpUtil;
import com.koudai.udc.utils.JsonParseUtil;
import com.koudai.udc.utils.QQKey;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UrlUtil;

public class VerifyQQTokenAction extends VerifyTokenAction {

	private static final Logger LOGGER = Logger.getLogger(VerifyQQTokenAction.class);

	private QQConfiguration qqConfiguration;

	private String key;

	private String secret;

	protected boolean codeIsNull(HttpServletRequest request, String code) {
		LOGGER.info("code>>>" + code);
		final String errorMsg = request.getParameter("msg");
		if (S.isInvalidValue(code) || !S.isInvalidValue(errorMsg)) {
			LOGGER.error("get qq code error when return with null or empty ,msg:" + errorMsg);
			return true;
		}
		return false;
	}

	public Event getTokenAndParseNick(RequestContext context, String code, HttpServletRequest request, String platform) {
		final String state = request.getParameter("state");
		Map<String, String> props = new HashMap<String, String>();
		props.put("grant_type", "authorization_code");
		props.put("code", code);
		props.put("state", state);
		props.put("client_id", key);
		props.put("client_secret", secret);
		props.put("redirect_uri", qqConfiguration.getCodeReturnUrl());
		String response = null;
		try {
			response = HttpUtil.post(qqConfiguration.getTokenUrl(), props);
			if (response.contains("error")) {
				String jsonString = response.replace("callback( ", "").replace(" );", "");
				JSONObject jsonObject = JSONObject.fromObject(jsonString);
				final String errorCode = jsonObject.optString(QQKey.MOBILE_ERROR, null);
				final String errorDes = jsonObject.optString(QQKey.MOBILE_ERROR_DES, null);
				if (!S.isInvalidValue(errorCode)) {
					throw new ThirdPartyErrorReturnException("get qq access token error return with code < " + errorCode + " > and description < " + errorDes + " >");
				}
			} else {
				getValuesAndSetToFlowScope(context, platform, response);
			}
		} catch (ThirdPartyErrorButValidReturnException e) {
			LOGGER.info(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return success();
	}

	private void getValuesAndSetToFlowScope(RequestContext context, String platform, String response) throws ThirdPartyErrorReturnException, ThirdPartyErrorButValidReturnException {
		String token = null;
		String nick = null;
		String expiresIn = null;
		String openId = null;
		String[] values = response.split("=|&");
		if (values == null || values.length != 4) {
			throw new ThirdPartyErrorReturnException("get qq access token error,response is :" + response);
		}
		token = values[1];
		expiresIn = values[3];
		openId = getOpenId(token);
		nick = getNick(token, openId);
		LOGGER.info("qq user nick is : " + nick);
		setValueToFlowScope(context, nick, token, expiresIn, platform, openId);
	}

	protected void setKeyAndSecretByPlatform(String platform) {
		if (S.BIJIA_PLATFORM.equals(platform)) {
			key = qqConfiguration.getBijiaAppKey();
			secret = qqConfiguration.getBijiaAppSecret();
		} else {
			key = qqConfiguration.getAppKey();
			secret = qqConfiguration.getAppSecret();
		}
	}

	private String getOpenId(String token) throws ThirdPartyErrorReturnException {
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put(QQKey.MOBILE_ACCESS_TOKEN, token);
		StringBuffer requestUrl = new StringBuffer(qqConfiguration.getServerUrl());
		requestUrl.append("?");
		requestUrl.append(UrlUtil.getSortedContent(requestParameters));
		String response = HttpUtil.get(requestUrl.toString(), null);
		String jsonString = response.replace("callback( ", "").replace(" );", "");
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		if (jsonObject == null) {
			throw new ThirdPartyErrorReturnException("get qq openid error when return json is null");
		}
		final String openId = jsonObject.optString(QQKey.API_OPENID, null);
		if (S.isInvalidValue(openId)) {
			final String errorCode = jsonObject.optString(QQKey.MOBILE_ERROR, null);
			final String errorDes = jsonObject.optString(QQKey.MOBILE_ERROR_DES, null);
			if (!S.isInvalidValue(errorCode)) {
				throw new ThirdPartyErrorReturnException("get qq openid error return with code < " + errorCode + " > and description < " + errorDes + " >");
			}
			throw new ThirdPartyErrorReturnException("get qq openid error when openid is invalid,jsonObject is :" + jsonObject.toString());
		}
		return openId;
	}

	private void setValueToFlowScope(RequestContext context, String nick, String token, String expiresIn, String platform, String openId) {
		final String userId = new StringBuffer(QQKey.USER_PREFIX).append(openId).toString();
		context.getFlowScope().put(QQKey.USER_ID, userId);
		context.getFlowScope().put(QQKey.TOKEN, token);
		context.getFlowScope().put(QQKey.TOKEN_EXPIRE, expiresIn);
		context.getFlowScope().put(QQKey.USER_NICK, nick);
		setConstantValueToFlowscope(context, platform, userId, nick);
	}

	private String getNick(String token, String openId) throws ThirdPartyErrorReturnException, ThirdPartyErrorButValidReturnException {
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("access_token", token);
		requestParameters.put("oauth_consumer_key", qqConfiguration.getAppKey());
		requestParameters.put("openid", openId);
		requestParameters.put("format", "json");
		StringBuffer requestUrl = new StringBuffer(qqConfiguration.getUserInfoUrl());
		requestUrl.append("?");
		requestUrl.append(UrlUtil.getSortedContent(requestParameters));

		String nick = null;
		for (int i = 1; i <= S.TRY_TIMES; i++) {
			JSONObject jsonObject = JsonParseUtil.parseJsonToObjectByGet(requestUrl.toString(), null);
			if (jsonObject == null) {
				throw new ThirdPartyErrorReturnException("get qq nick error when return json is null");
			}
			nick = jsonObject.optString(QQKey.API_NICKNAME, null);
			if (S.isInvalidValue(nick)) {
				final String returnCode = jsonObject.optString(QQKey.API_RETURN_CODE, null);
				if (S.SUCCESS_CODE_STR.equals(returnCode)) {
					LOGGER.info("return nick empty ,set to default nick ,jsonObject is :" + jsonObject.toString());
					return QQKey.DEFAULT_NICK;
				}
				if (S.SERVER_BUSY_CODE.equals(returnCode) && i != S.TRY_TIMES) {
					continue;
				} else {
					final String errorDesc = jsonObject.optString(QQKey.API_ERROR_MSG, null);
					throw new ThirdPartyErrorReturnException("get qq nick error return with code < " + returnCode + " > and description < " + errorDesc + " >");
				}
			}
		}
		return nick;
	}

	public void setQqConfiguration(QQConfiguration qqConfiguration) {
		this.qqConfiguration = qqConfiguration;
	}

}
