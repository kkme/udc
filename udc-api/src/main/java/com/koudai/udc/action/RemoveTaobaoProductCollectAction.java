package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectTaobaoDAO;
import com.koudai.udc.utils.S;

public class RemoveTaobaoProductCollectAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -3868416289588130323L;

	private static final Logger LOGGER = Logger.getLogger(RemoveTaobaoProductCollectAction.class);

	private String userId;

	private ProductCollectTaobaoDAO productCollectTaobaoDAOW;

	@Override
	protected void doExecute() throws Exception {
		if (S.isInvalidValue(userId) || !S.isTaobaoUser(userId)) {
			throw new IncorrectInputParameterException("User id is invalid");
		}
		long beginTime = System.currentTimeMillis();
		productCollectTaobaoDAOW.deleteProductsByUserId(userId);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("removeTaobaoProductCollect cost>>>" + costTime);
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setProductCollectTaobaoDAOW(ProductCollectTaobaoDAO productCollectTaobaoDAOW) {
		this.productCollectTaobaoDAOW = productCollectTaobaoDAOW;
	}

}
