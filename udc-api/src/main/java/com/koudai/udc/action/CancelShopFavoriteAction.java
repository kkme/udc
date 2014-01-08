package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ShopFavorite;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ShopFavoriteDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.ShopFavoriteKey;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class CancelShopFavoriteAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -8883034257813684031L;

	private static final Logger LOGGER = Logger.getLogger(CancelShopFavoriteAction.class);

	private String shopFavoriteIn;

	private ShopFavoriteDAO shopFavoriteDAOW;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(shopFavoriteIn)) {
			throw new IncorrectInputParameterException("cancel_shoppingfavorite_in is null or empty");
		}
		LOGGER.info("Cancel shop favorite request is : " + shopFavoriteIn);
		JSONObject content = new JSONObject(shopFavoriteIn);
		JSONArray inArray = content.getJSONArray(ShopFavoriteKey.CANCEL_CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String shopId = singleObject.optString(ShopFavoriteKey.SHOP_ID, null);
			final String userId = singleObject.optString(ShopFavoriteKey.USER_ID, null);
			if (S.isInvalidValue(shopId)) {
				LOGGER.error("Shop id is null or empty when canceling shop");
			}
			if (S.isInvalidValue(userId)) {
				LOGGER.error("User id is null or empty when canceling shop");
			}
			ShopFavorite shopFavorite = shopFavoriteDAOW.getShopFavoriteByUserAndShopId(userId, shopId);
			if (shopFavorite != null) {
				shopFavorite.cancel();
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("cancelShopFavorite cost>>>" + costTime);
	}

	public void setCancel_shoppingfavorite_in(String cancel_shoppingfavorite_in) {
		this.shopFavoriteIn = cancel_shoppingfavorite_in;
	}

	public void setShopFavoriteDAOW(ShopFavoriteDAO shopFavoriteDAOW) {
		this.shopFavoriteDAOW = shopFavoriteDAOW;
	}

}
