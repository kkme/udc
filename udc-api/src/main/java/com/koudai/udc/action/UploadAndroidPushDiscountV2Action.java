package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ProductPush;
import com.koudai.udc.domain.PushInfoType;
import com.koudai.udc.domain.factory.AndroidPushInfoFactory;
import com.koudai.udc.domain.factory.ProductPushFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.AndroidPushInfoDAO;
import com.koudai.udc.persistence.ProductPushDAO;
import com.koudai.udc.utils.AndroidPushInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadAndroidPushDiscountV2Action extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -8634818476222185078L;

	private static final Logger LOGGER = Logger.getLogger(UploadAndroidPushDiscountV2Action.class);

	private AndroidPushInfoDAO androidPushInfoDAOW;

	private ProductPushDAO productPushDAOW;

	private AndroidPushInfoFactory androidPushInfoFactory;

	private ProductPushFactory productPushFactory;

	private String androidPushInfoIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(androidPushInfoIn)) {
			throw new IncorrectInputParameterException("upload_android_push_info_in is null or empty");
		}
		JSONObject content = new JSONObject(androidPushInfoIn);
		JSONArray inArray = content.getJSONArray(AndroidPushInfoKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String machineId = singleObject.optString(AndroidPushInfoKey.MACHINE_ID, null);
			final String productName = singleObject.optString(AndroidPushInfoKey.PRODUCT_NAME, S.EMPTY_STR);
			final String productId = singleObject.optString(AndroidPushInfoKey.PRODUCT_ID, null);
			final int productType = singleObject.optInt(AndroidPushInfoKey.PRODUCT_TYPE, 0);
			final int pushType = singleObject.optInt(AndroidPushInfoKey.PUSH_TYPE, 0);

			final String manualId = singleObject.optString(AndroidPushInfoKey.MANUAL_ID, S.EMPTY_STR);
			final int manualType = singleObject.optInt(AndroidPushInfoKey.MANUAL_TYPE, 0);

			if (S.isInvalidValue(machineId)) {
				continue;
			}

			if (manualType == 0) {
				StringBuffer productIds = new StringBuffer(productId);
				productIds.append(S.COLON_STR);
				productIds.append(String.valueOf(productType));
				ProductPush productPush = productPushDAOW.getProductPushByMachineAndProductId(machineId, productId);
				if (productPush == null) {
					productPushDAOW.save(productPushFactory.newInstance(machineId, productId));
					androidPushInfoDAOW.save(androidPushInfoFactory.newInstance(machineId, productName, productIds.toString(), PushInfoType.SINGLE.getCode(), pushType, manualId));
				}
			} else {
				androidPushInfoDAOW.save(androidPushInfoFactory.newInstance(machineId, productName, S.EMPTY_STR, PushInfoType.SINGLE.getCode(), pushType, manualId));
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadAndroidPushDiscount2 cost>>>" + costTime);
	}

	public void setAndroidPushInfoDAOW(AndroidPushInfoDAO androidPushInfoDAOW) {
		this.androidPushInfoDAOW = androidPushInfoDAOW;
	}

	public void setProductPushDAOW(ProductPushDAO productPushDAOW) {
		this.productPushDAOW = productPushDAOW;
	}

	public void setAndroidPushInfoFactory(AndroidPushInfoFactory androidPushInfoFactory) {
		this.androidPushInfoFactory = androidPushInfoFactory;
	}

	public void setProductPushFactory(ProductPushFactory productPushFactory) {
		this.productPushFactory = productPushFactory;
	}

	public void setUpload_android_push_info_in(String upload_android_push_info_in) {
		this.androidPushInfoIn = upload_android_push_info_in;
	}

}
