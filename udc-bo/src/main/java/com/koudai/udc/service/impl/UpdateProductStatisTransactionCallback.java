package com.koudai.udc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.koudai.udc.statis.domain.ProductStatis;

class UpdateProductStatisTransactionCallback implements TransactionCallback<Boolean> {

	private static final Logger LOGGER = Logger.getLogger(UpdateProductStatisTransactionCallback.class);

	private ProductStatisServiceImpl impl;

	private Map<String, ProductStatis> productStatisMap;

	public UpdateProductStatisTransactionCallback(ProductStatisServiceImpl impl, Map<String, ProductStatis> productStatisMap) {
		this.impl = impl;
		this.productStatisMap = productStatisMap;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			List<ProductStatis> productStatisList = new ArrayList<ProductStatis>(productStatisMap.values());
			impl.getProductStatisDAO().batchUpdate(productStatisList);
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
