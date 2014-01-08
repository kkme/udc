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

import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.service.CookieControllerService;
import com.koudai.udc.service.impl.DoubanConfiguration;
import com.koudai.udc.service.impl.XmlParseService;
import com.koudai.udc.utils.AuthUtil;
import com.koudai.udc.utils.DoubanKey;
import com.koudai.udc.utils.HttpUtil;
import com.koudai.udc.utils.PropertiesUtil;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UrlUtil;

public class MobileDoubanController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(MobileDoubanController.class);

	private String successUrl;

	private String errorUrl;

	private DoubanConfiguration doubanConfiguration;

	protected CookieControllerService cookieControllerService;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		try {
			long beginTime = System.currentTimeMillis();
			final String platform = request.getParameter(S.KOUDAI_PLATFORM);
			final String version = request.getParameter(S.KOUDAI_VERSION);
			S.logForMap(DoubanKey.RETURN_TITLE, request.getParameterMap());
			if (S.isInvalidValue(platform) && S.isInvalidValue(version)) {
				String requestTokenUrl = constructRequestTokenUrl(request);
				final String res = HttpUtil.get(requestTokenUrl, null);
				if (S.isInvalidValue(res)) {
					throw new ThirdPartyErrorReturnException("get douban access token error");
				}
				Properties properties = PropertiesUtil.getPropertiesWithString(res);
				final String accessToken = properties.getProperty(DoubanKey.OAUTH_TOKEN);
				final String accessSecret = properties.getProperty(DoubanKey.OAUTH_TOKEN_SECRET);
				final String userId = properties.getProperty(DoubanKey.DOUBAN_USER_ID);
				if (S.isInvalidValue(accessToken)) {
					throw new ThirdPartyErrorReturnException("get douban access token error return with null or empty ,res is :" + res);
				}
				if (S.isInvalidValue(accessSecret)) {
					throw new ThirdPartyErrorReturnException("get douban access secret error return with null or empty ,res is :" + res);
				}
				if (S.isInvalidValue(userId)) {
					throw new ThirdPartyErrorReturnException("get douban userId error return with null or empty ,res is :" + res);
				}
				final String nick = getNick(userId);
				successUrl = getSuccessUrl(nick, accessToken, accessSecret, userId);
			}
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("mobile douban cost>>>" + costTime);
			return new ModelAndView(new RedirectView(successUrl));
		} catch (Exception e) {
			LOGGER.error(e);
			return new ModelAndView(new RedirectView(errorUrl));
		}
	}

	private String getSuccessUrl(final String nick, final String oauthAccessToken, final String oauthAccessTokenSecret, final String userId) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(DoubanKey.MOBILE_ACCESS_TOKEN, oauthAccessToken);
		parameters.put(DoubanKey.MOBILE_ACCESS_SECRET, oauthAccessTokenSecret);
		parameters.put(DoubanKey.MOBILE_USER_ID, userId);
		return UrlUtil.initResultUrl(successUrl, parameters, nick);
	}

	private String constructRequestTokenUrl(HttpServletRequest request) {
		final String requestToken = cookieControllerService.getStringValueFromCookie(request, DoubanKey.REQUEST_TOKEN);
		final String requestTokenSecret = cookieControllerService.getStringValueFromCookie(request, DoubanKey.REQUEST_TOKEN_SECRET);
		final String nonce = AuthUtil.getNonce();
		final String oauth_timestamp = (new Date().getTime() + "").substring(0, 10);
		StringBuffer requestTokenUrl = new StringBuffer(doubanConfiguration.getAccessTokenUrl()).append("?");
		try {
			String baseString = getBaseString(nonce, oauth_timestamp, requestToken);
			String oauthSignature = AuthUtil.hmacsha1(baseString, URLEncoder.encode(doubanConfiguration.getAppSecret(), S.UTF_8) + "&" + URLEncoder.encode(requestTokenSecret, S.UTF_8));
			Map<String, String> params = new HashMap<String, String>();
			constructNormalParameters(requestToken, nonce, oauth_timestamp, params);
			params.put(DoubanKey.OAUTH_SIGNATURE, oauthSignature);
			String encodedSortedContent = UrlUtil.getEncodedSortedContent(params);
			requestTokenUrl.append(encodedSortedContent);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException , reason :" + e.getMessage());
		}
		return requestTokenUrl.toString();

	}

	private String getNick(String userId) throws ThirdPartyErrorReturnException {
		StringBuffer requestUserInfoUrl = new StringBuffer(doubanConfiguration.getUserInfoUrl()).append(userId);
		String response = HttpUtil.get(requestUserInfoUrl.toString(), null);
		String nick = XmlParseService.getValueByElementName(response, DoubanKey.API_NICKNAME);
		if (S.isInvalidValue(nick)) {
			throw new ThirdPartyErrorReturnException("get douban nick error when return nick is null,response is:" + response);
		}
		return nick;
	}

	public String getBaseString(String nonce, String oauth_timestamp, String accessToken) throws UnsupportedEncodingException {
		StringBuffer prefix = new StringBuffer();
		prefix.append(DoubanKey.OAUTH_REQUEST_METHOD).append("&");
		prefix.append(URLEncoder.encode(doubanConfiguration.getAccessTokenUrl(), S.UTF_8)).append("&");
		Map<String, String> params = new HashMap<String, String>();
		constructNormalParameters(accessToken, nonce, oauth_timestamp, params);
		String sortedContent = UrlUtil.getSortedContent(params);
		return new StringBuffer(prefix.toString()).append(URLEncoder.encode(sortedContent, S.UTF_8)).toString();
	}

	private void constructNormalParameters(String accessToken, final String nonce, final String oauth_timestamp, Map<String, String> params) {
		params.put(DoubanKey.OAUTH_NONCE, nonce);
		params.put(DoubanKey.OAUTH_VERSION, DoubanKey.OAUTH_VERSION_VALUE);
		params.put(DoubanKey.OAUTH_TIMESTAMP, oauth_timestamp);
		params.put(DoubanKey.OAUTH_TOKEN, accessToken);
		params.put(DoubanKey.OAUTH_CONSUMER_KEY, doubanConfiguration.getAppApiKey());
		params.put(DoubanKey.OAUTH_SIGNATURE_METHOD, DoubanKey.OAUTH_SIGNATURE_METHOD_VALUE);
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	public void setDoubanConfiguration(DoubanConfiguration doubanConfiguration) {
		this.doubanConfiguration = doubanConfiguration;
	}

	public void setCookieControllerService(CookieControllerService cookieControllerService) {
		this.cookieControllerService = cookieControllerService;
	}

}
