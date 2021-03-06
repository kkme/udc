package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.utils.ProductCollectKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadProductCollectV2Action extends ProductCollectAction {

	private static final long serialVersionUID = -2107790866285988062L;

	private static final Logger LOGGER = Logger.getLogger(UploadProductCollectV2Action.class);

	private String productCollectIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(productCollectIn)) {
			throw new IncorrectInputParameterException("upload_productcollect_in of version 2 is null or empty");
		}
		LOGGER.info("Upload product collect request of version 2 is : " + productCollectIn);
		JSONObject content = new JSONObject(productCollectIn);
		JSONArray inArray = content.getJSONArray(ProductCollectKey.UPLOAD_CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(ProductCollectKey.PRODUCT_ID, null);
			final String machineId = singleObject.optString(ProductCollectKey.MACHINE_ID, null);
			final String userIdReal = singleObject.optString(ProductCollectKey.USER_ID_REAL, null);
			final String userIdAnonymous = singleObject.optString(ProductCollectKey.USER_ID_ANONYMOUS, null);
			final String networkId = singleObject.optString(ProductCollectKey.NETWORK_ID, null);
			final String softwareName = singleObject.optString(ProductCollectKey.SOFTWARE_NAME, null);
			final String softwareVersion = singleObject.optString(ProductCollectKey.SOFTWARE_VERSION, null);
			final String firmWareVersion = singleObject.optString(ProductCollectKey.FIRMWARE_VERSION, null);
			final String referId = singleObject.optString(ProductCollectKey.REFER_ID, null);
			if (S.isInvalidValue(productId)) {
				continue;
			}
			if (!S.isInvalidValue(userIdAnonymous) && S.isAnonymousUser(userIdAnonymous)) {
				collectAndCount(userIdAnonymous, productId, machineId, networkId, softwareName, softwareVersion, firmWareVersion, referId);
			}
			if (!S.isInvalidValue(userIdReal) && S.isTaobaoUser(userIdReal)) {
				collectAndCount(userIdReal, productId, machineId, networkId, softwareName, softwareVersion, firmWareVersion, referId);
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadProductCollect2 cost>>>" + costTime);
	}

	public void setUpload_productcollect_in(String upload_productcollect_in) {
		this.productCollectIn = upload_productcollect_in;
	}

}
