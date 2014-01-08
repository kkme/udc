package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.factory.ProductCollectTopFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectTopDAO;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.ProductCollectTopKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadAllProductCollectedTop30Action extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 2542465355251084374L;

	private static final Logger LOGGER = Logger.getLogger(UploadAllProductCollectedTop30Action.class);

	private String topIn;

	private ProductCollectTopDAO productCollectTopDAOW;

	private ProductCollectTopFactory productCollectTopFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(topIn)) {
			throw new IncorrectInputParameterException("all_top_30_in is null or empty");
		}
		LOGGER.info("Upload all product collect top 30 request is : " + topIn);
		JSONObject content = new JSONObject(topIn);
		JSONArray inArray = content.getJSONArray(ProductCollectTopKey.UPLOAD_CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(ProductCollectTopKey.PRODUCT_ID, null);
			final int collectedNum = singleObject.optInt(ProductCollectTopKey.COLLECTED_NUM, 0);
			final Date statTime = DateUtil.minYesterday();
			productCollectTopDAOW.save(productCollectTopFactory.newInstance(productId, collectedNum, statTime));
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadAllProductCollectedTop30 cost>>>" + costTime);
	}

	public void setAll_top_30_in(String all_top_30_in) {
		this.topIn = all_top_30_in;
	}

	public void setProductCollectTopDAOW(ProductCollectTopDAO productCollectTopDAOW) {
		this.productCollectTopDAOW = productCollectTopDAOW;
	}

	public void setProductCollectTopFactory(ProductCollectTopFactory productCollectTopFactory) {
		this.productCollectTopFactory = productCollectTopFactory;
	}

}
