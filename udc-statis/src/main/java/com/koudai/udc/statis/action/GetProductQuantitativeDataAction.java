package com.koudai.udc.statis.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.statis.dao.ProductRecommendDAO;
import com.koudai.udc.statis.dao.ProductStatisDAO;
import com.koudai.udc.statis.domain.ProductRecommend;
import com.koudai.udc.statis.domain.ProductStatis;
import com.koudai.udc.statis.tool.DateFormatter;
import com.koudai.udc.statis.tool.DateUtil;
import com.koudai.udc.statis.tool.Num;
import com.koudai.udc.statis.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetProductQuantitativeDataAction extends ActionSupport {

	private static final long serialVersionUID = -1241806523204646076L;

	private static final Logger LOGGER = Logger.getLogger(GetProductQuantitativeDataAction.class);

	private static final DateFormatter DATE_FORMAT = new DateFormatter();

	private ProductRecommendDAO productRecommendDAO;

	private ProductStatisDAO productStatisDAO;

	private JSONObject result;

	private Date startTime;

	private Date endTime;

	private int page;

	private int pageNum;

	private String userId;

	@Override
	public String execute() {
		try {
			LOGGER.info("GetProductQuantitativeData with userId < " + userId + " > and startTime < " + DATE_FORMAT.format(startTime) + " > and endTime < " + DATE_FORMAT.format(endTime) + " > and page < " + page + " > and pageNum < " + pageNum + " >");
			long beginTime = System.currentTimeMillis();
			List<EditorProductStatis> editorStatisList = new ArrayList<EditorProductStatis>();
			final int productNum = productRecommendDAO.getCountByUserIdAndStartAndEndTime(userId, startTime, endTime);
			if (productNum == 0) {
				result = getSuccessfulResult(Num.ZERO, editorStatisList);
			} else {
				List<ProductRecommend> recommends = productRecommendDAO.getLimitProductRecommendsByUserIdAndStartAndEndTime(userId, startTime, endTime, page, pageNum);
				for (ProductRecommend recommend : recommends) {
					ProductStatis statis = productStatisDAO.getProductStatisByProductId(recommend.getProductId());
					editorStatisList.add(new EditorProductStatis(recommend.getUserId(), statis));
				}
				result = getSuccessfulResult(productNum, editorStatisList);
			}
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getProductQuantitativeData cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			return ERROR;
		}
	}

	private JSONObject getSuccessfulResult(int productNum, List<EditorProductStatis> editorStatisList) {
		try {
			JSONObject result = new JSONObject();
			JSONObject status = new JSONObject();
			status.put("status_code", S.SUCCESS_CODE);
			status.put("status_reason", S.EMPTY_STR);
			status.put("product_num", productNum);
			JSONArray content = new JSONArray();
			for (EditorProductStatis editorStatis : editorStatisList) {
				JSONObject statisObject = new JSONObject();
				ProductStatis statis = editorStatis.getStatis();
				statisObject.put("userID", editorStatis.getUserId());
				statisObject.put("productID", statis.getProductId());
				statisObject.put("clickednum", statis.getClickedNum());
				statisObject.put("collectednum", statis.getCollectedNum());
				statisObject.put("purchasednum", statis.getPurchasedNum());
				content.put(statisObject);
			}
			result.put("status", status);
			result.put("product_quantitative_detail_data", content);
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
			status.put("product_num", Num.ZERO);
			JSONObject content = new JSONObject();
			result.put("status", status);
			result.put("product_quantitative_detail_data", content);
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

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

}
