package com.koudai.udc.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.persistence.ProductCollectDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class GetProductCollectMd5Action extends Md5Action {

	private static final long serialVersionUID = 1570412113821372505L;

	private static final Logger LOGGER = Logger.getLogger(GetShopFavoriteMd5Action.class);

	private ProductCollectDAO productCollectDAOR;

	@Override
	protected void generateMd5code() throws Exception {
		long beginTime = System.currentTimeMillis();
		LOGGER.info("User < " + userId + " > request products md5 with position from " + start + " to " + end);
		List<String> productIds = getLimitSortedProductIds();
		if (productIds.size() == 0) {
			result = getMd5Result(S.SUCCESS_CODE, S.EMPTY_STR, 0, S.EMPTY_STR);
			return;
		}
		result = getMd5Result(S.SUCCESS_CODE, S.EMPTY_STR, productIds.size(), md5Generator.getMd5Code(productIds));
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("getProductCollectMd5 cost>>>" + costTime);
	}

	private List<String> getLimitSortedProductIds() {
		if (start == 0 && end == 0) {
			return productCollectDAOR.getAllProductIdsByUserIdOrderByTime(userId);
		}
		return productCollectDAOR.getProductIdsByUserIdAndStartAndEndPosOrderByTime(userId, start, end);
	}

	@Override
	protected JSONObject getMd5Result(int code, String reason, int num, String md5) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			statusObject.put("product_num", num);
			JSONObject md5Object = new JSONObject();
			md5Object.put("md5", md5);
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("productcollectmd5", md5Object);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setProductCollectDAOR(ProductCollectDAO productCollectDAOR) {
		this.productCollectDAOR = productCollectDAOR;
	}

}
