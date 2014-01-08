package com.koudai.udc.service.impl;

import com.koudai.udc.service.Properties;

public class QQConfiguration {

	private static final String TENCENT_QQ_APP_KEY = "tencent_qq_app_key";
	private static final String TENCENT_QQ_APP_SECRET = "tencent_qq_app_secret";
	private static final String TENCENT_QQ_BIJIA_APP_KEY = "tencent_qq_bijia_app_key";
	private static final String TENCENT_QQ_BIJIA_APP_SECRET = "tencent_qq_bijia_app_secret";
	private static final String TENCENT_QQ_UPLOAD_URL = "tencent_qq_upload_url";
	private static final String TENCENT_QQ_CODE_URL = "tencent_qq_code_url";
	private static final String TENCENT_QQ_CODE_RETURN_URL = "tencent_qq_code_return_url";
	private static final String TENCENT_QQ_TOKEN_URL = "tencent_qq_token_url";
	private static final String TENCENT_QQ_SERVER_URL = "tencent_qq_server_url";
	private static final String TENCENT_QQ_USER_INFO_URL = "tencent_qq_user_info_url";
	private static final String TENCENT_QQ_API_LIST = "tencent_qq_api_list";
	private static final String TENCENT_QQ_REDIRECT_URI = "tencent_qq_redirect_uri";

	private final Properties properties;

	public QQConfiguration(Properties properties) {
		this.properties = properties;
	}

	public String getAppKey() {
		return properties.get(TENCENT_QQ_APP_KEY);
	}

	public String getAppSecret() {
		return properties.get(TENCENT_QQ_APP_SECRET);
	}

	public String getUploadUrl() {
		return properties.get(TENCENT_QQ_UPLOAD_URL);
	}

	public String getCodeUrl() {
		return properties.get(TENCENT_QQ_CODE_URL);
	}

	public String getCodeReturnUrl() {
		return properties.get(TENCENT_QQ_CODE_RETURN_URL);
	}

	public String getTokenUrl() {
		return properties.get(TENCENT_QQ_TOKEN_URL);
	}

	public String getServerUrl() {
		return properties.get(TENCENT_QQ_SERVER_URL);
	}

	public String getUserInfoUrl() {
		return properties.get(TENCENT_QQ_USER_INFO_URL);
	}

	public String getApiList() {
		return properties.get(TENCENT_QQ_API_LIST);
	}

	public String getRedirectUri() {
		return properties.get(TENCENT_QQ_REDIRECT_URI);
	}

	public String getBijiaAppKey() {
		return properties.get(TENCENT_QQ_BIJIA_APP_KEY);
	}

	public String getBijiaAppSecret() {
		return properties.get(TENCENT_QQ_BIJIA_APP_SECRET);
	}

}
