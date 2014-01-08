package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.Platform;
import com.koudai.udc.domain.ProductPush;
import com.koudai.udc.domain.PushInfoType;
import com.koudai.udc.domain.factory.IosPushInfoFactory;
import com.koudai.udc.domain.factory.ProductPushFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.IosPushInfoDAO;
import com.koudai.udc.persistence.ProductPushDAO;
import com.koudai.udc.utils.IosPushInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadIosPushDiscountV2Action extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 1329812123111035239L;

	private static final Logger LOGGER = Logger.getLogger(UploadIosPushDiscountV2Action.class);

	private IosPushInfoDAO iosPushInfoDAOW;

	private ProductPushDAO productPushDAOW;

	private IosPushInfoFactory iosPushInfoFactory;

	private ProductPushFactory productPushFactory;

	private String iosPushInfoIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(iosPushInfoIn)) {
			throw new IncorrectInputParameterException("upload_ios_push_info_in is null or empty");
		}
		JSONObject content = new JSONObject(iosPushInfoIn);
		JSONArray inArray = content.getJSONArray(IosPushInfoKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String machineId = singleObject.optString(IosPushInfoKey.MACHINE_ID, null);
			final String token = singleObject.optString(IosPushInfoKey.TOKEN, null);
			final String platformValue = singleObject.optString(IosPushInfoKey.PLATFORM, null);
			final String productName = singleObject.optString(IosPushInfoKey.PRODUCT_NAME, null);
			final String productId = singleObject.optString(IosPushInfoKey.PRODUCT_ID, null);
			final int productType = singleObject.optInt(IosPushInfoKey.PRODUCT_TYPE, 0);
			if (S.isInvalidValue(machineId) || S.isInvalidValue(token) || S.isInvalidValue(platformValue) || S.isInvalidValue(productName) || S.isInvalidValue(productId)) {
				continue;
			}
			Platform platform = Platform.valueOf(platformValue);
			ProductPush productPush = productPushDAOW.getProductPushByMachineAndProductId(machineId, productId);
			if (productPush == null) {
				StringBuffer productIds = new StringBuffer();
				productIds.append(productId).append(S.COLON_STR);
				productIds.append(String.valueOf(productType));
				productPushDAOW.save(productPushFactory.newInstance(machineId, productId));
				iosPushInfoDAOW.save(iosPushInfoFactory.newInstance(machineId, productName, productIds.toString(), PushInfoType.SINGLE.getCode(), token, platform));
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadIosPushDiscount2 cost>>>" + costTime);
	}

	public void setIosPushInfoDAOW(IosPushInfoDAO iosPushInfoDAOW) {
		this.iosPushInfoDAOW = iosPushInfoDAOW;
	}

	public void setProductPushDAOW(ProductPushDAO productPushDAOW) {
		this.productPushDAOW = productPushDAOW;
	}

	public void setIosPushInfoFactory(IosPushInfoFactory iosPushInfoFactory) {
		this.iosPushInfoFactory = iosPushInfoFactory;
	}

	public void setProductPushFactory(ProductPushFactory productPushFactory) {
		this.productPushFactory = productPushFactory;
	}

	public void setUpload_ios_push_info_in(String upload_ios_push_info_in) {
		this.iosPushInfoIn = upload_ios_push_info_in;
	}

}
