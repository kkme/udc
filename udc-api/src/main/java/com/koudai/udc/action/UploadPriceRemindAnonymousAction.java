package com.koudai.udc.action;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.PriceRemindAnonymous;
import com.koudai.udc.domain.factory.PriceRemindAnonymousFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.PriceRemindAnonymousDAO;
import com.koudai.udc.utils.CurrencyUtil;
import com.koudai.udc.utils.PriceRemindAnonymousKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadPriceRemindAnonymousAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -6778959396195615868L;

	private static final Logger LOGGER = Logger.getLogger(UploadPriceRemindAnonymousAction.class);

	private String priceRemindAnonymousIn;

	private PriceRemindAnonymousDAO priceRemindAnonymousDAOW;

	private PriceRemindAnonymousFactory priceRemindAnonymousFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(priceRemindAnonymousIn)) {
			throw new IncorrectInputParameterException("upload_priceremind_anonymous_in is null or empty");
		}
		LOGGER.info("Upload anonymous price remind request is : " + priceRemindAnonymousIn);
		JSONObject content = new JSONObject(priceRemindAnonymousIn);
		JSONArray inArray = content.getJSONArray(PriceRemindAnonymousKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(PriceRemindAnonymousKey.PRODUCT_ID, null);
			final String email = singleObject.optString(PriceRemindAnonymousKey.EMAIL, null);
			BigDecimal subscribePrice = CurrencyUtil.transfer(singleObject.optString(PriceRemindAnonymousKey.SUBSCRIBE_PRICE, S.ZERO));
			final String productUrl = singleObject.optString(PriceRemindAnonymousKey.PRODUCT_URL, null);
			BigDecimal targetPrice = CurrencyUtil.transfer(singleObject.optString(PriceRemindAnonymousKey.TARGET_PRICE, S.ZERO));
			if (S.isInvalidValue(productId) || S.BIJIA_ERROR_STR.contains(productId.trim())) {
				continue;
			}
			PriceRemindAnonymous remind = priceRemindAnonymousDAOW.getPriceRemindAnonymousByEmailAndProductId(email, productId);
			if (remind != null) {
				remind.update(subscribePrice, productUrl, targetPrice);
			} else {
				priceRemindAnonymousDAOW.save(priceRemindAnonymousFactory.newInstance(productId, email, subscribePrice, productUrl, targetPrice));
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadPriceRemindAnonymous cost>>>" + costTime);
	}

	public void setUpload_priceremind_anonymous_in(String upload_priceremind_anonymous_in) {
		this.priceRemindAnonymousIn = upload_priceremind_anonymous_in;
	}

	public void setPriceRemindAnonymousDAOW(PriceRemindAnonymousDAO priceRemindAnonymousDAOW) {
		this.priceRemindAnonymousDAOW = priceRemindAnonymousDAOW;
	}

	public void setPriceRemindAnonymousFactory(PriceRemindAnonymousFactory priceRemindAnonymousFactory) {
		this.priceRemindAnonymousFactory = priceRemindAnonymousFactory;
	}

}
