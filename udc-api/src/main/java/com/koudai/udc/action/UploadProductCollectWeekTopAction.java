package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.factory.ProductCollectWeekTopFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectWeekTopDAO;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.ProductCollectWeekTopKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadProductCollectWeekTopAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 9218134887639766773L;

	private static final Logger LOGGER = Logger.getLogger(UploadProductCollectWeekTopAction.class);

	private ProductCollectWeekTopDAO productCollectWeekTopDAOW;

	private ProductCollectWeekTopFactory productCollectWeekTopFactory;

	private String weekTopIn;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(weekTopIn)) {
			throw new IncorrectInputParameterException("upload_productcollect_week_top_in is null or empty");
		}
		LOGGER.info("Upload product collect week top request is : " + weekTopIn);
		JSONObject content = new JSONObject(weekTopIn);
		JSONArray inArray = content.getJSONArray(ProductCollectWeekTopKey.UPLOAD_CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(ProductCollectWeekTopKey.PRODUCT_ID, null);
			final int collectedNum = singleObject.optInt(ProductCollectWeekTopKey.COLLECTED_NUM, 0);
			final String typeId = singleObject.optString(ProductCollectWeekTopKey.TYPE_ID, null);
			final int week = singleObject.optInt(ProductCollectWeekTopKey.WEEK, DateUtil.lastWeek());
			final int year = singleObject.optInt(ProductCollectWeekTopKey.YEAR, DateUtil.yearOfLastWeek());
			productCollectWeekTopDAOW.save(productCollectWeekTopFactory.newInstance(productId, collectedNum, typeId, week, year));
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadProductCollectWeekTop cost>>>" + costTime);
	}

	public void setProductCollectWeekTopDAOW(ProductCollectWeekTopDAO productCollectWeekTopDAOW) {
		this.productCollectWeekTopDAOW = productCollectWeekTopDAOW;
	}

	public void setProductCollectWeekTopFactory(ProductCollectWeekTopFactory productCollectWeekTopFactory) {
		this.productCollectWeekTopFactory = productCollectWeekTopFactory;
	}

	public void setUpload_productcollect_week_top_in(String upload_productcollect_week_top_in) {
		this.weekTopIn = upload_productcollect_week_top_in;
	}

}
