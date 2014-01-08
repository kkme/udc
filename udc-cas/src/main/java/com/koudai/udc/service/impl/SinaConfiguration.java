package com.koudai.udc.service.impl;

import com.koudai.udc.service.Properties;

public class SinaConfiguration {

	private static final String SINA_WEIBO_APP_KEY = "sina_weibo_app_key";
	private static final String SINA_WEIBO_APP_SECRET = "sina_weibo_app_secret";
	private static final String SINA_WEIBO_BIJIA_APP_KEY = "sina_weibo_bijia_app_key";
	private static final String SINA_WEIBO_BIJIA_APP_SECRET = "sina_weibo_bijia_app_secret";
	private static final String SINA_WEIBO_UPLOAD_URL = "sina_weibo_upload_url";
	private static final String SINA_WEIBO_CODE_URL = "sina_weibo_code_url";
	private static final String SINA_WEIBO_CODE_RETURN_URL = "sina_weibo_code_return_url";
	private static final String SINA_WEIBO_TOKEN_URL = "sina_weibo_token_url";
	private static final String SINA_WEIBO_API_NICK_URL = "sina_weibo_api_nick_url";
	private static final String SINA_WEIBO_API_LOGOUT_URL = "sina_weibo_api_logout_url";
	private static final String SINA_WEIBO_REDIRECT_URI = "sina_weibo_redirect_uri";
	private static final String SINA_WEIBO_API_UID_URI = "sina_weibo_api_uid_url";

	private final Properties properties;

	public SinaConfiguration(Properties properties) {
		this.properties = properties;
	}

	public String getAppKey() {
		return properties.get(SINA_WEIBO_APP_KEY);
	}

	public String getAppSecret() {
		return properties.get(SINA_WEIBO_APP_SECRET);
	}

	public String getUploadUrl() {
		return properties.get(SINA_WEIBO_UPLOAD_URL);
	}

	public String getCodeUrl() {
		return properties.get(SINA_WEIBO_CODE_URL);
	}

	public String getCodeReturnUrl() {
		return properties.get(SINA_WEIBO_CODE_RETURN_URL);
	}

	public String getTokenUrl() {
		return properties.get(SINA_WEIBO_TOKEN_URL);
	}

	public String getNickUrl() {
		return properties.get(SINA_WEIBO_API_NICK_URL);
	}

	public String getLogoutUrl() {
		return properties.get(SINA_WEIBO_API_LOGOUT_URL);
	}

	public String getRedirectUri() {
		return properties.get(SINA_WEIBO_REDIRECT_URI);
	}

	public String getUidUrl() {
		return properties.get(SINA_WEIBO_API_UID_URI);
	}

	public String getBijiaAppKey() {
		return properties.get(SINA_WEIBO_BIJIA_APP_KEY);
	}

	public String getBijiaAppSecret() {
		return properties.get(SINA_WEIBO_BIJIA_APP_SECRET);
	}

}
