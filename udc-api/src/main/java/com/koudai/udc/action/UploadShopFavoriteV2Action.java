package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.ShopFavoriteKey;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadShopFavoriteV2Action extends ShopFavoriteAction {

	private static final long serialVersionUID = 8873943276497485188L;

	private static final Logger LOGGER = Logger.getLogger(UploadShopFavoriteV2Action.class);

	private String shopFavoriteIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(shopFavoriteIn)) {
			throw new IncorrectInputParameterException("upload_shopfavorite_in of version 2 is null or empty");
		}
		LOGGER.info("Upload shop favorite request of version 2 is : " + shopFavoriteIn);
		JSONObject content = new JSONObject(shopFavoriteIn);
		JSONArray inArray = content.getJSONArray(ShopFavoriteKey.UPLOAD_CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String shopId = singleObject.optString(ShopFavoriteKey.SHOP_ID, null);
			final String machineId = singleObject.optString(ShopFavoriteKey.MACHINE_ID, null);
			final String userIdReal = singleObject.optString(ShopFavoriteKey.USER_ID_REAL, null);
			final String userIdAnonymous = singleObject.optString(ShopFavoriteKey.USER_ID_ANONYMOUS, null);
			final String networkId = singleObject.optString(ShopFavoriteKey.NETWORK_ID, null);
			final String softwareVersion = singleObject.optString(ShopFavoriteKey.SOFTWARE_VERSION, null);
			final String softwareName = singleObject.optString(ShopFavoriteKey.SOFTWARE_NAME, null);
			final String firmWareVersion = singleObject.optString(ShopFavoriteKey.FIRMWARE_VERSION, null);
			final String referId = singleObject.optString(ShopFavoriteKey.REFER_ID, null);
			if (S.isInvalidValue(shopId)) {
				continue;
			}
			if (!S.isInvalidValue(userIdAnonymous) && S.isAnonymousUser(userIdAnonymous)) {
				favoriteAndCount(userIdAnonymous, shopId, machineId, networkId, softwareName, softwareVersion, firmWareVersion, referId);
			}
			if (!S.isInvalidValue(userIdReal) && S.isTaobaoUser(userIdReal)) {
				favoriteAndCount(userIdReal, shopId, machineId, networkId, softwareName, softwareVersion, firmWareVersion, referId);
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadShopFavorite2 cost>>>" + costTime);
	}

	public void setUpload_shopfavorite_in(String upload_shopfavorite_in) {
		this.shopFavoriteIn = upload_shopfavorite_in;
	}

}
