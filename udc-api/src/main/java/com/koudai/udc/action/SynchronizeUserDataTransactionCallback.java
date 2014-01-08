package com.koudai.udc.action;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

class SynchronizeUserDataTransactionCallback implements TransactionCallback<Boolean> {

	private UploadBindingInfoV2Action action;

	private String mainId;

	private String subId;

	private String machineId;

	public SynchronizeUserDataTransactionCallback(UploadBindingInfoV2Action action, String mainId, String subId, String machineId) {
		this.action = action;
		this.mainId = mainId;
		this.subId = subId;
		this.machineId = machineId;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			action.getUserDataService().synchronize(mainId, subId, machineId);
		} catch (Exception e) {
			action.logErrorAndSetRollbackOnly(ts, e);
		}
		return ts.isRollbackOnly();
	}

}
