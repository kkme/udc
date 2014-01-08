package com.koudai.udc.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.UserBuyerGetRequest;
import com.taobao.api.response.UserBuyerGetResponse;

public final class TaobaoApiUtil {

	public static String getLoginUrl(String requestUrl, String appKey, String returnUrl, String responseType, String view, String state) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(TaobaoKey.KEY_CLIENT_ID, appKey);
		parameters.put(TaobaoKey.KEY_REDIRECT_URI, returnUrl);
		parameters.put(TaobaoKey.KEY_RESPONSE_TYPE, responseType);
		parameters.put(TaobaoKey.KEY_SCOPE, TaobaoKey.VALUE_SCOPE);
		parameters.put(TaobaoKey.KEY_VIEW, view);
		if (!S.isInvalidValue(state)) {
			parameters.put(TaobaoKey.KEY_STATE, state);
		}
		StringBuffer loginUrl = new StringBuffer(requestUrl);
		loginUrl.append("?");
		loginUrl.append(UrlUtil.getEncodedSortedContent(parameters));
		return loginUrl.toString();
	}

	public static String getBijiaLoginUrl(String appKey, String requestUrl, String returnUrl) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(TaobaoKey.KEY_RESPONSE_TYPE, TaobaoKey.VALUE_RESPONSE_TYPE_USER);
		parameters.put(TaobaoKey.KEY_REDIRECT_URI, returnUrl);
		parameters.put(TaobaoKey.KEY_CLIENT_ID, appKey);
		StringBuffer loginUrl = new StringBuffer(requestUrl);
		loginUrl.append("?");
		loginUrl.append(UrlUtil.getEncodedSortedContent(parameters));
		return loginUrl.toString();
	}

	public static JSONObject getTokenObject(String code, String appKey, String appSecret, String returnUrl, String requestUrl) throws IOException, ThirdPartyErrorReturnException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(TaobaoKey.KEY_GRANT_TYPE, TaobaoKey.VALUE_GRANT_TYPE);
		parameters.put(TaobaoKey.KEY_CODE, code);
		parameters.put(TaobaoKey.KEY_CLIENT_ID, appKey);
		parameters.put(TaobaoKey.KEY_CLIENT_SECRET, appSecret);
		parameters.put(TaobaoKey.KEY_REDIRECT_URI, returnUrl);
		String tokenResponse = WebUtils.doPost(requestUrl, parameters, 30000, 30000);
		JSONObject tokenObject = JSONObject.fromObject(tokenResponse);
		if (tokenObject.has("error")) {
			throw new ThirdPartyErrorReturnException("get taobao access token error,tokenResponse is :" + tokenResponse);
		}
		return tokenObject;
	}

	public static String getHeadPictureUrl(String requestUrl, String appKey, String appSecret, String token) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(requestUrl, appKey, appSecret);
		UserBuyerGetRequest req = new UserBuyerGetRequest();
		req.setFields(TaobaoKey.HEAD_URL_KEY);
		UserBuyerGetResponse response = client.execute(req, token);
		if (response.isSuccess()) {
			return response.getUser().getAvatar();
		}
		return null;
	}

}
