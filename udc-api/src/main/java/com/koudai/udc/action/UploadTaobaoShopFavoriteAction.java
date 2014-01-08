package com.koudai.udc.action;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

import com.koudai.udc.domain.factory.ShopFavoriteTaobaoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ShopFavoriteTaobaoDAO;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.ShopFavoriteTaobaoKey;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadTaobaoShopFavoriteAction extends JsonResultWithoutTransactionAction {

	private static final long serialVersionUID = 3771365439280151865L;

	private static final Logger LOGGER = Logger.getLogger(UploadTaobaoShopFavoriteAction.class);

	private ShopFavoriteTaobaoDAO shopFavoriteTaobaoDAOW;

	private ShopFavoriteTaobaoFactory shopFavoriteTaobaoFactory;

	private String taobaoShopIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(taobaoShopIn)) {
			throw new IncorrectInputParameterException("taobao_shop_in is null or empty");
		}
		JSONObject content = new JSONObject(taobaoShopIn);
		JSONArray inArray = content.getJSONArray(ShopFavoriteTaobaoKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String userId = singleObject.optString(ShopFavoriteTaobaoKey.USER_ID, null);
			final String shopId = singleObject.optString(ShopFavoriteTaobaoKey.SHOP_ID, null);
			final String shopName = singleObject.optString(ShopFavoriteTaobaoKey.SHOP_NAME, null);
			final String ownerNick = singleObject.optString(ShopFavoriteTaobaoKey.OWNER_NICK, null);
			if (S.isInvalidValue(userId) || !S.isTaobaoUser(userId) || S.isInvalidValue(shopId) || S.isInvalidValue(shopName) || S.isInvalidValue(ownerNick)) {
				continue;
			}
			if (shopFavoriteTaobaoDAOW.getShopByUserIdAndShopId(userId, shopId) != null) {
				continue;
			}
			Boolean isFailed = transactionTemplate.execute(new SaveTaobaoShopTransactionCallback(this, userId, shopId, shopName, ownerNick));
			if (isFailed) {
				LOGGER.error("Save user taobao shop failed with userId < " + userId + " > and shopId < " + shopId + " > and shopName < " + shopName + " > and ownerNick < " + ownerNick + " >");
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadTaobaoShopFavorite cost>>>" + costTime);
	}

	protected void logErrorAndSetRollbackOnly(TransactionStatus ts, Exception e) {
		ts.setRollbackOnly();
		LOGGER.error(e);
	}

	public ShopFavoriteTaobaoDAO getShopFavoriteTaobaoDAOW() {
		return shopFavoriteTaobaoDAOW;
	}

	public void setShopFavoriteTaobaoDAOW(ShopFavoriteTaobaoDAO shopFavoriteTaobaoDAOW) {
		this.shopFavoriteTaobaoDAOW = shopFavoriteTaobaoDAOW;
	}

	public ShopFavoriteTaobaoFactory getShopFavoriteTaobaoFactory() {
		return shopFavoriteTaobaoFactory;
	}

	public void setShopFavoriteTaobaoFactory(ShopFavoriteTaobaoFactory shopFavoriteTaobaoFactory) {
		this.shopFavoriteTaobaoFactory = shopFavoriteTaobaoFactory;
	}

	public void setTaobao_shop_in(String taobao_shop_in) {
		this.taobaoShopIn = taobao_shop_in;
	}

}
