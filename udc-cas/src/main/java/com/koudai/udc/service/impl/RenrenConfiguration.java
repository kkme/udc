package com.koudai.udc.service.impl;

import com.koudai.udc.service.Properties;

public class RenrenConfiguration {

	private static final String RENREN_APP_API_KEY = "renren_app_api_key";
	private static final String RENREN_APP_SECRET = "renren_app_secret";
	private static final String RENREN_CODE_URL = "renren_code_url";
	private static final String RENREN_API_LIST = "renren_api_list";
	private static final String RENREN_REDIRECT_URI = "renren_redirect_uri";
	private static final String RENREN_USER_INFO_URL = "renren_user_info_url";

	private final Properties properties;

	public RenrenConfiguration(Properties properties) {
		this.properties = properties;
	}

	public String getAppApiKey() {
		return properties.get(RENREN_APP_API_KEY);
	}

	public String getAppSecret() {
		return properties.get(RENREN_APP_SECRET);
	}

	public String getCodeUrl() {
		return properties.get(RENREN_CODE_URL);
	}

	public String getApiList() {
		return properties.get(RENREN_API_LIST);
	}

	public String getRedirectUri() {
		return properties.get(RENREN_REDIRECT_URI);
	}

	public String getUserInfoUrl() {
		return properties.get(RENREN_USER_INFO_URL);
	}
}
