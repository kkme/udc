package com.koudai.udc.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.UserCredit;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.request.UserBuyerGetRequest;
import com.taobao.api.response.ShopGetResponse;
import com.taobao.api.response.UserBuyerGetResponse;

public final class TaobaoApiUtil {

	public static String refreshToken(String refreshTokenUrl, String appKey, String appSecret, String refreshToken) throws IOException, IncorrectInputParameterException, JSONException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(UserTaobaoInfoKey.API_GRANT_TYPE, UserTaobaoInfoKey.API_REFRESH_TOKEN);
		parameters.put(UserTaobaoInfoKey.API_REFRESH_TOKEN, refreshToken);
		parameters.put(UserTaobaoInfoKey.API_CLIENT_ID, appKey);
		parameters.put(UserTaobaoInfoKey.API_CLIENT_SECRET, appSecret);
		parameters.put(UserTaobaoInfoKey.API_SCOPE, UserTaobaoInfoKey.API_SCOPE_VALUE);
		parameters.put(UserTaobaoInfoKey.API_VIEW, UserTaobaoInfoKey.API_VIEW_VALUE);
		String reponseStr = WebUtils.doPost(refreshTokenUrl, parameters, S.FIVE_SECONDS, S.FIVE_SECONDS);
		if (S.isInvalidValue(reponseStr)) {
			throw new IncorrectInputParameterException("Refresh token response return is null");
		}
		JSONObject responseObject = new JSONObject(reponseStr);
		String token = responseObject.getString(UserTaobaoInfoKey.API_ACCESS_TOKEN);
		if (token == null) {
			throw new IncorrectInputParameterException("Token return after refreshing is null");
		}
		return token;
	}

	public static String getShopId(String apiUrl, String appKey, String appSecret, String userId) throws ApiException, IncorrectInputParameterException {
		final String nick = userId.replace(S.PREFIX_TAOBAO, S.EMPTY_STR);
		TaobaoClient client = new DefaultTaobaoClient(apiUrl, appKey, appSecret, Constants.FORMAT_JSON, S.FIVE_SECONDS, S.FIVE_SECONDS);
		ShopGetRequest request = new ShopGetRequest();
		request.setFields(UserTaobaoInfoKey.API_SID);
		request.setNick(nick);
		ShopGetResponse response = client.execute(request);
		if (response == null) {
			throw new IncorrectInputParameterException("ShopGetResponse return is null");
		}
		if (response.isSuccess()) {
			Long sid = response.getShop().getSid();
			return String.valueOf(sid);
		}
		throw new IncorrectInputParameterException("User < " + userId + " > request seller shop id failed with error code and message < " + response.getErrorCode() + "," + response.getMsg() + " > and sub code and message < " + response.getSubCode() + "," + response.getSubMsg() + " >");
	}

	public static Long getBuyerCreditLevel(String apiUrl, String appKey, String appSecret, String userId, String token) throws ApiException, IncorrectInputParameterException {
		TaobaoClient client = new DefaultTaobaoClient(apiUrl, appKey, appSecret, Constants.FORMAT_JSON, S.FIVE_SECONDS, S.FIVE_SECONDS);
		UserBuyerGetRequest request = new UserBuyerGetRequest();
		request.setFields(UserTaobaoInfoKey.API_BUYER_CREDIT);
		UserBuyerGetResponse response = client.execute(request, token);
		if (response == null) {
			throw new IncorrectInputParameterException("UserBuyerGetResponse return is null");
		}
		if (response.isSuccess()) {
			UserCredit buyerCredit = response.getUser().getBuyerCredit();
			return buyerCredit.getLevel();
		}
		throw new IncorrectInputParameterException("User < " + userId + " > request buyer credit failed with error code and message < " + response.getErrorCode() + "," + response.getMsg() + " > and sub code and message < " + response.getSubCode() + "," + response.getSubMsg() + " >");
	}

}
