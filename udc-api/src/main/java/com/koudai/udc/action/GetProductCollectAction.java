package com.koudai.udc.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.persistence.ProductCollectDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class GetProductCollectAction extends SortLimitAction {

	private static final long serialVersionUID = 2941082910454447286L;

	private static final Logger LOGGER = Logger.getLogger(GetProductCollectAction.class);

	private ProductCollectDAO productCollectDAOR;

	@Override
	protected void getItemIdsByStartAndEndPos() throws Exception {
		long beginTime = System.currentTimeMillis();
		LOGGER.info("User < " + userId + " > request products with position from " + start + " to " + end);
		List<String> productIds = getLimitSortedProductIds();
		result = getSortLimitResult(S.SUCCESS_CODE, S.EMPTY_STR, productIds.size(), productIds);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("getProductCollect cost>>>" + costTime);
	}

	private List<String> getLimitSortedProductIds() {
		if (start == 0 && end == 0) {
			return productCollectDAOR.getAllProductIdsByUserIdOrderByTime(userId);
		}
		return productCollectDAOR.getProductIdsByUserIdAndStartAndEndPosOrderByTime(userId, start, end);
	}

	@Override
	protected JSONObject getSortLimitResult(int code, String reason, int num, List<String> itemIds) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			statusObject.put("product_num", num);
			JSONArray idArray = new JSONArray();
			for (String itemId : itemIds) {
				JSONObject idObject = new JSONObject();
				idObject.put("productID", itemId);
				idArray.put(idObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("productcollect", idArray);
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
