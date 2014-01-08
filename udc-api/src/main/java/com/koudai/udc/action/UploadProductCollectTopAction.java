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

public class UploadProductCollectTopAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 7693160982736559357L;

	private static final Logger LOGGER = Logger.getLogger(UploadProductCollectAction.class);

	private String productCollectTopIn;

	private ProductCollectTopDAO productCollectTopDAOW;

	private ProductCollectTopFactory productCollectTopFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(productCollectTopIn)) {
			throw new IncorrectInputParameterException("upload_productcollect_top_in is null or empty");
		}
		LOGGER.info("Upload product collect top request is : " + productCollectTopIn);
		JSONObject content = new JSONObject(productCollectTopIn);
		JSONArray inArray = content.getJSONArray(ProductCollectTopKey.UPLOAD_CONTENT_KEY);
		for (int i = 0; i < inArray.length(); i++) {
			JSONObject singleObject = inArray.getJSONObject(i);
			final String productId = singleObject.optString(ProductCollectTopKey.PRODUCT_ID, null);
			final int collectedNum = singleObject.optInt(ProductCollectTopKey.COLLECTED_NUM, 0);
			final String typeId = singleObject.optString(ProductCollectTopKey.TYPE_ID, null);
			final int useType = singleObject.optInt(ProductCollectTopKey.USE_TYPE, 0);
			final Date statTime = DateUtil.minYesterday();
			productCollectTopDAOW.save(productCollectTopFactory.newInstance(productId, collectedNum, typeId, statTime, useType));
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadProductCollectTop cost>>>" + costTime);
	}

	public void setUpload_productcollect_top_in(String upload_productcollect_top_in) {
		this.productCollectTopIn = upload_productcollect_top_in;
	}

	public void setProductCollectTopDAOW(ProductCollectTopDAO productCollectTopDAOW) {
		this.productCollectTopDAOW = productCollectTopDAOW;
	}

	public void setProductCollectTopFactory(ProductCollectTopFactory productCollectTopFactory) {
		this.productCollectTopFactory = productCollectTopFactory;
	}

}
