package com.koudai.udc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.koudai.udc.statis.domain.ProductStatis;

class SaveProductStatisTransactionCallback implements TransactionCallback<Boolean> {

	private static final Logger LOGGER = Logger.getLogger(SaveProductStatisTransactionCallback.class);

	private EditorServiceimpl impl;

	private List<String> productIds;

	public SaveProductStatisTransactionCallback(EditorServiceimpl impl, List<String> productIds) {
		this.impl = impl;
		this.productIds = productIds;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			List<ProductStatis> productStatisList = new ArrayList<ProductStatis>();
			for (String productId : productIds) {
				productStatisList.add(impl.getProductStatisFactory().newInstance(productId));
			}
			impl.getProductStatisDAO().batchSave(productStatisList);
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
