package com.koudai.udc.action;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.ProductRemindService;
import com.koudai.udc.utils.CurrencyUtil;
import com.koudai.udc.utils.PriceRemindKey;
import com.koudai.udc.utils.ProductCollectBijiaKey;
import com.koudai.udc.utils.ProductRemindKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadProductCollectPriceRemindAction extends BijiaProductAction {

	private static final long serialVersionUID = 7363271755027290680L;

	private static final Logger LOGGER = Logger.getLogger(UploadProductCollectPriceRemindAction.class);

	private String productRemindIn;

	private ProductRemindService productRemindService;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(productRemindIn)) {
			throw new IncorrectInputParameterException("upload_productcollect_priceremind_in is null or empty");
		}
		LOGGER.info("Upload product remind request is : " + productRemindIn);
		JSONObject content = new JSONObject(productRemindIn);
		JSONArray inArray = content.getJSONArray(ProductRemindKey.CONTENT_KEY);
		Set<Boolean> allCollected = new HashSet<Boolean>();
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			JSONObject productObject = singleObject.getJSONObject(ProductRemindKey.PRODUCT_CONTENT_KEY);
			final String productId = productObject.optString(ProductCollectBijiaKey.PRODUCT_ID, null);
			final String machineId = productObject.optString(ProductCollectBijiaKey.MACHINE_ID, null);
			final String networkId = productObject.optString(ProductCollectBijiaKey.NETWORK_ID, null);
			final String userId = productObject.optString(ProductCollectBijiaKey.USER_ID, null);
			final String softwareName = productObject.optString(ProductCollectBijiaKey.SOFTWARE_NAME, null);
			final String softwareVersion = productObject.optString(ProductCollectBijiaKey.SOFTWARE_VERSION, null);
			final String firmWareVersion = productObject.optString(ProductCollectBijiaKey.FIRMWARE_VERSION, null);
			final String referId = productObject.optString(ProductCollectBijiaKey.REFER_ID, null);

			JSONObject remindObject = singleObject.getJSONObject(ProductRemindKey.REMIND_CONTENT_KEY);
			BigDecimal subscribePrice = CurrencyUtil.transfer(remindObject.optString(PriceRemindKey.SUBSCRIBE_PRICE, S.ZERO));
			final String productUrl = remindObject.optString(PriceRemindKey.PRODUCT_URL, null);
			BigDecimal targetPrice = CurrencyUtil.transfer(remindObject.optString(PriceRemindKey.TARGET_PRICE, S.ZERO));
			int noticeType = remindObject.optInt(PriceRemindKey.NOTICE_TYPE);
			final String email = remindObject.optString(PriceRemindKey.EMAIL, null);

			if (S.isInvalidValue(productId) || S.BIJIA_ERROR_STR.contains(productId.trim())) {
				continue;
			}
			allCollected.add(productRemindService.collectProductAndRemindPrice(productId, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId, subscribePrice, productUrl, targetPrice, noticeType, email));
		}
		result = getSuccessfulUploadResult(allCollected);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadProductCollectPriceRemind cost>>>" + costTime);
	}

	public void setUpload_productcollect_priceremind_in(String upload_productcollect_priceremind_in) {
		this.productRemindIn = upload_productcollect_priceremind_in;
	}

	public void setProductRemindService(ProductRemindService productRemindService) {
		this.productRemindService = productRemindService;
	}

}
