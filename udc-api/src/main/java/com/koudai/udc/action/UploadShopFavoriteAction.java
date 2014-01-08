package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.ShopFavoriteKey;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadShopFavoriteAction extends ShopFavoriteAction {

	private static final long serialVersionUID = 7890678420220433864L;

	private static final Logger LOGGER = Logger.getLogger(UploadShopFavoriteAction.class);

	private String shopFavoriteIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(shopFavoriteIn)) {
			throw new IncorrectInputParameterException("upload_shopfavorite_in is null or empty");
		}
		LOGGER.info("Upload shop favorite request is : " + shopFavoriteIn);
		JSONObject content = new JSONObject(shopFavoriteIn);
		JSONArray inArray = content.getJSONArray(ShopFavoriteKey.UPLOAD_CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String shopId = singleObject.optString(ShopFavoriteKey.SHOP_ID, null);
			final String machineId = singleObject.optString(ShopFavoriteKey.MACHINE_ID, null);
			final String userId = singleObject.optString(ShopFavoriteKey.USER_ID, null);
			final String networkId = singleObject.optString(ShopFavoriteKey.NETWORK_ID, null);
			final String softwareVersion = singleObject.optString(ShopFavoriteKey.SOFTWARE_VERSION, null);
			final String softwareName = singleObject.optString(ShopFavoriteKey.SOFTWARE_NAME, null);
			final String firmWareVersion = singleObject.optString(ShopFavoriteKey.FIRMWARE_VERSION, null);
			final String referId = singleObject.optString(ShopFavoriteKey.REFER_ID, null);
			if (S.isInvalidValue(shopId) || S.isInvalidValue(userId)) {
				continue;
			}
			favoriteAndCount(userId, shopId, machineId, networkId, softwareName, softwareVersion, firmWareVersion, referId);
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadShopFavorite cost>>>" + costTime);
	}

	public void setUpload_shopfavorite_in(String upload_shopfavorite_in) {
		this.shopFavoriteIn = upload_shopfavorite_in;
	}

}
