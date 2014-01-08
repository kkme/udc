package com.koudai.udc.action;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

class DealWithAnonymousDataTransactionCallback implements TransactionCallback<Boolean> {

	private UploadBindingInfoV3Action action;

	private String userId;

	private String machineId;

	public DealWithAnonymousDataTransactionCallback(UploadBindingInfoV3Action action, String userId, String machineId) {
		this.action = action;
		this.userId = userId;
		this.machineId = machineId;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			action.getUserDataService().dealWithAnonymousData(userId, machineId);
		} catch (Exception e) {
			action.logErrorAndSetRollbackOnly(ts, e);
		}
		return ts.isRollbackOnly();
	}

}
