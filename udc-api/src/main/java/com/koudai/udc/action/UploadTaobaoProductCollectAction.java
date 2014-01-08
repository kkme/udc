package com.koudai.udc.action;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;

import com.koudai.udc.domain.factory.ProductCollectTaobaoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectTaobaoDAO;
import com.koudai.udc.utils.ProductCollectTaobaoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadTaobaoProductCollectAction extends JsonResultWithoutTransactionAction {

	private static final long serialVersionUID = -4999172578415229369L;

	private static final Logger LOGGER = Logger.getLogger(UploadTaobaoProductCollectAction.class);

	private ProductCollectTaobaoDAO productCollectTaobaoDAOW;

	private ProductCollectTaobaoFactory productCollectTaobaoFactory;

	private String taobaoProductIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(taobaoProductIn)) {
			throw new IncorrectInputParameterException("taobao_product_in is null or empty");
		}
		JSONObject content = new JSONObject(taobaoProductIn);
		JSONArray inArray = content.getJSONArray(ProductCollectTaobaoKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String userId = singleObject.optString(ProductCollectTaobaoKey.USER_ID, null);
			final String productId = singleObject.optString(ProductCollectTaobaoKey.PRODUCT_ID, null);
			final String productName = singleObject.optString(ProductCollectTaobaoKey.PRODUCT_NAME, null);
			final String ownerNick = singleObject.optString(ProductCollectTaobaoKey.OWNER_NICK, null);
			if (S.isInvalidValue(userId) || !S.isTaobaoUser(userId) || S.isInvalidValue(productId) || S.isInvalidValue(productName) || S.isInvalidValue(ownerNick)) {
				continue;
			}
			if (productCollectTaobaoDAOW.getProductByUserIdAndProductId(userId, productId) != null) {
				continue;
			}
			Boolean isFailed = transactionTemplate.execute(new SaveTaobaoProductTransactionCallback(this, userId, productId, productName, ownerNick));
			if (isFailed) {
				LOGGER.error("Save user taobao product failed with userId < " + userId + " > and productId < " + productId + " > and productName < " + productName + " > and ownerNick < " + ownerNick + " >");
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadTaobaoProductCollect cost>>>" + costTime);
	}

	protected void logErrorAndSetRollbackOnly(TransactionStatus ts, Exception e) {
		ts.setRollbackOnly();
		LOGGER.error(e);
	}

	public ProductCollectTaobaoDAO getProductCollectTaobaoDAOW() {
		return productCollectTaobaoDAOW;
	}

	public void setProductCollectTaobaoDAOW(ProductCollectTaobaoDAO productCollectTaobaoDAOW) {
		this.productCollectTaobaoDAOW = productCollectTaobaoDAOW;
	}

	public ProductCollectTaobaoFactory getProductCollectTaobaoFactory() {
		return productCollectTaobaoFactory;
	}

	public void setProductCollectTaobaoFactory(ProductCollectTaobaoFactory productCollectTaobaoFactory) {
		this.productCollectTaobaoFactory = productCollectTaobaoFactory;
	}

	public void setTaobao_product_in(String taobao_product_in) {
		this.taobaoProductIn = taobao_product_in;
	}

}
