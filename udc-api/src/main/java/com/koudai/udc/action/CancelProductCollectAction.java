package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ProductCollect;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectDAO;
import com.koudai.udc.utils.ProductCollectKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class CancelProductCollectAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 3006673959356888701L;

	private static final Logger LOGGER = Logger.getLogger(CancelProductCollectAction.class);

	private String productCollectIn;

	private ProductCollectDAO productCollectDAOW;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(productCollectIn)) {
			throw new IncorrectInputParameterException("cancel_productcollect_in is null or empty");
		}
		LOGGER.info("Cancel product collect request is : " + productCollectIn);
		JSONObject content = new JSONObject(productCollectIn);
		JSONArray inArray = content.getJSONArray(ProductCollectKey.CANCEL_CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(ProductCollectKey.PRODUCT_ID, null);
			final String userId = singleObject.optString(ProductCollectKey.USER_ID, null);
			if (S.isInvalidValue(productId)) {
				LOGGER.error("Product id is null or empty when canceling product");
			}
			if (S.isInvalidValue(userId)) {
				LOGGER.error("User id is null or empty when canceling product");
			}
			ProductCollect productCollect = productCollectDAOW.getProductCollectByUserAndProductId(userId, productId);
			if (productCollect != null) {
				productCollect.cancel();
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("cancelProductCollect cost>>>" + costTime);
	}

	public void setCancel_productcollect_in(String cancel_productcollect_in) {
		this.productCollectIn = cancel_productcollect_in;
	}

	public void setProductCollectDAOW(ProductCollectDAO productCollectDAOW) {
		this.productCollectDAOW = productCollectDAOW;
	}

}
