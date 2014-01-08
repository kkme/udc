package com.koudai.udc.service.impl;

import com.koudai.udc.service.Properties;

public class TaobaoConfiguration {

	private static final String TAOBAO_MEITU_APP_KEY = "taobao_meitu_app_key";
	private static final String TAOBAO_MEITU_APP_SECRET = "taobao_meitu_app_secret";
	private static final String TAOBAO_BIJIA_APP_KEY = "taobao_bijia_app_key";
	private static final String TAOBAO_BIJIA_APP_SECRET = "taobao_bijia_app_secret";
	private static final String TAOBAO_MOBILE_APP_KEY = "taobao_mobile_app_key";
	private static final String TAOBAO_MOBILE_APP_SECRET = "taobao_mobile_app_secret";
	private static final String TAOBAO_UPLOAD_URL = "taobao_upload_url";
	private static final String TAOBAO_SERVER_URL = "taobao_server_url";
	private static final String TAOBAO_REDIRECT_URI = "taobao_redirect_uri";
	private static final String TAOBAO_TOKEN_URL = "taobao_token_url";
	private static final String TAOBAO_CODE_URL = "taobao_code_url";
	private static final String TAOBAO_CODE_RETURN_URL = "taobao_code_return_url";
	private static final String TAOBAO_BIJIA_RETURN_URL = "taobao_bijia_return_url";
	private static final String TAOBAO_LOGOUT_MEITU_URL = "taobao_logout_meitu_url";
	private static final String TAOBAO_LOGOUT_BIJIA_URL = "taobao_logout_bijia_url";
	private static final String TAOBAO_BIND_RETURN_URL = "taobao_bind_return_url";
	private static final String TAOBAO_UPLOAD_BINDING_URL = "taobao_upload_binding_url";

	private final Properties properties;

	public TaobaoConfiguration(Properties properties) {
		this.properties = properties;
	}

	public String getMeituAppKey() {
		return properties.get(TAOBAO_MEITU_APP_KEY);
	}

	public String getMeituAppSecret() {
		return properties.get(TAOBAO_MEITU_APP_SECRET);
	}

	public String getBijiaAppKey() {
		return properties.get(TAOBAO_BIJIA_APP_KEY);
	}

	public String getBijiaAppSecret() {
		return properties.get(TAOBAO_BIJIA_APP_SECRET);
	}

	public String getMobileAppKey() {
		return properties.get(TAOBAO_MOBILE_APP_KEY);
	}

	public String getMobileAppSecret() {
		return properties.get(TAOBAO_MOBILE_APP_SECRET);
	}

	public String getUploadUrl() {
		return properties.get(TAOBAO_UPLOAD_URL);
	}

	public String getServerUrl() {
		return properties.get(TAOBAO_SERVER_URL);
	}

	public String getRedirectUri() {
		return properties.get(TAOBAO_REDIRECT_URI);
	}

	public String getTokenUrl() {
		return properties.get(TAOBAO_TOKEN_URL);
	}

	public String getCodeUrl() {
		return properties.get(TAOBAO_CODE_URL);
	}

	public String getCodeReturnUrl() {
		return properties.get(TAOBAO_CODE_RETURN_URL);
	}

	public String getBijiaReturnUrl() {
		return properties.get(TAOBAO_BIJIA_RETURN_URL);
	}

	public String getLogoutMeituUrl() {
		return properties.get(TAOBAO_LOGOUT_MEITU_URL);
	}

	public String getLogoutBijiaUrl() {
		return properties.get(TAOBAO_LOGOUT_BIJIA_URL);
	}

	public String getBindReturnUrl() {
		return properties.get(TAOBAO_BIND_RETURN_URL);
	}

	public String getUploadBindingUrl() {
		return properties.get(TAOBAO_UPLOAD_BINDING_URL);
	}

}
