package com.koudai.udc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.service.CookieControllerService;
import com.koudai.udc.service.impl.DoubanConfiguration;
import com.koudai.udc.utils.AuthUtil;
import com.koudai.udc.utils.DoubanKey;
import com.koudai.udc.utils.HttpUtil;
import com.koudai.udc.utils.PropertiesUtil;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UrlUtil;

public class MobileLoginDoubanController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(MobileLoginDoubanController.class);

	protected CookieControllerService cookieControllerService;

	private DoubanConfiguration doubanConfiguration;

	private String errorUrl;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		S.logForMap(DoubanKey.LOGIN_TITLE, request.getParameterMap());
		long beginTime = System.currentTimeMillis();
		String requestTokenUrl = constructRequestTokenUrl();
		final String res = HttpUtil.get(requestTokenUrl, null);
		if (S.isInvalidValue(res)) {
			LOGGER.error("Get douban token failed");
			return new ModelAndView(new RedirectView(errorUrl));
		}
		String requestToken = getRequestTokenAndSetToSession(request, response, res);
		String authorizeUrl = contrusctAuthorizeUrl(requestToken);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("mobile loginDouban cost>>>" + costTime);
		return new ModelAndView(new RedirectView(authorizeUrl));
	}

	private String getRequestTokenAndSetToSession(HttpServletRequest request, HttpServletResponse response, String res) {
		Properties properties = PropertiesUtil.getPropertiesWithString(res);
		final String requestToken = properties.getProperty(DoubanKey.OAUTH_TOKEN);
		final String requestTokenSecret = properties.getProperty(DoubanKey.OAUTH_TOKEN_SECRET);
		cookieControllerService.setCookieToResponse(request, response, S.COOKIE_DOMAIN, DoubanKey.REQUEST_TOKEN, requestToken, S.DEFAULT_MAX_AGE);
		cookieControllerService.setCookieToResponse(request, response, S.COOKIE_DOMAIN, DoubanKey.REQUEST_TOKEN_SECRET, requestTokenSecret, S.DEFAULT_MAX_AGE);
		return requestToken;
	}

	private String contrusctAuthorizeUrl(String requestToken) {
		StringBuffer authorizeUrl = new StringBuffer(doubanConfiguration.getAuthorizeUrl()).append("?");
		authorizeUrl.append("oauth_token=").append(requestToken);
		authorizeUrl.append("&oauth_callback=").append(doubanConfiguration.getRedirectUri());
		authorizeUrl.append("&p=1");
		return authorizeUrl.toString();
	}

	private String constructRequestTokenUrl() {
		final String nonce = AuthUtil.getNonce();
		final String oauth_timestamp = (new Date().getTime() + "").substring(0, 10);
		String baseString = getBaseString(nonce, oauth_timestamp);
		String oauth_signature = AuthUtil.hmacsha1(baseString, doubanConfiguration.getAppSecret() + "&");
		StringBuffer requestTokenUrl = new StringBuffer(doubanConfiguration.getRequestTokenUrl()).append("?");
		Map<String, String> params = new HashMap<String, String>();
		constructNormalParameters(nonce, oauth_timestamp, params);
		params.put(DoubanKey.OAUTH_SIGNATURE, oauth_signature);
		String encodedSortedContent = UrlUtil.getEncodedSortedContent(params);
		requestTokenUrl.append(encodedSortedContent);
		return requestTokenUrl.toString();
	}

	public String getBaseString(String nonce, String oauth_timestamp) {
		StringBuffer prefix = new StringBuffer();
		prefix.append(DoubanKey.OAUTH_REQUEST_METHOD).append("&");
		String sortedContentAfterEncoder = "";
		try {
			prefix.append(URLEncoder.encode(doubanConfiguration.getRequestTokenUrl(), S.UTF_8)).append("&");
			Map<String, String> params = new HashMap<String, String>();
			constructNormalParameters(nonce, oauth_timestamp, params);
			sortedContentAfterEncoder = URLEncoder.encode(UrlUtil.getSortedContent(params), S.UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException msg :" + e.getMessage());
		}
		return new StringBuffer(prefix.toString()).append(sortedContentAfterEncoder).toString();
	}

	private void constructNormalParameters(final String nonce, final String oauth_timestamp, Map<String, String> params) {
		params.put(DoubanKey.OAUTH_NONCE, nonce);
		params.put(DoubanKey.OAUTH_VERSION, DoubanKey.OAUTH_VERSION_VALUE);
		params.put(DoubanKey.OAUTH_TIMESTAMP, oauth_timestamp);
		params.put(DoubanKey.OAUTH_CONSUMER_KEY, doubanConfiguration.getAppApiKey());
		params.put(DoubanKey.OAUTH_SIGNATURE_METHOD, DoubanKey.OAUTH_SIGNATURE_METHOD_VALUE);
	}

	public void setDoubanConfiguration(DoubanConfiguration doubanConfiguration) {
		this.doubanConfiguration = doubanConfiguration;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	public void setCookieControllerService(CookieControllerService cookieControllerService) {
		this.cookieControllerService = cookieControllerService;
	}

}
