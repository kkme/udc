package com.koudai.udc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.domain.LoginPlatform;

public final class S {

	public static final String ISO_8859_1 = "ISO-8859-1";

	public static final String UTF_8 = "UTF-8";

	public static final String COMMA_STR = ",";

	public static final String COLON_STR = ":";

	public static final int BATCH_SIZE = 500;

	public static final String EMPTY_STR = "";

	public static final String TAB = "\t";

	public static final int SUCCESS_CODE = 0;

	public static final int ERROR_CODE = 1;

	public static final int USER_FIRST_LOGIN_CODE = 1;

	public static final int USER_HAS_LOGIN_CODE = 0;

	public static final int USER_ERROR_CODE = 2;

	public static final String PREFIX_ANONYMOUS = "@anonymous:";

	public static final String PREFIX_TAOBAO = "@taobao:";

	public static final String ZERO = "0";

	public static final String QQ_PREFIX = "@qq:";

	public static final String JSON_CONTENT_TYPE = "application/json";

	public static final int UPLOAD_SUCCESS_AND_COLLECT_SOME_CODE = 0;

	public static final int UPLOAD_SUCCESS_AND_COLLECT_ALL_CODE = 2;

	public static final int UPLOAD_ERROR_CODE = 1;

	public static final String TYPE_GIRL = "Combine_Default";

	public static final String TAOBAO_USER_NAME_RULE = "^(@taobao):.*";

	public static final String QQ_USER_NAME_RULE = "^(@qq):.*";

	public static final String ANONYMOUS_USER_NAME_RULE = "^(@anonymous):.*";

	public static final String REAL_USER_NAME_RULE = "^(@taobao|@sina|@qq):.*";

	public static final String VALID_USER_NAME_RULE = "^(@taobao|@sina|@qq|@anonymous):.*";

	public static final String BIND_USER_NAME_RULE = "^(@sina|@qq):.*";

	public static final int USER_NOT_ACTIVATED = 0;

	public static final int USER_ACTIVATED_NOT_TESTED = 1;

	public static final int USER_ACTIVATED_AND_TESTED = 2;

	public static final int WHITE_LIST_USER = 3;

	public static final int TAOBAO_YELLOW_DIAMONDS_LEVEL = 6;

	public static final int FIVE_SECONDS = 5000;

	public static final int ERROR_START = 0;

	public static final int COLD_START = 1;

	public static final int HOT_START = 2;

	public static final String MAN_STYLE_QID = "16";

	public static final String WOMAN_STYLE_QID = "10";

	public static final int INT_TRUE = 1;

	public static final int INT_FALSE = 0;

	public static final int CHECK_FOLLOW_QZONE_ERROR_CODE = 0;

	public static final int USER_FOLLOW_QZONE_CODE = 1;

	public static final int USER_NOT_FOLLOW_QZONE_CODE = 2;

	public static final int USER_HAS_BOUND = 0;

	public static final int USER_BOUND_ERROR = 1;

	public static final int USER_NOT_BOUND = 2;

	public static final String LISTENER_PRODUCTIDS = "taobao123:0";

	public static final String LISTENER_FIRST_PRODUCT_NAME = "测试数据";

	public static final String DEFAULT_NICK = "My,dear";

	public static final List<String> EMPTY_STR_LIST = new ArrayList<String>();

	public static final Map<String, Integer> EMPTY_INT_MAP = new HashMap<String, Integer>();

	public static final Map<String, String> EMPTY_STR_MAP = new HashMap<String, String>();

	public static final List<IdAndDate> EMPTY_ID_AND_DATE_LIST = new ArrayList<IdAndDate>();

	public static final Set<String> BIJIA_ERROR_STR = new HashSet<String>();

	public static final List<String> LISTENER_TOKENS = new ArrayList<String>();

	public static final Set<String> INVALID_SUB_IDS = new HashSet<String>();

	public static final Set<String> STYLE_QID = new HashSet<String>();

	static {
		BIJIA_ERROR_STR.add(EMPTY_STR);
		BIJIA_ERROR_STR.add("undefined");
		BIJIA_ERROR_STR.add("?");

		LISTENER_TOKENS.add("1e7301ad8e395f36caeb236fb60b01c006a580e00061c2baf9ac499841e29627");
		LISTENER_TOKENS.add("fa621af8f2e62ff7eb00c9fc943e14c1e1c5b722395dc8300c6883bfb8b3d170");
		LISTENER_TOKENS.add("26834ec465b5a6c97f0a69cfed36e59931b7ab3ca71c71006ad8338ac27c9cbd");

		INVALID_SUB_IDS.add("@anonymous:null_null");
		INVALID_SUB_IDS.add("@anonymous:null");

		STYLE_QID.add(MAN_STYLE_QID);
		STYLE_QID.add(WOMAN_STYLE_QID);
	}

	public static boolean isInvalidValue(String value) {
		return StringUtils.isBlank(value);
	}

	public static boolean isTaobaoUser(String userId) {
		return Pattern.matches(TAOBAO_USER_NAME_RULE, userId);
	}

	public static boolean isQQUser(String userId) {
		return Pattern.matches(QQ_USER_NAME_RULE, userId);
	}

	public static boolean isAnonymousUser(String userId) {
		return Pattern.matches(ANONYMOUS_USER_NAME_RULE, userId);
	}

	public static boolean isRealUser(String userId) {
		return Pattern.matches(REAL_USER_NAME_RULE, userId);
	}

	public static boolean isValidUser(String userId) {
		return Pattern.matches(VALID_USER_NAME_RULE, userId);
	}

	public static boolean isWebPlatform(String platform) {
		return LoginPlatform.meitu.toString().equals(platform) || LoginPlatform.bijia.toString().equals(platform);
	}

	public static boolean isBindUser(String userId) {
		return Pattern.matches(BIND_USER_NAME_RULE, userId);
	}

	public static boolean isNeedToUpdateNick(String nick) {
		return !S.DEFAULT_NICK.equals(nick);
	}
}
