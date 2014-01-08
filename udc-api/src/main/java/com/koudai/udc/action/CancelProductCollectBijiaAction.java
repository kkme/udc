package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.ProductRemindService;
import com.koudai.udc.utils.ProductCollectBijiaKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class CancelProductCollectBijiaAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 6576458017454078341L;

	private static final Logger LOGGER = Logger.getLogger(CancelProductCollectBijiaAction.class);

	private String productCollectBijiaIn;

	private ProductRemindService productRemindService;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(productCollectBijiaIn)) {
			throw new IncorrectInputParameterException("cancel_productcollect_bijia_in is null or empty");
		}
		LOGGER.info("Cancel bijia product collect request is : " + productCollectBijiaIn);
		JSONObject content = new JSONObject(productCollectBijiaIn);
		JSONArray inArray = content.getJSONArray(ProductCollectBijiaKey.CANCEL_CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(ProductCollectBijiaKey.PRODUCT_ID, null);
			final String userId = singleObject.optString(ProductCollectBijiaKey.USER_ID, null);
			productRemindService.cancelProduct(productId, userId);
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("cancelProductCollectBijia cost>>>" + costTime);
	}

	public void setCancel_productcollect_bijia_in(String cancel_productcollect_bijia_in) {
		this.productCollectBijiaIn = cancel_productcollect_bijia_in;
	}

	public void setProductRemindService(ProductRemindService productRemindService) {
		this.productRemindService = productRemindService;
	}

}
