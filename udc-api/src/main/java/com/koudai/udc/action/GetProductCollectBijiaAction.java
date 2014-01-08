package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.BijiaProduct;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.ProductRemindService;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetProductCollectBijiaAction extends ActionSupport {

	private static final long serialVersionUID = 2950718567578317006L;

	private static final Logger LOGGER = Logger.getLogger(GetProductCollectBijiaAction.class);

	private static final List<BijiaProduct> EMPTY_ITEMS = new ArrayList<BijiaProduct>();

	private String userId;

	private int start = 0;

	private int end = 0;

	private ProductRemindService productRemindService;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			LOGGER.info("User < " + userId + " > request bijia products with position from " + start + " to " + end);
			verifyInputParameters();
			List<BijiaProduct> products = productRemindService.getBijiaProducts(userId, start, end);
			result = getResult(S.SUCCESS_CODE, S.EMPTY_STR, products.size(), products);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getProductCollectBijia cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getResult(S.ERROR_CODE, e.getMessage(), 0, EMPTY_ITEMS);
			return ERROR;
		}
	}

	private void verifyInputParameters() throws IncorrectInputParameterException {
		if (S.isInvalidValue(userId)) {
			throw new IncorrectInputParameterException("User id is null or empty");
		}
		if (start > end) {
			throw new IncorrectInputParameterException("Start position should less than end position");
		}
	}

	private JSONObject getResult(int code, String reason, int num, List<BijiaProduct> products) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			statusObject.put("product_num", num);
			JSONArray itemArray = new JSONArray();
			for (BijiaProduct product : products) {
				JSONObject idObject = new JSONObject();
				idObject.put("productID", product.getProductId());
				idObject.put("priceReduction", product.getFormatterPriceReduction());
				idObject.put("canRemind", product.getFormatterCanRemind());
				idObject.put("priceCutOff", product.getFormatterBalance());
				itemArray.put(idObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("productcollect", itemArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setStartPos(int startPos) {
		this.start = startPos;
	}

	public void setEndPos(int endPos) {
		this.end = endPos;
	}

	public void setProductRemindService(ProductRemindService productRemindService) {
		this.productRemindService = productRemindService;
	}

	public JSONObject getResult() {
		return result;
	}

}
