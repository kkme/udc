package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.ProductRemindService;
import com.koudai.udc.utils.PriceRemindKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class CancelPriceRemindAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -3015245999474314230L;

	private static final Logger LOGGER = Logger.getLogger(CancelPriceRemindAction.class);

	private String priceRemindIn;

	private ProductRemindService productRemindService;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(priceRemindIn)) {
			throw new IncorrectInputParameterException("cancel_priceremind_in is null or empty");
		}
		LOGGER.info("Cancel price remind request is : " + priceRemindIn);
		JSONObject content = new JSONObject(priceRemindIn);
		JSONArray inArray = content.getJSONArray(PriceRemindKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(PriceRemindKey.PRODUCT_ID, null);
			final String userId = singleObject.optString(PriceRemindKey.USER_ID, null);
			productRemindService.cancelRemind(productId, userId);
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("cancelPriceRemind cost>>>" + costTime);
	}

	public void setCancel_priceremind_in(String cancel_priceremind_in) {
		this.priceRemindIn = cancel_priceremind_in;
	}

	public void setProductRemindService(ProductRemindService productRemindService) {
		this.productRemindService = productRemindService;
	}

}
