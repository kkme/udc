package com.koudai.udc.service.impl;

import com.koudai.udc.service.Properties;

public class TaobaoConfiguration {

	private static final String TAOBAO_API_URL = "taobao_api_url";
	private static final String TAOBAO_MOBILE_APP_KEY = "taobao_mobile_app_key";
	private static final String TAOBAO_MOBILE_APP_SECRET = "taobao_mobile_app_secret";
	private static final String TAOBAO_REFRESH_TOKEN_URL = "taobao_refresh_token_url";
	private static final String TAOBAO_MEITU_APP_KEY = "taobao_meitu_app_key";
	private static final String TAOBAO_MEITU_APP_SECRET = "taobao_meitu_app_secret";

	private final Properties properties;

	public TaobaoConfiguration(Properties properties) {
		this.properties = properties;
	}

	public String getTaobaoApiUrl() {
		return properties.get(TAOBAO_API_URL);
	}

	public String getTaobaoMobileAppKey() {
		return properties.get(TAOBAO_MOBILE_APP_KEY);
	}

	public String getTaobaoMobileAppSecret() {
		return properties.get(TAOBAO_MOBILE_APP_SECRET);
	}

	public String getTaobaoRefreshTokenUrl() {
		return properties.get(TAOBAO_REFRESH_TOKEN_URL);
	}

	public String getTaobaoMeituAppKey() {
		return properties.get(TAOBAO_MEITU_APP_KEY);
	}

	public String getTaobaoMeituAppSecret() {
		return properties.get(TAOBAO_MEITU_APP_SECRET);
	}

}
