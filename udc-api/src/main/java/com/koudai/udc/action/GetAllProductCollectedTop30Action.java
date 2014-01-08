package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ProductCollectTop;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectTopDAO;
import com.koudai.udc.utils.DateFormatter;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetAllProductCollectedTop30Action extends ActionSupport {

	private static final long serialVersionUID = -2835102046450218076L;

	private static final Logger LOGGER = Logger.getLogger(GetAllProductCollectedTop30Action.class);

	private static final List<ProductCollectTop> EMPTY_TOPS = new ArrayList<ProductCollectTop>();

	private ProductCollectTopDAO productCollectTopDAOR;

	private String day;

	private Date startTime;

	private Date endTime;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			LOGGER.info("Get all product collect top 30 data with day < " + day + " >");
			verifyInputParameters();
			List<ProductCollectTop> productCollectTops = productCollectTopDAOR.getTop30ByStartAndEndTime(startTime, endTime);
			Collections.sort(productCollectTops, new DescendingComparator());
			result = getTopResult(S.SUCCESS_CODE, S.EMPTY_STR, productCollectTops);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getAllProductCollectedTop30 cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getTopResult(S.ERROR_CODE, e.getMessage(), EMPTY_TOPS);
			return ERROR;
		}
	}

	private void verifyInputParameters() throws IncorrectInputParameterException {
		if (S.isInvalidValue(day)) {
			startTime = DateUtil.setTimeOnDate(DateUtil.yesterday(), 0, 0, 0, 0);
			endTime = DateUtil.tomorrow(startTime);
			return;
		}
		if (!DateUtil.isDate(day)) {
			throw new IncorrectInputParameterException("Day format is illegal");
		}
		startTime = DateUtil.setTimeOnDate(new DateFormatter(DateFormatter.COMMON_DATE_FORMAT).parse(day), 0, 0, 0, 0);
		endTime = DateUtil.tomorrow(startTime);
	}

	private JSONObject getTopResult(int code, String reason, List<ProductCollectTop> productCollectTops) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray topArray = new JSONArray();
			for (ProductCollectTop productCollectTop : productCollectTops) {
				JSONObject topObject = new JSONObject();
				topObject.put("productid", productCollectTop.getProductId());
				topObject.put("collect_num", productCollectTop.getCollectedNum());
				topArray.put(topObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("result_info", topArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setProductCollectTopDAOR(ProductCollectTopDAO productCollectTopDAOR) {
		this.productCollectTopDAOR = productCollectTopDAOR;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public JSONObject getResult() {
		return result;
	}

}
