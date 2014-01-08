package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.DiscountProduct;
import com.koudai.udc.domain.ProductPush;
import com.koudai.udc.domain.PushDiscount;
import com.koudai.udc.domain.factory.AndroidPushInfoFactory;
import com.koudai.udc.domain.factory.ProductPushFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.AndroidPushInfoDAO;
import com.koudai.udc.persistence.ProductPushDAO;
import com.koudai.udc.utils.AndroidPushInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadAndroidPushDiscountAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 1280350424531728546L;

	private static final Logger LOGGER = Logger.getLogger(UploadAndroidPushDiscountAction.class);

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
		List<PushDiscount> pushDiscounts = new ArrayList<PushDiscount>();
		JSONObject content = new JSONObject(androidPushInfoIn);
		JSONArray inArray = content.getJSONArray(AndroidPushInfoKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String machineId = singleObject.optString(AndroidPushInfoKey.MACHINE_ID, null);
			if (S.isInvalidValue(machineId)) {
				continue;
			}
			JSONArray productArray = singleObject.getJSONArray(AndroidPushInfoKey.PRODUCT_INFO_KEY);
			List<DiscountProduct> discountProducts = new ArrayList<DiscountProduct>();
			for (int j = 0; j < productArray.length(); j++) {
				JSONObject productObject = productArray.getJSONObject(j);
				final String name = productObject.optString(AndroidPushInfoKey.PRODUCT_NAME, null);
				final String id = productObject.optString(AndroidPushInfoKey.PRODUCT_ID, null);
				final int type = productObject.optInt(AndroidPushInfoKey.PRODUCT_TYPE, 0);
				if (S.isInvalidValue(name) || S.isInvalidValue(id)) {
					continue;
				}
				ProductPush productPush = productPushDAOW.getProductPushByMachineAndProductId(machineId, id);
				if (productPush == null) {
					productPushDAOW.save(productPushFactory.newInstance(machineId, id));
					discountProducts.add(new DiscountProduct(id, name, type));
				}
			}
			if (!discountProducts.isEmpty()) {
				PushDiscount pushDiscount = new PushDiscount(machineId, discountProducts);
				pushDiscounts.add(pushDiscount);
				androidPushInfoDAOW.save(androidPushInfoFactory.newInstance(machineId, pushDiscount.getFirstProductName(), pushDiscount.getProductIds(), pushDiscount.getInfoType()));
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadAndroidPushDiscount cost>>>" + costTime);
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
