package com.koudai.udc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.koudai.udc.service.Properties;
import com.koudai.udc.utils.S;

public class MyStreetConfiguration {

	private static final String VIP_MODEL = "vip_model";

	private static final String SESSION_DELAY = "session_delay";

	private static final String WHITE_LIST = "white_list";

	private static final String COLD_LIST = "cold_list";

	private final Properties properties;

	public MyStreetConfiguration(Properties properties) {
		this.properties = properties;
	}

	public boolean isVipModel() {
		return Boolean.valueOf(properties.get(VIP_MODEL));
	}

	public long getSessionDelay() {
		return Long.valueOf(properties.get(SESSION_DELAY));
	}

	public List<String> getWhiteList() {
		List<String> whiteList = new ArrayList<String>();
		List<String> tempList = Arrays.asList(properties.get(WHITE_LIST, S.ISO_8859_1, S.UTF_8).split(","));
		for (String temp : tempList) {
			whiteList.add(temp.toLowerCase());
		}
		return whiteList;
	}

	public List<String> getColdList() {
		List<String> coldList = new ArrayList<String>();
		List<String> tempList = Arrays.asList(properties.get(COLD_LIST, S.ISO_8859_1, S.UTF_8).split(","));
		for (String temp : tempList) {
			coldList.add(temp.toLowerCase());
		}
		return coldList;
	}

}
