package com.koudai.udc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.koudai.udc.statis.domain.ProductRecommend;

class RecommendTransactionCallback implements TransactionCallback<Boolean> {

	private static final Logger LOGGER = Logger.getLogger(RecommendTransactionCallback.class);

	private EditorServiceimpl impl;

	private List<ProductRecommend> saveRecommends;

	private List<ProductRecommend> updateRecommends;

	public RecommendTransactionCallback(EditorServiceimpl impl, List<ProductRecommend> saveRecommends, List<ProductRecommend> updateRecommends) {
		this.impl = impl;
		this.saveRecommends = saveRecommends;
		this.updateRecommends = updateRecommends;
	}

	@Override
	public Boolean doInTransaction(TransactionStatus ts) {
		try {
			impl.getProductRecommendDAO().batchSave(saveRecommends);
			impl.getProductRecommendDAO().batchUpdate(updateRecommends);
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
