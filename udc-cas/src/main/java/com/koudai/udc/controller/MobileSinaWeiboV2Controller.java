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
import com.koudai.udc.utils.JsonParseUtil;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.SinaKey;
import com.koudai.udc.utils.UrlUtil;

public class MobileSinaWeiboV2Controller extends MobileSinaWeiboController {

	private static final Logger LOGGER = Logger.getLogger(MobileSinaWeiboV2Controller.class);

	private String successUrl;

	private String errorUrl;

	private String thirdHeadUrl;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		try {
			long beginTime = System.currentTimeMillis();
			S.logForMap(SinaKey.RETURN_TITLE, request.getParameterMap());
			final String oauthToken = request.getParameter(SinaKey.MOBILE_V1_OAUTH_TOKEN);
			if (!S.isInvalidValue(oauthToken)) {
				throw new ThirdPartyErrorButValidReturnException("sina oauth1.0 error return");
			}
			final String errorCode = request.getParameter(SinaKey.MOBILE_ERROR_CODE);
			if (!S.isInvalidValue(errorCode) && SinaKey.ERROR_CODE_ACCESS_DENIED.equals(errorCode)) {
				throw new ThirdPartyErrorButValidReturnException("sina user denies our app");
			}
			final String token = request.getParameter(SinaKey.MOBILE_ACCESS_TOKEN);
			final String expires = request.getParameter(SinaKey.MOBILE_EXPIRES_IN);
			if (S.isInvalidValue(token)) {
				throw new ThirdPartyErrorReturnException("get sina access token error return with null or empty");
			}
			if (S.isInvalidValue(expires)) {
				throw new ThirdPartyErrorReturnException("get sina expires error return with null or empty");
			}
			final String uid = getUid(token);
			final String nick = getNickAndHeadUrl(token, uid);
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(SinaKey.MOBILE_ACCESS_TOKEN, token);
			parameters.put(SinaKey.MOBILE_EXPIRE_TIME, expires);
			parameters.put(SinaKey.MOBILE_USER_ID, uid);
			parameters.put(SinaKey.MOBILE_USER_NICK, nick);
			parameters.put(S.KOUDAI_ID, new StringBuffer(SinaKey.USER_PREFIX).append(uid).toString());
			successUrl = UrlUtil.initResultUrl(successUrl, parameters, nick);
			uploadUserSinaInfo(token, expires, uid, nick);
			updateHeadUrlIfNecessary(uid, thirdHeadUrl);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("mobile sina_weibo_v2 cost>>>" + costTime);
			return new ModelAndView(new RedirectView(successUrl));
		} catch (ThirdPartyErrorButValidReturnException e) {
			LOGGER.info(e);
			return new ModelAndView(new RedirectView(errorUrl));
		} catch (Exception e) {
			LOGGER.error(e);
			return new ModelAndView(new RedirectView(errorUrl));
		}
	}

	private String getUid(String token) throws ThirdPartyErrorReturnException {
		StringBuffer apiUrl = new StringBuffer(sinaConfiguration.getUidUrl());
		apiUrl.append("?").append(SinaKey.MOBILE_ACCESS_TOKEN).append("=").append(token);
		JSONObject jsonObject = JsonParseUtil.parseJsonToObjectByGet(apiUrl.toString(), S.JSON_CONTENT_TYPE);
		if (jsonObject == null) {
			throw new ThirdPartyErrorReturnException("get sina uid error when return json is null");
		}
		String uid = jsonObject.optString(SinaKey.API_UID, null);
		if (S.isInvalidValue(uid)) {
			throw new ThirdPartyErrorReturnException("get sina uid error when uid is null,jsonObject is :" + jsonObject.toString());
		}
		return uid;
	}

	private String getNickAndHeadUrl(String token, String uid) throws ThirdPartyErrorReturnException {
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
		final String nick = jsonObject.optString(SinaKey.API_SCREEN_NAME, null);
		thirdHeadUrl = jsonObject.optString(SinaKey.HEAD_URL_KEY, null);
		if (S.isInvalidValue(nick)) {
			throw new ThirdPartyErrorReturnException("get sina nick error when nick is null,jsonObject is :" + jsonObject.toString());
		}
		return nick;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

}
