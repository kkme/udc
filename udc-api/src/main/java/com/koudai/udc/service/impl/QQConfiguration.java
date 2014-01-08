package com.koudai.udc.service.impl;

import com.koudai.udc.service.Properties;

public class QQConfiguration {

	private static final String CHECK_PAGE_FANS_URL = "tencent_qq_check_page_fans_url";
	private static final String MEITU_APP_KEY = "tencent_qq_meitu_app_key";
	private static final String MEITU_PAGE_ID = "tencent_qq_meitu_page_id";
	private static final String BIJIA_APP_KEY = "tencent_qq_bijia_app_key";
	private static final String BIJIA_PAGE_ID = "tencent_qq_bijia_page_id";

	private final Properties properties;

	public QQConfiguration(Properties properties) {
		this.properties = properties;
	}

	public String getCheckPageFansUrl() {
		return properties.get(CHECK_PAGE_FANS_URL);
	}

	public String getMeituAppKey() {
		return properties.get(MEITU_APP_KEY);
	}

	public String getMeituPageId() {
		return properties.get(MEITU_PAGE_ID);
	}

	public String getBijiaAppKey() {
		return properties.get(BIJIA_APP_KEY);
	}

	public String getBijiaPageId() {
		return properties.get(BIJIA_PAGE_ID);
	}

}
