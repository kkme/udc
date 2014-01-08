package com.koudai.udc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.ws.security.util.Base64;
import org.springframework.webflow.execution.RequestContext;

public final class UrlUtil {

	private static final Logger LOGGER = Logger.getLogger(UrlUtil.class);

	public static String getEncodedSortedContent(Map<String, String> params) {
		return getSortedContent(encodeParameters(params, S.UTF_8));
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

	public static String getAppendValueAfterSorted(Map<String, String> params) {
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			sb.append(key).append("=").append(value);
		}
		return sb.toString();
	}

	public static String getAppendValueAfterSorted2(Map<String, String> params) {
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			sb.append(key).append(value);
		}
		return sb.toString();
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

	public static String base64Encode(String parameter) {
		try {
			return Base64.encode(parameter.getBytes(S.UTF_8));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public static String base64Decode(String parameter) {
		try {
			return new String(Base64.decode(parameter), S.UTF_8);
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		}
	}

	public static String encode(String parameter) {
		try {
			return URLEncoder.encode(parameter, S.UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static String initResultUrl(String prefix, Map parameters, String nick, String koudaiId) {
		Map<String, String> formatParameters = new HashMap<String, String>();
		Iterator it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			String key = (String) entry.getKey();
			String[] values = (String[]) entry.getValue();
			formatParameters.put(key, values[0]);
		}
		formatParameters.put(S.KOUDAI_ID, koudaiId);
		StringBuffer link = new StringBuffer(prefix);
		link.append("?").append(S.KOUDAI_TOKEN).append(encode(base64Encode(getSortedContent(formatParameters))));
		link.append("&").append(S.NICK_NAME).append(UrlUtil.encode(nick));
		return link.toString();
	}

	public static String initResultUrl(String prefix, Map<String, String> parameters, String nick) {
		StringBuffer link = new StringBuffer(prefix);
		link.append("?").append(S.KOUDAI_TOKEN).append(encode(base64Encode(getSortedContent(parameters))));
		link.append("&").append(S.NICK_NAME).append(UrlUtil.encode(nick));
		return link.toString();
	}

	public static String initReturnUrl(RequestContext context, String returnUrl) {
		Object service = context.getFlowScope().get("service");
		LOGGER.info("service>>>" + service);
		if (service == null) {
			return returnUrl;
		}
		return new StringBuffer(returnUrl).append("?service=").append(service).toString();
	}

}
