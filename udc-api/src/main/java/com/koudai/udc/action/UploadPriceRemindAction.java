package com.koudai.udc.action;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.ProductRemindService;
import com.koudai.udc.utils.CurrencyUtil;
import com.koudai.udc.utils.PriceRemindKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadPriceRemindAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 6211701504213568122L;

	private static final Logger LOGGER = Logger.getLogger(UploadPriceRemindAction.class);

	private String priceRemindIn;

	private ProductRemindService productRemindService;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(priceRemindIn)) {
			throw new IncorrectInputParameterException("upload_priceremind_in is null or empty");
		}
		LOGGER.info("Upload price remind request is : " + priceRemindIn);
		JSONObject content = new JSONObject(priceRemindIn);
		JSONArray inArray = content.getJSONArray(PriceRemindKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(PriceRemindKey.PRODUCT_ID, null);
			final String userId = singleObject.optString(PriceRemindKey.USER_ID, null);
			BigDecimal subscribePrice = CurrencyUtil.transfer(singleObject.optString(PriceRemindKey.SUBSCRIBE_PRICE, S.ZERO));
			final String productUrl = singleObject.optString(PriceRemindKey.PRODUCT_URL, null);
			BigDecimal targetPrice = CurrencyUtil.transfer(singleObject.optString(PriceRemindKey.TARGET_PRICE, S.ZERO));
			int subscribeType = singleObject.optInt(PriceRemindKey.SUBSCRIBE_TYPE);
			int noticeType = singleObject.optInt(PriceRemindKey.NOTICE_TYPE);
			final String email = singleObject.optString(PriceRemindKey.EMAIL, null);
			if (S.isInvalidValue(productId) || S.BIJIA_ERROR_STR.contains(productId.trim())) {
				continue;
			}
			productRemindService.remindPrice(productId, userId, subscribePrice, productUrl, targetPrice, subscribeType, noticeType, email);
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadPriceRemind cost>>>" + costTime);
	}

	public void setUpload_priceremind_in(String upload_priceremind_in) {
		this.priceRemindIn = upload_priceremind_in;
	}

	public void setProductRemindService(ProductRemindService productRemindService) {
		this.productRemindService = productRemindService;
	}

}
