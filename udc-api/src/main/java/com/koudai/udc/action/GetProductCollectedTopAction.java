package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ProductCollectTop;
import com.koudai.udc.domain.ProductTopStatus;
import com.koudai.udc.persistence.ProductCollectTopDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class GetProductCollectedTopAction extends TopAction {

	private static final long serialVersionUID = -2736531983531286334L;

	private static final Logger LOGGER = Logger.getLogger(GetProductCollectedTopAction.class);

	private ProductCollectTopDAO productCollectTopDAOR;

	@Override
	protected void getTopData() throws Exception {
		long beginTime = System.currentTimeMillis();
		LOGGER.info("Get product collect top data with day < " + day + " > and num < " + top + " > and type id < " + typeId + " >");
		List<ProductCollectTop> subList = getSubTopData();
		result = getSuccessfulTopResult(subList);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("getProductCollectedTop cost>>>" + costTime);
	}

	private List<ProductCollectTop> getSubTopData() {
		List<ProductCollectTop> top10s = productCollectTopDAOR.getTopsByTypeIdAndStartAndEndTime(typeId, startTime, endTime, ProductTopStatus.daily_top_ten.getCode());
		Collections.sort(top10s, new DescendingComparator());
		if (top10s.size() == 0) {
			return new ArrayList<ProductCollectTop>();
		}
		if (top <= 10) {
			return top10s.subList(0, top);
		}
		List<ProductCollectTop> top1000s = productCollectTopDAOR.getTopsByTypeIdAndStartAndEndTime(typeId, startTime, endTime, ProductTopStatus.daily_top_one_thousand.getCode());
		Collections.sort(top1000s, new DescendingComparator());
		if (top1000s.size() == 0 || top1000s.size() <= 10) {
			return top10s;
		}
		top1000s.removeAll(top10s);
		List<ProductCollectTop> allTops = new ArrayList<ProductCollectTop>();
		allTops.addAll(top10s);
		allTops.addAll(top1000s);
		return allTops.subList(0, top);
	}

	private JSONObject getSuccessfulTopResult(List<ProductCollectTop> productCollectTops) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", S.SUCCESS_CODE);
			statusObject.put("status_reason", S.EMPTY_STR);
			JSONArray topArray = new JSONArray();
			for (ProductCollectTop productCollectTop : productCollectTops) {
				JSONObject idObject = new JSONObject();
				idObject.put("productid", productCollectTop.getProductId());
				idObject.put("collect_num", productCollectTop.getCollectedNum());
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

	public void setProductCollectTopDAOR(ProductCollectTopDAO productCollectTopDAOR) {
		this.productCollectTopDAOR = productCollectTopDAOR;
	}

}
