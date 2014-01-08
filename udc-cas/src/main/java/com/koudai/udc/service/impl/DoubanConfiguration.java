package com.koudai.udc.service.impl;

import com.koudai.udc.service.Properties;

public class DoubanConfiguration {

	private static final String DOUBAN_APP_API_KEY = "douban_app_api_key";
	private static final String DOUBAN_APP_SECRET = "douban_app_secret";
	private static final String DOUBAN_GET_REQUEST_TOKEN_URL = "douban_get_request_token_url";
	private static final String DOUBAN_REDIRECT_URI = "douban_redirect_uri";
	private static final String DOUBAN_AUTHORIZE_URL = "douban_authorize_url";
	private static final String DOUBAN_ACCESS_TOKEN_URL = "douban_access_token_url";
	private static final String DOUBAN_USER_INFO_URL = "douban_user_info_url";

	private final Properties properties;

	public DoubanConfiguration(Properties properties) {
		this.properties = properties;
	}

	public String getAppApiKey() {
		return properties.get(DOUBAN_APP_API_KEY);
	}

	public String getAppSecret() {
		return properties.get(DOUBAN_APP_SECRET);
	}

	public String getRequestTokenUrl() {
		return properties.get(DOUBAN_GET_REQUEST_TOKEN_URL);
	}

	public String getRedirectUri() {
		return properties.get(DOUBAN_REDIRECT_URI);
	}

	public String getAuthorizeUrl() {
		return properties.get(DOUBAN_AUTHORIZE_URL);
	}

	public String getAccessTokenUrl() {
		return properties.get(DOUBAN_ACCESS_TOKEN_URL);
	}

	public String getUserInfoUrl() {
		return properties.get(DOUBAN_USER_INFO_URL);
	}
}
