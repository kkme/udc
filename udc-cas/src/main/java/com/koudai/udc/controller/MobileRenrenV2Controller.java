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
import com.koudai.udc.utils.MD5Util;
import com.koudai.udc.utils.RenrenKey;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UrlUtil;

public class MobileRenrenV2Controller extends MobileRenrenController {

	private static final Logger LOGGER = Logger.getLogger(MobileRenrenV2Controller.class);

	private String successUrl;

	private String errorUrl;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		try {
			long beginTime = System.currentTimeMillis();
			S.logForMap(RenrenKey.RETURN_TITLE, request.getParameterMap());
			final String token = request.getParameter(RenrenKey.MOBILE_ACCESS_TOKEN);
			final String expiresIn = request.getParameter(RenrenKey.MOBILE_EXPIRE_IN);
			final String nick = getUserNickByToken(token);
			if (S.isInvalidValue(token)) {
				throw new ThirdPartyErrorReturnException("get renren token error return with null or empty");
			}
			if (S.isInvalidValue(expiresIn)) {
				throw new ThirdPartyErrorReturnException("get renren expiresin error return with null or empty");
			}
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(RenrenKey.MOBILE_ACCESS_TOKEN, token);
			parameters.put(RenrenKey.MOBILE_EXPIRE_DATE, S.getExpireDateMillisecond(expiresIn));
			successUrl = UrlUtil.initResultUrl(successUrl, parameters, nick);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("mobile renren_v2 cost>>>" + costTime);
			return new ModelAndView(new RedirectView(successUrl));
		} catch (ThirdPartyErrorButValidReturnException e) {
			LOGGER.info(e);
			return new ModelAndView(new RedirectView(errorUrl));
		} catch (Exception e) {
			LOGGER.error(e);
			return new ModelAndView(new RedirectView(errorUrl));
		}
	}

	private String getUserNickByToken(String token) throws ThirdPartyErrorReturnException, ThirdPartyErrorButValidReturnException {
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("access_token", token);
		requestParameters.put("method", "users.getInfo");
		requestParameters.put("v", "1.0");
		requestParameters.put("format", "JSON");
		final String sig = getSig(requestParameters);
		requestParameters.put("sig", sig);
		String response = HttpUtil.post(renrenConfiguration.getUserInfoUrl(), requestParameters);
		JSONObject jsonObject = JSONObject.fromObject(response.substring(1, response.length() - 1));
		if (jsonObject == null) {
			throw new ThirdPartyErrorReturnException("get renren nick error when return json is null");
		}
		String nick = jsonObject.optString("name", null);
		if (S.isInvalidValue(nick)) {
			throw new ThirdPartyErrorButValidReturnException("get renren nick error when return null,jsonObject is :" + jsonObject.toString());
		}
		return nick;
	}

	private String getSig(Map<String, String> requestParameters) {
		StringBuffer sb = new StringBuffer();
		sb.append(UrlUtil.getAppendValueAfterSorted(requestParameters)).append(renrenConfiguration.getAppSecret());
		return MD5Util.getMD5String(sb.toString());
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

}
