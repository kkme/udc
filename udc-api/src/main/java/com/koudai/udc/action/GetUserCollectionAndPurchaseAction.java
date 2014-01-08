package com.koudai.udc.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectDAO;
import com.koudai.udc.persistence.PurchaseRecordDAO;
import com.koudai.udc.utils.DateFormatter;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetUserCollectionAndPurchaseAction extends ActionSupport {

	private static final long serialVersionUID = -8398905565908908322L;

	private static final Logger LOGGER = Logger.getLogger(GetUserCollectionAndPurchaseAction.class);

	private ProductCollectDAO productCollectDAOR;

	private PurchaseRecordDAO purchaseRecordDAOR;

	private String userId;

	private Date startTime;

	private Date endTime;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			verifyInputParameters();
			long beginTime = System.currentTimeMillis();
			List<IdAndDate> collections = productCollectDAOR.getProductIdAndCollectTimeByUserIdAndStartAndEndTime(userId, startTime, endTime);
			List<IdAndDate> purchases = purchaseRecordDAOR.getProductIdAndPurchaseTimeByUserIdAndStartAndEndTime(userId, startTime, endTime);
			result = getUserResult(S.SUCCESS_CODE, S.EMPTY_STR, collections, purchases);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getUserCollectionAndPurchase cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getUserResult(S.ERROR_CODE, e.getMessage(), S.EMPTY_ID_AND_DATE_LIST, S.EMPTY_ID_AND_DATE_LIST);
			return ERROR;
		}
	}

	private void verifyInputParameters() throws IncorrectInputParameterException {
		if (S.isInvalidValue(userId) || !S.isValidUser(userId)) {
			throw new IncorrectInputParameterException("User id is invalid");
		}
		Date currentDate = new Date();
		if (startTime == null) {
			startTime = currentDate;
			endTime = DateUtil.tomorrow(currentDate);
		} else if (endTime == null) {
			endTime = currentDate;
		}
		if (startTime.after(endTime)) {
			throw new IncorrectInputParameterException("StartTime should be before endTime when getting user collection and purchase");
		}
		LOGGER.info("Get user collection and purchase data with user id < " + userId + " > and startTime < " + new DateFormatter().format(startTime) + " > and endTime < " + new DateFormatter().format(endTime) + " >.");
	}

	private JSONObject getUserResult(int code, String reason, List<IdAndDate> collections, List<IdAndDate> purchases) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray collectionArray = new JSONArray();
			for (IdAndDate collection : collections) {
				JSONObject tempObject = new JSONObject();
				tempObject.put("productid", collection.getId());
				tempObject.put("collecttime", collection.getFormatterDate());
				collectionArray.put(tempObject);
			}
			JSONArray purchaseArray = new JSONArray();
			for (IdAndDate purchase : purchases) {
				JSONObject tempObject = new JSONObject();
				tempObject.put("productid", purchase.getId());
				tempObject.put("purchasetime", purchase.getFormatterDate());
				purchaseArray.put(tempObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("collection", collectionArray);
			result.put("purchase", purchaseArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setProductCollectDAOR(ProductCollectDAO productCollectDAOR) {
		this.productCollectDAOR = productCollectDAOR;
	}

	public void setPurchaseRecordDAOR(PurchaseRecordDAO purchaseRecordDAOR) {
		this.purchaseRecordDAOR = purchaseRecordDAOR;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public JSONObject getResult() {
		return result;
	}

}
