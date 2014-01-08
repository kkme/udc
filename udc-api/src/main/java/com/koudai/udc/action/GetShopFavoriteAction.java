package com.koudai.udc.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.persistence.ShopFavoriteDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class GetShopFavoriteAction extends SortLimitAction {

	private static final long serialVersionUID = -8166655594753186614L;

	private static final Logger LOGGER = Logger.getLogger(GetShopFavoriteAction.class);

	private ShopFavoriteDAO shopFavoriteDAOR;

	@Override
	protected void getItemIdsByStartAndEndPos() throws Exception {
		long beginTime = System.currentTimeMillis();
		LOGGER.info("User < " + userId + " > request shops with position from " + start + " to " + end);
		List<String> shopIds = getLimitSortedShopIds();
		result = getSortLimitResult(S.SUCCESS_CODE, S.EMPTY_STR, shopIds.size(), shopIds);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("getShopFavorite cost>>>" + costTime);
	}

	private List<String> getLimitSortedShopIds() {
		if (start == 0 && end == 0) {
			return shopFavoriteDAOR.getAllShopIdsByUserIdOrderByTime(userId);
		}
		return shopFavoriteDAOR.getShopIdsByUserIdAndStartAndEndPosOrderByTime(userId, start, end);
	}

	@Override
	protected JSONObject getSortLimitResult(int code, String reason, int num, List<String> itemIds) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			statusObject.put("shop_num", num);
			JSONArray idArray = new JSONArray();
			for (String itemId : itemIds) {
				JSONObject idObject = new JSONObject();
				idObject.put("shopID", itemId);
				idArray.put(idObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("shopfavorite", idArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setShopFavoriteDAOR(ShopFavoriteDAO shopFavoriteDAOR) {
		this.shopFavoriteDAOR = shopFavoriteDAOR;
	}

}
