package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectDAO;
import com.koudai.udc.utils.N;
import com.koudai.udc.utils.ProductCollectKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class IsUserProductCollectedAction extends OwnAction {

	private static final long serialVersionUID = -3001476766772730599L;

	private static final Logger LOGGER = Logger.getLogger(IsUserProductCollectedAction.class);

	private String productCollectedIn;

	private ProductCollectDAO productCollectDAOR;

	@Override
	protected void doExecute() throws Exception {
		if (S.isInvalidValue(productCollectedIn)) {
			throw new IncorrectInputParameterException("is_user_product_collected_in is null or empty");
		}
		long beginTime = System.currentTimeMillis();
		JSONObject content = new JSONObject(productCollectedIn);
		final String userId = content.optString(ProductCollectKey.OWN_USER_KEY, null);
		if (S.isInvalidValue(userId)) {
			throw new IncorrectInputParameterException("User id is null or empty");
		}
		List<String> ownerList = productCollectDAOR.getAllProductIdsByUserId(userId);
		List<String> sourceList = new ArrayList<String>();
		JSONArray idArray = content.getJSONArray(ProductCollectKey.OWN_ID_SET_KEY);
		for (int i = 0; i < idArray.length(); i++) {
			JSONObject singleObject = idArray.getJSONObject(i);
			final String productId = singleObject.optString(ProductCollectKey.OWN_ID_KEY, null);
			if (S.isInvalidValue(productId)) {
				continue;
			}
			sourceList.add(productId);
		}
		result = getOwnResult(S.SUCCESS_CODE, S.EMPTY_STR, sourceList, ownerList);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("isUserProductCollected cost>>>" + costTime);
	}

	@Override
	protected JSONObject getOwnResult(int code, String reason, List<String> sourceList, List<String> ownerList) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray idArray = new JSONArray();
			for (String productId : sourceList) {
				JSONObject singleObject = new JSONObject();
				singleObject.put("productID", productId);
				singleObject.put("iscollected", N.booleanToInt(ownerList.contains(productId)));
				idArray.put(singleObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("iscollected", idArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setIs_user_product_collected_in(String is_user_product_collected_in) {
		this.productCollectedIn = is_user_product_collected_in;
	}

	public void setProductCollectDAOR(ProductCollectDAO productCollectDAOR) {
		this.productCollectDAOR = productCollectDAOR;
	}

}
