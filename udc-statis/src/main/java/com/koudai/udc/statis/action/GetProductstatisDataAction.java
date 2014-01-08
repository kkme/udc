package com.koudai.udc.statis.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.statis.dao.ProductRecommendDAO;
import com.koudai.udc.statis.dao.ProductStatisDAO;
import com.koudai.udc.statis.domain.ProductRecommend;
import com.koudai.udc.statis.domain.ProductStatis;
import com.koudai.udc.statis.exception.InvalidInputParameterException;
import com.koudai.udc.statis.utils.ProductStatisKey;
import com.koudai.udc.statis.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetProductstatisDataAction extends ActionSupport {

	private static final long serialVersionUID = 1431801680992879073L;

	private static final Logger LOGGER = Logger.getLogger(GetProductQuantitativeDataAction.class);

	private ProductRecommendDAO productRecommendDAO;

	private ProductStatisDAO productStatisDAO;

	private JSONObject result;

	private String productStatisDataIn;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			if (productStatisDataIn == null || S.EMPTY_STR.equals(productStatisDataIn.trim())) {
				throw new InvalidInputParameterException("get_product_statis_data_in is null or empty");
			}
			LOGGER.info("Get product statis data request is : " + productStatisDataIn);
			JSONObject content = new JSONObject(productStatisDataIn);
			JSONArray inArray = content.getJSONArray(ProductStatisKey.CONTENT);
			List<String> productIds = new ArrayList<String>();
			for (int i = 0; i < inArray.length(); i++) {
				JSONObject singleObject = inArray.getJSONObject(i);
				final String productId = singleObject.optString(ProductStatisKey.PRODUCT_ID, null);
				if (productId == null || S.EMPTY_STR.equals(productId.trim())) {
					continue;
				}
				productIds.add(productId);
			}
			List<EditorProductStatis> editorStatisList = new ArrayList<EditorProductStatis>();
			for (String productId : productIds) {
				ProductRecommend recommend = productRecommendDAO.getProductRecommendByProductId(productId);
				ProductStatis statis = productStatisDAO.getProductStatisByProductId(productId);
				if (recommend != null && statis != null) {
					editorStatisList.add(new EditorProductStatis(recommend.getUserId(), statis));
				}
			}
			result = getSuccessfulResult(editorStatisList);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getProductstatisData cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			return ERROR;
		}
	}

	private JSONObject getSuccessfulResult(List<EditorProductStatis> editorStatisList) {
		try {
			JSONObject result = new JSONObject();
			JSONObject status = new JSONObject();
			status.put("status_code", S.SUCCESS_CODE);
			status.put("status_reason", S.EMPTY_STR);
			JSONArray content = new JSONArray();
			for (EditorProductStatis editorStatis : editorStatisList) {
				ProductStatis statis = editorStatis.getStatis();
				JSONObject statisObject = new JSONObject();
				statisObject.put("userID", editorStatis.getUserId());
				statisObject.put("productID", statis.getProductId());
				statisObject.put("clickednum", statis.getClickedNum());
				statisObject.put("collectednum", statis.getCollectedNum());
				statisObject.put("purchasednum", statis.getPurchasedNum());
				content.put(statisObject);
			}
			result.put("status", status);
			result.put("product_statis_data", content);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	private JSONObject getFailedResult(String reason) {
		try {
			JSONObject result = new JSONObject();
			JSONObject status = new JSONObject();
			status.put("status_code", S.ERROR_CODE);
			status.put("status_reason", reason);
			JSONObject content = new JSONObject();
			result.put("status", status);
			result.put("product_statis_data", content);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setProductRecommendDAO(ProductRecommendDAO productRecommendDAO) {
		this.productRecommendDAO = productRecommendDAO;
	}

	public void setProductStatisDAO(ProductStatisDAO productStatisDAO) {
		this.productStatisDAO = productStatisDAO;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setGet_product_statis_data_in(String get_product_statis_data_in) {
		this.productStatisDataIn = get_product_statis_data_in;
	}

}
