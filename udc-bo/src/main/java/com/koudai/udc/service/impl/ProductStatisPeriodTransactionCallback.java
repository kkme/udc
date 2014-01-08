package com.koudai.udc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.koudai.udc.statis.domain.ProductStatisPeriod;

class ProductStatisPeriodTransactionCallback implements TransactionCallback<Boolean> {

	private static final Logger LOGGER = Logger.getLogger(ProductStatisPeriodTransactionCallback.class);

	private ProductStatisServiceImpl impl;

	private Map<String, ProductStatisPeriod> productStatisPeriodMap;

	public ProductStatisPeriodTransactionCallback(ProductStatisServiceImpl impl, Map<String, ProductStatisPeriod> productStatisPeriodMap) {
		this.impl = impl;
		this.productStatisPeriodMap = productStatisPeriodMap;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			List<ProductStatisPeriod> productStatisPeriods = new ArrayList<ProductStatisPeriod>(productStatisPeriodMap.values());
			impl.getProductStatisPeriodDAO().batchSave(productStatisPeriods);
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
