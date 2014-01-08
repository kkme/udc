package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.factory.PurchaseRecordFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.PurchaseRecordDAO;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.PurchaseRecordKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadPurchaseRecordAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -2617063450163633914L;

	private static final Logger LOGGER = Logger.getLogger(UploadPurchaseRecordAction.class);

	private PurchaseRecordDAO purchaseRecordDAOW;

	private PurchaseRecordFactory purchaseRecordFactory;

	private String purchaseRecordIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(purchaseRecordIn)) {
			throw new IncorrectInputParameterException("purchase_record_in is null or empty");
		}
		LOGGER.info("Upload purchase record request is : " + purchaseRecordIn);
		JSONObject content = new JSONObject(purchaseRecordIn);
		JSONArray inArray = content.getJSONArray(PurchaseRecordKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String userId = singleObject.optString(PurchaseRecordKey.USER_ID, null);
			final String productId = singleObject.optString(PurchaseRecordKey.PRODUCT_ID, null);
			final String purchaseTimeStr = singleObject.optString(PurchaseRecordKey.PURCHASE_TIME, null);
			final String machineId = singleObject.optString(PurchaseRecordKey.MACHINE_ID, null);
			final String networkId = singleObject.optString(PurchaseRecordKey.NETWORK_ID, null);
			final String softwareName = singleObject.optString(PurchaseRecordKey.SOFTWARE_NAME, null);
			final String softwareVersion = singleObject.optString(PurchaseRecordKey.SOFTWARE_VERSION, null);
			final String firmWareVersion = singleObject.optString(PurchaseRecordKey.FIRMWARE_VERSION, null);
			final String referId = singleObject.optString(PurchaseRecordKey.REFER_ID, null);
			if (S.isInvalidValue(productId) || S.isInvalidValue(userId) || !S.isValidUser(userId)) {
				continue;
			}
			if (purchaseRecordDAOW.getPurchaseRecordByUserIdAndProductId(userId, productId) == null) {
				purchaseRecordDAOW.save(purchaseRecordFactory.newInstance(userId, productId, DateUtil.getDateWithStr(purchaseTimeStr, new Date()), machineId, networkId, softwareVersion, softwareName, firmWareVersion, referId));
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadPurchaseRecord cost>>>" + costTime);
	}

	public void setPurchaseRecordDAOW(PurchaseRecordDAO purchaseRecordDAOW) {
		this.purchaseRecordDAOW = purchaseRecordDAOW;
	}

	public void setPurchaseRecordFactory(PurchaseRecordFactory purchaseRecordFactory) {
		this.purchaseRecordFactory = purchaseRecordFactory;
	}

	public void setPurchase_record_in(String purchase_record_in) {
		this.purchaseRecordIn = purchase_record_in;
	}

}
