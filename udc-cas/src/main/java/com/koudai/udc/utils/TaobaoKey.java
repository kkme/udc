package com.koudai.udc.utils;

import java.util.regex.Pattern;

public final class TaobaoKey {

	public static final String RETURN_TITLE = "Taobao Return";
	public static final String LOGIN_TITLE = "Taobao Login";
	public static final String LOGOUT_TITLE = "Taobao Logout";

	public static final String USER_PREFIX = "@taobao:";

	public static final String USER_ID = "userID";
	public static final String BIND_ID = "bindID";
	public static final String TAOBAO_USER_ID = "taobaoUserId";
	public static final String TOKEN = "token";
	public static final String TOKEN_EXPIRE = "expires";
	public static final String REFRESH_TOKEN = "refreshToken";
	public static final String REFRESH_TOKEN_EXPIRE = "refreshExpire";

	public static final String MOBILE_ACCESS_TOKEN = "access_token";
	public static final String MOBILE_EXPIRES_IN = "expires_in";
	public static final String MOBILE_REFRESH_TOKEN = "refresh_token";
	public static final String MOBILE_RE_EXPIRES_IN = "re_expires_in";
	public static final String MOBILE_MOBILE_TOKEN = "mobile_token";
	public static final String MOBILE_TAOBAO_USER_ID = "taobao_user_id";
	public static final String MOBILE_TAOBAO_USER_NICK = "taobao_user_nick";
	public static final String MOBILE_ERROR = "error";
	public static final String MOBILE_ERROR_DES = "error_description";

	public static final String KEY_GRANT_TYPE = "grant_type";
	public static final String KEY_CODE = "code";
	public static final String KEY_CLIENT_ID = "client_id";
	public static final String KEY_CLIENT_SECRET = "client_secret";
	public static final String KEY_RESPONSE_TYPE = "response_type";
	public static final String KEY_REDIRECT_URI = "redirect_uri";
	public static final String KEY_SCOPE = "scope";
	public static final String KEY_VIEW = "view";
	public static final String KEY_STATE = "state";

	public static final String VALUE_GRANT_TYPE = "authorization_code";
	public static final String VALUE_RESPONSE_TYPE_TOKEN = "token";
	public static final String VALUE_RESPONSE_TYPE_USER = "user";
	public static final String VALUE_SCOPE = "promotion,item,usergrade";
	public static final String VALUE_VIEW_WAP = "wap";
	public static final String VALUE_VIEW_WEB = "web";

	public static final String ERROR_AUTHORIZE_REJECT = "authorize reject";
	public static final String ERROR_SUBUSER = "subuser can't access";

	public static final String BIND_USER_ID = "user_id";
	public static final String BIND_TO_URI = "to_uri";
	public static final String ERROR_BIND_RULE = "^(@sina|@qq):.*";

	public static final String HEAD_URL_KEY = "avatar";
	public static final String DEFAULT_HEAD_URL = "default/avatar";

	public static boolean isInvalidBind(String userId) {
		return !Pattern.matches(ERROR_BIND_RULE, userId);
	}
}
