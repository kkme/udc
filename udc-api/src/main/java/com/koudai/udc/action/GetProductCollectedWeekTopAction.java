package com.koudai.udc.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ProductCollectWeekTop;
import com.koudai.udc.persistence.ProductCollectWeekTopDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class GetProductCollectedWeekTopAction extends WeekTopAction {

	private static final long serialVersionUID = 8729927567726381228L;

	private static final Logger LOGGER = Logger.getLogger(GetProductCollectedWeekTopAction.class);

	private ProductCollectWeekTopDAO productCollectWeekTopDAOR;

	@Override
	protected void getWeekTopData() throws Exception {
		long beginTime = System.currentTimeMillis();
		LOGGER.info("Get product collect week top data with typeId < " + typeId + " > and week < " + week + " > and year < " + year + " >");
		List<ProductCollectWeekTop> tops = productCollectWeekTopDAOR.getWeekTopsByTypeIdAndWeekAndYear(typeId, week, year);
		result = getSuccessfulWeekTopResult(tops);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("getProductCollectedWeekTop cost>>>" + costTime);
	}

	private JSONObject getSuccessfulWeekTopResult(List<ProductCollectWeekTop> tops) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", S.SUCCESS_CODE);
			statusObject.put("status_reason", S.EMPTY_STR);
			JSONArray topArray = new JSONArray();
			for (ProductCollectWeekTop top : tops) {
				JSONObject idObject = new JSONObject();
				idObject.put("productid", top.getProductId());
				idObject.put("collect_num", top.getCollectedNum());
				topArray.put(idObject);
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

	public void setProductCollectWeekTopDAOR(ProductCollectWeekTopDAO productCollectWeekTopDAOR) {
		this.productCollectWeekTopDAOR = productCollectWeekTopDAOR;
	}

}
