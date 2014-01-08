package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ShopFavoritedCountDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.ShopCountKey;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class GetShopIDFavoritedCountAction extends CountAction {

	private static final long serialVersionUID = 8109396917386936716L;

	private static final Logger LOGGER = Logger.getLogger(GetShopIDFavoritedCountAction.class);

	private String shopIdIn;

	private ShopFavoritedCountDAO shopFavoritedCountDAOR;

	@Override
	protected void verifyInputParameters() throws Exception {
		if (S.isInvalidValue(shopIdIn)) {
			throw new IncorrectInputParameterException("get_shopID_favoritedcount_in is null or empty");
		}
	}

	@Override
	protected Map<String, Integer> getItemCountMap() throws Exception {
		long beginTime = System.currentTimeMillis();
		Map<String, Integer> itemCountMap = new HashMap<String, Integer>();
		JSONObject content = new JSONObject(shopIdIn);
		JSONArray inArray = content.getJSONArray(ShopCountKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String shopId = singleObject.optString(ShopCountKey.SHOP_ID, null);
			if (S.isInvalidValue(shopId)) {
				continue;
			}
			int count = shopFavoritedCountDAOR.getFavoritedCountByShopId(shopId);
			itemCountMap.put(shopId, count);
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("getShopIDFavoritedCount cost>>>" + costTime);
		return itemCountMap;
	}

	@Override
	protected JSONObject getCountResult(int code, String reason, Map<String, Integer> itemCountMap) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray countArray = new JSONArray();
			Iterator<Entry<String, Integer>> it = itemCountMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Integer> entry = it.next();
				JSONObject countObject = new JSONObject();
				countObject.put("shopID", entry.getKey());
				countObject.put("favoritedCount", entry.getValue());
				countArray.put(countObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("favoritedcount", countArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setGet_shopID_favoritedcount_in(String get_shopID_favoritedcount_in) {
		this.shopIdIn = get_shopID_favoritedcount_in;
	}

	public void setShopFavoritedCountDAOR(ShopFavoritedCountDAO shopFavoritedCountDAOR) {
		this.shopFavoritedCountDAOR = shopFavoritedCountDAOR;
	}

}
