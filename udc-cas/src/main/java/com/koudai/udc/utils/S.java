package com.koudai.udc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.util.Base64;

import com.koudai.udc.exception.DomainException;

public final class S {

	private static final Logger LOGGER = Logger.getLogger(S.class);

	public static final String UTF_8 = "utf-8";

	public static final String USER_ID_COOKIE = "user_id";

	public static final String USER_ERROR_CODE = "1";

	public static final String NICK = "nick";

	public static final String SHOW_NICK_COOKIE = "show_nick";

	public static final String LOGIN_PLATFORM_COOKIE = "loginPlatform";

	public static final String COOKIE_DOMAIN = ".koudai.com";

	public static final int DEFAULT_MAX_AGE = -1;

	public static final String PLATFORM = "platform";

	public static final String MEITU_PALTFORM = "meitu";

	public static final String BIJIA_PLATFORM = "bijia";

	public static final String MOBILE_PLATFORM = "mobile";

	public static final String JSON_CONTENT_TYPE = "application/json";

	public static final String KOUDAI_PLATFORM = "koudaiplatform";

	public static final String KOUDAI_VERSION = "koudaiversion";

	public static final String EMPTY_STR = "";

	public static final String IPHONE = "iphone";

	public static final String IPAD = "ipad";

	public static final String ANDROID = "android";

	public static final String IOS_VERSION = "1.9";

	public static final String ANDROID_VERSION = "1.4";

	public static final String KEY_KOUDAI_TOKEN = "koudaitoken";

	public static final String KOUDAI_TOKEN = "koudaitoken=";

	public static final String NICK_NAME = "nickname=";

	public static final String KOUDAI_ID = "koudaiid";

	public static final int HTTP_SUCCESS_CODE = 200;

	public static final String COMMA_STR = ",";

	public static final int ONE_THOUSAND = 1000;

	public static final String ERROR_USER_NAME_RULE = "^(@taobao|@sina|@qq):null$";

	public static final String USER_AGENT = "User-Agent";

	public static final String WINDOWS_8 = "Windows NT 6.2";

	public static final String SERVICE = "service";

	public static final String DATA = "data";

	public static final String HAS_UPLOAD_HEAD_KEY = "hasUploadHead";

	public static final int HAS_UPLOAD_HEAD = 1;

	public static final String USER_ID = "userID";

	public static final String RESULT = "result";

	public static final String THIRD_HEADURL = "thirdHeadurl";

	public static final int SUCCESS_CODE = 0;

	public static final int ERROR_CODE = 1;

	public static final String SPLIT = "@@##%%";

	public static final int TRY_TIMES = 3;

	public static final String SERVER_BUSY_CODE = "1001";

	public static final String SUCCESS_CODE_STR = "0";

	public static boolean isInvalidValue(String value) {
		return StringUtils.isBlank(value);
	}

	public static boolean isInterimVersion(String platform, String version) throws DomainException {
		if (IPHONE.equals(platform) || IPAD.equals(platform)) {
			return version.contains(IOS_VERSION);
		}
		if (ANDROID.equals(platform)) {
			return version.contains(ANDROID_VERSION);
		}
		throw new DomainException("Invalid platform < " + platform + " >");
	}

	public static void verifyParametersNotNull(Map<String, String> parameters) throws DomainException {
		Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> next = it.next();
			if (S.isInvalidValue(next.getValue())) {
				throw new DomainException("Parameter with key < " + next.getKey() + " > is null or empty");
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void logForMap(String title, Map parameters) {
		StringBuffer sb = new StringBuffer();
		Iterator it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			String key = (String) entry.getKey();
			String[] values = (String[]) entry.getValue();
			sb.append(key);
			sb.append("=");
			sb.append(values[0]);
			sb.append("|");
		}
		LOGGER.info("< " + title + " > parameters with key and value is < " + sb.toString() + " >");
	}

	public static boolean isInvalidUser(String userId) {
		return Pattern.matches(ERROR_USER_NAME_RULE, userId);
	}

	public static String getExpireDateMillisecond(String expiresIn) {
		long currentTime = System.currentTimeMillis();
		long expireTime = currentTime + Long.valueOf(expiresIn) * ONE_THOUSAND;
		return String.valueOf(expireTime);
	}

	public static Map<String, String> convertBase64StringtoMap(String str) {
		if (str == null)
			return null;
		String keyvalues = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			keyvalues = new String(Base64.decode(URLDecoder.decode(str, "utf-8")));
			String[] keyvalueArray = keyvalues.split("\\&");
			for (String keyvalue : keyvalueArray) {
				String[] s = keyvalue.split("\\=");
				if (s == null || s.length != 2)
					return null;
				map.put(s[0], s[1]);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException>>" + e.getMessage() + ",str is :" + str);
		} catch (WSSecurityException e) {
			LOGGER.error("WSSecurityException>>" + e.getMessage() + ",str is :" + str);
		}
		return map;
	}
}
