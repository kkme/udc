package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.DiscountProduct;
import com.koudai.udc.domain.Platform;
import com.koudai.udc.domain.ProductPush;
import com.koudai.udc.domain.PushDiscount;
import com.koudai.udc.domain.factory.IosPushInfoFactory;
import com.koudai.udc.domain.factory.ProductPushFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.IosPushInfoDAO;
import com.koudai.udc.persistence.ProductPushDAO;
import com.koudai.udc.utils.IosPushInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadIosPushDiscountAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -447519248183457694L;

	private static final Logger LOGGER = Logger.getLogger(UploadIosPushDiscountAction.class);

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
		List<PushDiscount> pushDiscounts = new ArrayList<PushDiscount>();
		JSONObject content = new JSONObject(iosPushInfoIn);
		JSONArray inArray = content.getJSONArray(IosPushInfoKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String machineId = singleObject.optString(IosPushInfoKey.MACHINE_ID, null);
			final String token = singleObject.optString(IosPushInfoKey.TOKEN, null);
			final String platformValue = singleObject.optString(IosPushInfoKey.PLATFORM, null);
			if (S.isInvalidValue(machineId) || S.isInvalidValue(token) || S.isInvalidValue(platformValue)) {
				continue;
			}
			Platform platform = Platform.valueOf(platformValue);
			JSONArray productArray = singleObject.getJSONArray(IosPushInfoKey.PRODUCT_INFO_KEY);
			List<DiscountProduct> discountProducts = new ArrayList<DiscountProduct>();
			for (int j = 0; j < productArray.length(); j++) {
				JSONObject productObject = productArray.getJSONObject(j);
				final String name = productObject.optString(IosPushInfoKey.PRODUCT_NAME, null);
				final String id = productObject.optString(IosPushInfoKey.PRODUCT_ID, null);
				final int type = productObject.optInt(IosPushInfoKey.PRODUCT_TYPE, 0);
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
				PushDiscount pushDiscount = new PushDiscount(machineId, token, discountProducts);
				pushDiscounts.add(pushDiscount);
				iosPushInfoDAOW.save(iosPushInfoFactory.newInstance(machineId, pushDiscount.getFirstProductName(), pushDiscount.getProductIds(), pushDiscount.getInfoType(), token, platform));
			}
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadIosPushDiscount cost>>>" + costTime);
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
