package com.koudai.udc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class UrlUtil {

	public static String getEncodedSortedContent(Map<String, String> params) {
		return getSortedContent(encodeParameters(params, "utf-8"));
	}

	public static String getSortedContent(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer prestr = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (key != null && value != null) {
				if (i == keys.size() - 1) {
					prestr.append(key + "=" + value);
				} else {
					prestr.append(key + "=" + value + "&");
				}
			}
		}
		return prestr.toString();
	}

	public static Map<String, String> encodeParameters(Map<String, String> original, String charset) {
		Map<String, String> tempMap = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : original.entrySet()) {
			try {
				if (entry.getKey() != null && entry.getValue() != null)
					tempMap.put(entry.getKey(), URLEncoder.encode(entry.getValue(), charset));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return tempMap;
	}

}
