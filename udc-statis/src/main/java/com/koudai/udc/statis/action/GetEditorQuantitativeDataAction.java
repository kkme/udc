package com.koudai.udc.statis.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.koudai.udc.statis.dao.ProductRecommendDAO;
import com.koudai.udc.statis.dao.ProductStatisDAO;
import com.koudai.udc.statis.domain.ProductRecommend;
import com.koudai.udc.statis.domain.ProductStatis;
import com.koudai.udc.statis.tool.DateFormatter;
import com.koudai.udc.statis.tool.DateUtil;
import com.koudai.udc.statis.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetEditorQuantitativeDataAction extends ActionSupport {

	private static final long serialVersionUID = -482793214543578002L;

	private static final Logger LOGGER = Logger.getLogger(GetEditorQuantitativeDataAction.class);

	private static final DateFormatter DATE_FORMAT = new DateFormatter();

	private ProductRecommendDAO productRecommendDAO;

	private ProductStatisDAO productStatisDAO;

	private JSONObject result;

	private Date startTime;

	private Date endTime;

	private String userId;

	@Override
	public String execute() {
		try {
			LOGGER.info("GetEditorQuantitativeData with userId < " + userId + " > and startTime < " + DATE_FORMAT.format(startTime) + " > and endTime < " + DATE_FORMAT.format(endTime) + " >");
			long beginTime = System.currentTimeMillis();
			List<ProductRecommend> recommends = productRecommendDAO.getProductRecommendsByUserIdAndStartAndEndTime(userId, startTime, endTime);
			Map<String, Integer> totalNum = new HashMap<String, Integer>();
			totalNum.put(S.CLICK_TAG, 0);
			totalNum.put(S.COLLECT_TAG, 0);
			totalNum.put(S.PURCHASE_TAG, 0);
			Map<String, Integer> containNum = new HashMap<String, Integer>();
			containNum.put(S.CLICK_TAG, 0);
			containNum.put(S.COLLECT_TAG, 0);
			containNum.put(S.PURCHASE_TAG, 0);
			initTotalAndContainNumMap(recommends, totalNum, containNum);
			result = getSuccessfulResult(userId, recommends.size(), totalNum, containNum);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getEditorQuantitativeData cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			return ERROR;
		}
	}

	private void initTotalAndContainNumMap(List<ProductRecommend> recommends, Map<String, Integer> totalNum, Map<String, Integer> containNum) {
		for (ProductRecommend recommend : recommends) {
			ProductStatis productStatis = productStatisDAO.getProductStatisByProductId(recommend.getProductId());
			if (productStatis == null) {
				LOGGER.error("ProductStatis not found with product id < " + recommend.getProductId() + " >");
				continue;
			}
			int totalClickedNum = totalNum.get(S.CLICK_TAG);
			totalNum.put(S.CLICK_TAG, totalClickedNum + productStatis.getClickedNum());
			int totalCollectedNum = totalNum.get(S.COLLECT_TAG);
			totalNum.put(S.COLLECT_TAG, totalCollectedNum + productStatis.getCollectedNum());
			int totalPurchasedNum = totalNum.get(S.PURCHASE_TAG);
			totalNum.put(S.PURCHASE_TAG, totalPurchasedNum + productStatis.getPurchasedNum());
			if (productStatis.getClickedNum() > 0) {
				int containClickedNum = containNum.get(S.CLICK_TAG);
				containNum.put(S.CLICK_TAG, containClickedNum + 1);
			}
			if (productStatis.getCollectedNum() > 0) {
				int containCollectedNum = containNum.get(S.COLLECT_TAG);
				containNum.put(S.COLLECT_TAG, containCollectedNum + 1);
			}
			if (productStatis.getPurchasedNum() > 0) {
				int containPurchasedNum = containNum.get(S.PURCHASE_TAG);
				containNum.put(S.PURCHASE_TAG, containPurchasedNum + 1);
			}
		}
	}

	private JSONObject getSuccessfulResult(String userId, int productNum, Map<String, Integer> totalNum, Map<String, Integer> containNum) {
		try {
			JSONObject result = new JSONObject();
			JSONObject status = new JSONObject();
			status.put("status_code", S.SUCCESS_CODE);
			status.put("status_reason", S.EMPTY_STR);
			JSONObject content = new JSONObject();
			if (userId == null) {
				content.put("userID", "all");
			} else {
				content.put("userID", userId);
			}
			content.put("productnum", String.valueOf(productNum));
			JSONObject click = new JSONObject();
			click.put("containnum", containNum.get(S.CLICK_TAG));
			click.put("totalnum", totalNum.get(S.CLICK_TAG));
			content.put("clickednum", click);
			JSONObject collect = new JSONObject();
			collect.put("containnum", containNum.get(S.COLLECT_TAG));
			collect.put("totalnum", totalNum.get(S.COLLECT_TAG));
			content.put("collectednum", collect);
			JSONObject purchase = new JSONObject();
			purchase.put("containnum", containNum.get(S.PURCHASE_TAG));
			purchase.put("totalnum", totalNum.get(S.PURCHASE_TAG));
			content.put("purchasednum", purchase);
			result.put("status", status);
			result.put("editor_quantitative_overall_data", content);
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
			result.put("editor_quantitative_overall_data", content);
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

	public void setStartTime(Date startTime) {
		this.startTime = DateUtil.setTimeOnDate(startTime, 0, 0, 0);
	}

	public void setEndTime(Date endTime) {
		this.endTime = DateUtil.setTimeOnDate(endTime, 23, 59, 59);
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

}
