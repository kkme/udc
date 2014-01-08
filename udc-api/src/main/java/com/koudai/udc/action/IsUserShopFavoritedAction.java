package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ShopFavoriteDAO;
import com.koudai.udc.utils.N;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.ShopFavoriteKey;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class IsUserShopFavoritedAction extends OwnAction {

	private static final long serialVersionUID = 4032542213833497914L;

	private static final Logger LOGGER = Logger.getLogger(IsUserShopFavoritedAction.class);

	private String shopFavoritedIn;

	private ShopFavoriteDAO shopFavoriteDAOR;

	@Override
	protected void doExecute() throws Exception {
		if (S.isInvalidValue(shopFavoritedIn)) {
			throw new IncorrectInputParameterException("is_user_shop_favorited_in is null or empty");
		}
		long beginTime = System.currentTimeMillis();
		JSONObject content = new JSONObject(shopFavoritedIn);
		final String userId = content.optString(ShopFavoriteKey.OWN_USER_KEY, null);
		if (S.isInvalidValue(userId)) {
			throw new IncorrectInputParameterException("User id is null or empty");
		}
		List<String> ownerList = shopFavoriteDAOR.getAllShopIdsByUserId(userId);
		List<String> sourceList = new ArrayList<String>();
		JSONArray idArray = content.getJSONArray(ShopFavoriteKey.OWN_ID_SET_KEY);
		for (int i = 0; i < idArray.length(); i++) {
			JSONObject singleObject = idArray.getJSONObject(i);
			final String shopId = singleObject.optString(ShopFavoriteKey.OWN_ID_KEY, null);
			if (S.isInvalidValue(shopId)) {
				continue;
			}
			sourceList.add(shopId);
		}
		result = getOwnResult(S.SUCCESS_CODE, S.EMPTY_STR, sourceList, ownerList);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("isUserShopFavorited cost>>>" + costTime);
	}

	@Override
	protected JSONObject getOwnResult(int code, String reason, List<String> sourceList, List<String> ownerList) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray idArray = new JSONArray();
			for (String shopId : sourceList) {
				JSONObject singleObject = new JSONObject();
				singleObject.put("shopID", shopId);
				singleObject.put("isfavorited", N.booleanToInt(ownerList.contains(shopId)));
				idArray.put(singleObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("isfavorited", idArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setIs_user_shop_favorited_in(String is_user_shop_favorited_in) {
		this.shopFavoritedIn = is_user_shop_favorited_in;
	}

	public void setShopFavoriteDAOR(ShopFavoriteDAO shopFavoriteDAOR) {
		this.shopFavoriteDAOR = shopFavoriteDAOR;
	}

}
