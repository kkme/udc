package com.koudai.udc.action;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

class NewBindingInfoV2TransactionCallback implements TransactionCallback<Boolean> {

	private UploadBindingInfoV3Action action;

	private String userId;

	private String machineId;

	public NewBindingInfoV2TransactionCallback(UploadBindingInfoV3Action action, String userId, String machineId) {
		this.action = action;
		this.userId = userId;
		this.machineId = machineId;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			action.getBindingInfoDAOW().save(action.getBindingInfoFactory().newInstance(machineId, userId));
		} catch (Exception e) {
			action.logErrorAndSetRollbackOnly(ts, e);
		}
		return ts.isRollbackOnly();
	}

}
