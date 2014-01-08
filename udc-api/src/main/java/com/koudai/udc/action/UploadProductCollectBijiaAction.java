package com.koudai.udc.action;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ConfirmStatus;
import com.koudai.udc.domain.ProductCollectBijia;
import com.koudai.udc.domain.factory.ProductCollectBijiaFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectBijiaDAO;
import com.koudai.udc.utils.ProductCollectBijiaKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadProductCollectBijiaAction extends BijiaProductAction {

	private static final long serialVersionUID = 1507333161468599822L;

	private static final Logger LOGGER = Logger.getLogger(UploadProductCollectBijiaAction.class);

	private String productCollectBijiaIn;

	private ProductCollectBijiaDAO productCollectBijiaDAOW;

	private ProductCollectBijiaFactory productCollectBijiaFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(productCollectBijiaIn)) {
			throw new IncorrectInputParameterException("upload_productcollect_bijia_in is null or empty");
		}
		LOGGER.info("Upload product collect bijia request is : " + productCollectBijiaIn);
		JSONObject content = new JSONObject(productCollectBijiaIn);
		JSONArray inArray = content.getJSONArray(ProductCollectBijiaKey.UPLOAD_CONTENT_KEY);
		Set<Boolean> allCollected = new HashSet<Boolean>();
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String machineId = singleObject.optString(ProductCollectBijiaKey.MACHINE_ID, null);
			final String networkId = singleObject.optString(ProductCollectBijiaKey.NETWORK_ID, null);
			final String userId = singleObject.optString(ProductCollectBijiaKey.USER_ID, null);
			final String softwareName = singleObject.optString(ProductCollectBijiaKey.SOFTWARE_NAME, null);
			final String softwareVersion = singleObject.optString(ProductCollectBijiaKey.SOFTWARE_VERSION, null);
			final String firmWareVersion = singleObject.optString(ProductCollectBijiaKey.FIRMWARE_VERSION, null);
			final String productId = singleObject.optString(ProductCollectBijiaKey.PRODUCT_ID, null);
			final String referId = singleObject.optString(ProductCollectBijiaKey.REFER_ID, null);
			ProductCollectBijia productCollectBijia = productCollectBijiaDAOW.getProductCollectBijiaByUserAndProductId(userId, productId);
			if (S.isInvalidValue(productId) || S.BIJIA_ERROR_STR.contains(productId.trim())) {
				continue;
			}
			if (productCollectBijia != null) {
				allCollected.add(productCollectBijia.normal(machineId, networkId, softwareVersion, softwareName, firmWareVersion, referId));
			} else {
				productCollectBijiaDAOW.save(productCollectBijiaFactory.newInstance(productId, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId, ConfirmStatus.NO.getCode()));
				allCollected.add(false);
			}
		}
		result = getSuccessfulUploadResult(allCollected);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadProductCollectBijia cost>>>" + costTime);
	}

	public void setUpload_productcollect_bijia_in(String upload_productcollect_bijia_in) {
		this.productCollectBijiaIn = upload_productcollect_bijia_in;
	}

	public void setProductCollectBijiaDAOW(ProductCollectBijiaDAO productCollectBijiaDAOW) {
		this.productCollectBijiaDAOW = productCollectBijiaDAOW;
	}

	public void setProductCollectBijiaFactory(ProductCollectBijiaFactory productCollectBijiaFactory) {
		this.productCollectBijiaFactory = productCollectBijiaFactory;
	}

}
