package com.koudai.udc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.util.Base64;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.utils.HttpUtil;
import com.koudai.udc.utils.MD5Util;
import com.koudai.udc.utils.UrlUtil;

public class TestCallbackController extends AbstractController {
	private static final Logger LOGGER = Logger.getLogger(TestCallbackController.class);

	private String redirectUrl;

	private String appkey;

	private String secret;

	private String serverUrl;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String appkey = request.getParameter("top_appkey");
		String session = request.getParameter("top_session");
		String sign = request.getParameter("top_sign");
		String parameter = request.getParameter("top_parameters");
		checkSign(appkey, session, parameter, sign);
		getRefreshTokenAndUserNick(parameter);
		StringBuffer casLogoutWithServiceUrl = new StringBuffer(redirectUrl);
		return new ModelAndView(new RedirectView(casLogoutWithServiceUrl.toString()));
	}

	protected boolean checkSign(String appkey, String session, String parameter, String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		StringBuilder result = new StringBuilder();
		result.append(appkey).append(parameter).append(session).append(appkey);
		byte[] bytes = md5.digest(result.toString().getBytes("UTF-8"));
		String encode = Base64.encode(bytes);
		return encode.equals(sign);
	}

	@SuppressWarnings("rawtypes")
	protected void getRefreshTokenAndUserNick(String top_parameters) throws WSSecurityException {
		String refreshToken = null;
		Map<String, String> map = convertBase64StringtoMap(top_parameters);
		if (map != null) {
			Iterator<Entry<String, String>> keyValuePairs = map.entrySet().iterator();
			for (int i = 0; i < map.size(); i++) {
				Map.Entry entry = (Map.Entry) keyValuePairs.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				if (key.equals("refresh_token")) {
					refreshToken = (String) value;
				}
			}
			LOGGER.info("refreshToken>>" + refreshToken);
			getUserNick(refreshToken);
		}
	}

	private Map<String, String> convertBase64StringtoMap(String str) throws WSSecurityException {
		if (str == null)
			return null;
		String keyvalues = null;
		try {
			keyvalues = new String(Base64.decode(URLDecoder.decode(str, "utf-8")));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException>>" + e.getMessage());
		}
		String[] keyvalueArray = keyvalues.split("\\&");
		Map<String, String> map = new HashMap<String, String>();
		for (String keyvalue : keyvalueArray) {
			String[] s = keyvalue.split("\\=");
			if (s == null || s.length != 2)
				return null;
			map.put(s[0], s[1]);
		}
		return map;
	}

	private void getUserNick(String refreshToken) {
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("method", "taobao.user.get");
		requestParameters.put("session", refreshToken);
		requestParameters.put("timestamp", String.valueOf(new Date().getTime()));
		requestParameters.put("format", "json");
		requestParameters.put("app_key", appkey);
		requestParameters.put("v", "2.0");
		requestParameters.put("sign_method", "md5");
		requestParameters.put("fields", "nick");
		String sign = getSign(requestParameters);
		requestParameters.put("sign", sign.toUpperCase());
		StringBuffer requestUrl = new StringBuffer(serverUrl);
		requestUrl.append("?");
		requestUrl.append(UrlUtil.getSortedContent(requestParameters));
		String response = HttpUtil.get(requestUrl.toString(), null);
		LOGGER.info("response>>" + response);
	}

	private String getSign(Map<String, String> requestParameters) {
		return MD5Util.getMD5String(new StringBuffer(secret).append(UrlUtil.getAppendValueAfterSorted2(requestParameters)).append(secret).toString());
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
}
