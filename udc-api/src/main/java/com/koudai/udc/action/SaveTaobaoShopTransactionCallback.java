package com.koudai.udc.action;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

class SaveTaobaoShopTransactionCallback implements TransactionCallback<Boolean> {

	private UploadTaobaoShopFavoriteAction action;

	private String userId;

	private String shopId;

	private String shopName;

	private String ownerNick;

	public SaveTaobaoShopTransactionCallback(UploadTaobaoShopFavoriteAction action, String userId, String shopId, String shopName, String ownerNick) {
		this.action = action;
		this.userId = userId;
		this.shopId = shopId;
		this.shopName = shopName;
		this.ownerNick = ownerNick;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			action.getShopFavoriteTaobaoDAOW().save(action.getShopFavoriteTaobaoFactory().newInstance(userId, shopId, shopName, ownerNick));
		} catch (Exception e) {
			action.logErrorAndSetRollbackOnly(ts, e);
		}
		return ts.isRollbackOnly();
	}

}
