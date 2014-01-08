package com.koudai.udc.utils;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public final class JsonParseUtil {

	public static JSONObject parseJsonToObject(String url, Map<String, String> requestParameters) {
		try {
			return JSONObject.fromObject(HttpUtil.post(url, requestParameters));
		} catch (Exception e) {
			return null;
		}
	}

	public static JSONArray parseJsonToArray(String url, Map<String, String> requestParameters) {
		try {
			return JSONArray.fromObject(HttpUtil.post(url, requestParameters));
		} catch (Exception e) {
			return null;
		}
	}

	public static JSONObject parseJsonToObject(String url, String requestParameters) {
		try {
			return JSONObject.fromObject(HttpUtil.post(url, requestParameters));
		} catch (Exception e) {
			return null;
		}
	}

	public static JSONArray parseJsonToArray(String url, String requestParameters) {
		try {
			return JSONArray.fromObject(HttpUtil.post(url, requestParameters));
		} catch (Exception e) {
			return null;
		}
	}

}
