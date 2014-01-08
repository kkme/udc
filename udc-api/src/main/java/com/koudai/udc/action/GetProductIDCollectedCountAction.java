package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectedCountDAO;
import com.koudai.udc.utils.ProductCountKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class GetProductIDCollectedCountAction extends CountAction {

	private static final long serialVersionUID = 1018989063195848935L;

	private static final Logger LOGGER = Logger.getLogger(GetProductIDCollectedCountAction.class);

	private String productIdIn;

	private ProductCollectedCountDAO productCollectedCountDAOR;

	@Override
	protected void verifyInputParameters() throws Exception {
		if (S.isInvalidValue(productIdIn)) {
			throw new IncorrectInputParameterException("get_productID_collectedcount_in is null or empty");
		}
	}

	@Override
	protected Map<String, Integer> getItemCountMap() throws Exception {
		long beginTime = System.currentTimeMillis();
		Map<String, Integer> itemCountMap = new HashMap<String, Integer>();
		JSONObject content = new JSONObject(productIdIn);
		JSONArray inArray = content.getJSONArray(ProductCountKey.CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(ProductCountKey.PRODUCT_ID, null);
			if (S.isInvalidValue(productId)) {
				continue;
			}
			int count = productCollectedCountDAOR.getCollectedCountByProductId(productId);
			itemCountMap.put(productId, count);
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("getProductIDCollectedCount cost>>>" + costTime);
		return itemCountMap;
	}

	@Override
	protected JSONObject getCountResult(int code, String reason, Map<String, Integer> itemCountMap) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray countArray = new JSONArray();
			Iterator<Entry<String, Integer>> it = itemCountMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Integer> entry = it.next();
				JSONObject countObject = new JSONObject();
				countObject.put("productID", entry.getKey());
				countObject.put("collectedCount", entry.getValue());
				countArray.put(countObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("collectedcount", countArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setGet_productID_collectedcount_in(String get_productID_collectedcount_in) {
		this.productIdIn = get_productID_collectedcount_in;
	}

	public void setProductCollectedCountDAOR(ProductCollectedCountDAO productCollectedCountDAOR) {
		this.productCollectedCountDAOR = productCollectedCountDAOR;
	}

}
