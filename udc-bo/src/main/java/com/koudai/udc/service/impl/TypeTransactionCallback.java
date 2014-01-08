package com.koudai.udc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.koudai.udc.statis.domain.ProductType;

class TypeTransactionCallback implements TransactionCallback<Boolean> {

	private static final Logger LOGGER = Logger.getLogger(TypeTransactionCallback.class);

	private EditorServiceimpl impl;

	private List<ProductType> saveTypes;

	private List<ProductType> updateTypes;

	public TypeTransactionCallback(EditorServiceimpl impl, List<ProductType> saveTypes, List<ProductType> updateTypes) {
		this.impl = impl;
		this.saveTypes = saveTypes;
		this.updateTypes = updateTypes;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			impl.getProductTypeDAO().batchDelete(updateTypes);
			saveTypes.addAll(updateTypes);
			impl.getProductTypeDAO().batchSave(saveTypes);
		} catch (Exception e) {
			logErrorAndSetRollbackOnly(ts, e);
		}
		return ts.isRollbackOnly();
	}

	private void logErrorAndSetRollbackOnly(TransactionStatus ts, Exception e) {
		ts.setRollbackOnly();
		LOGGER.error(e);
	}

}
