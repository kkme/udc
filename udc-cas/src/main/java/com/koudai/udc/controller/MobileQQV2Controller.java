package com.koudai.udc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.exception.ThirdPartyErrorButValidReturnException;
import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.utils.HttpUtil;
import com.koudai.udc.utils.JsonParseUtil;
import com.koudai.udc.utils.QQKey;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UrlUtil;

public class MobileQQV2Controller extends MobileQQController {

	private static final Logger LOGGER = Logger.getLogger(MobileQQV2Controller.class);

	private String successUrl;

	private String errorUrl;

	private String thirdHeadurl;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		try {
			long beginTime = System.currentTimeMillis();
			S.logForMap(QQKey.RETURN_TITLE, request.getParameterMap());
			final String errorCode = request.getParameter(QQKey.MOBILE_ERROR);
			final String errorDes = request.getParameter(QQKey.MOBILE_ERROR_DES);
			if (!S.isInvalidValue(errorCode)) {
				throw new ThirdPartyErrorReturnException("get qq access token error return with code < " + errorCode + " > and description < " + errorDes + " >");
			}
			final String token = request.getParameter(QQKey.MOBILE_ACCESS_TOKEN);
			final String expiresIn = request.getParameter(QQKey.MOBILE_EXPIRE_IN);
			if (S.isInvalidValue(token)) {
				throw new ThirdPartyErrorReturnException("get qq access token error return with null or empty");
			}
			if (S.isInvalidValue(expiresIn)) {
				throw new ThirdPartyErrorReturnException("get qq expiresin error return with null or empty");
			}
			final String openId = getOpenId(token);
			final String nick = getNickAndHeadUrl(token, openId);
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(QQKey.MOBILE_ACCESS_TOKEN, token);
			parameters.put(QQKey.MOBILE_EXPIRE_DATE, expiresIn);
			parameters.put(QQKey.MOBILE_OPEN_ID, openId);
			parameters.put(QQKey.MOBILE_USER_NICK, nick);
			parameters.put(QQKey.MOBILE_APP_ID, qqConfiguration.getAppKey());
			parameters.put(S.KOUDAI_ID, new StringBuffer(QQKey.USER_PREFIX).append(openId).toString());
			successUrl = UrlUtil.initResultUrl(successUrl, parameters, nick);
			uploadUserQQInfo(token, expiresIn, nick, openId);
			updateHeadUrlIfNecessary(openId, thirdHeadurl);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("mobile tencent_qq_v2 cost>>>" + costTime);
			return new ModelAndView(new RedirectView(successUrl));
		} catch (ThirdPartyErrorButValidReturnException e) {
			LOGGER.info(e);
			return new ModelAndView(new RedirectView(errorUrl));
		} catch (Exception e) {
			LOGGER.error(e);
			return new ModelAndView(new RedirectView(errorUrl));
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

	private String getNickAndHeadUrl(String token, String openId) throws ThirdPartyErrorButValidReturnException, ThirdPartyErrorReturnException {
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("access_token", token);
		requestParameters.put("oauth_consumer_key", qqConfiguration.getAppKey());
		requestParameters.put("openid", openId);
		requestParameters.put("format", "json");
		StringBuffer requestUrl = new StringBuffer(qqConfiguration.getUserInfoUrl());
		requestUrl.append("?");
		requestUrl.append(UrlUtil.getSortedContent(requestParameters));

		String nick = "";
		for (int i = 1; i <= S.TRY_TIMES; i++) {
			JSONObject jsonObject = JsonParseUtil.parseJsonToObjectByGet(requestUrl.toString(), null);
			if (jsonObject == null) {
				throw new ThirdPartyErrorReturnException("get qq nick error when return json is null");
			}
			nick = jsonObject.optString(QQKey.API_NICKNAME, null);
			thirdHeadurl = jsonObject.optString(QQKey.HEAD_PICTURE_KEY, null);
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
		LOGGER.info("qq nick is :" + nick);
		return nick;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

}
