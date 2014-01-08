package com.koudai.udc.utils;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public final class JsonParseUtil {
	private static final Logger LOGGER = Logger.getLogger(JsonParseUtil.class);

	public static JSONObject parseJsonToObject(String url, Map<String, String> requestParameters) {
		try {
			String response = HttpUtil.post(url, requestParameters);
			return JSONObject.fromObject(response);
		} catch (Exception e) {
			LOGGER.error("parseJsonToObject error :" + e.getMessage());
			return null;
		}
	}

	public static JSONObject parseJsonToObjectByGet(String url, String contentType) {
		try {
			return JSONObject.fromObject(HttpUtil.get(url, contentType));
		} catch (Exception e) {
			LOGGER.error("parseJsonToObjectByGet error :" + e.getMessage());
			return null;
		}
	}

}
