package com.koudai.udc.action;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

class SaveTaobaoProductTransactionCallback implements TransactionCallback<Boolean> {

	private UploadTaobaoProductCollectAction action;

	private String userId;

	private String productId;

	private String productName;

	private String ownerNick;

	public SaveTaobaoProductTransactionCallback(UploadTaobaoProductCollectAction action, String userId, String productId, String productName, String ownerNick) {
		this.action = action;
		this.userId = userId;
		this.productId = productId;
		this.productName = productName;
		this.ownerNick = ownerNick;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			action.getProductCollectTaobaoDAOW().save(action.getProductCollectTaobaoFactory().newInstance(userId, productId, productName, ownerNick));
		} catch (Exception e) {
			action.logErrorAndSetRollbackOnly(ts, e);
		}
		return ts.isRollbackOnly();
	}

}
