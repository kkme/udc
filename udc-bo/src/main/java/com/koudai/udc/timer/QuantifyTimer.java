package com.koudai.udc.timer;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.service.EditorService;
import com.koudai.udc.service.ProductStatisService;
import com.koudai.udc.statis.dao.ProductRecommendDAO;
import com.koudai.udc.statis.tool.DateFormatter;
import com.koudai.udc.statis.tool.DateUtil;
import com.koudai.udc.statis.tool.Num;

public class QuantifyTimer {

	private static final Logger LOGGER = Logger.getLogger(QuantifyTimer.class);

	private EditorService editorService;

	private ProductStatisService productStatisService;

	private ProductRecommendDAO productRecommendDAO;

	public void run() {
		try {
			LOGGER.info("Quantify start");
			long beginTime = System.currentTimeMillis();
			final String day = new DateFormatter(DateFormatter.COMMON_DATE_FORMAT).format(DateUtil.yesterday());
			editorService.dealWithEditorData(day);
			final int count = productRecommendDAO.getCount();
			final int page = count / Num.BATCH_SIZE;
			for (int i = 0; i <= page; i++) {
				List<String> productIds = productRecommendDAO.getProductRecommendsByStartPosAndMaxNum(i * Num.BATCH_SIZE, Num.BATCH_SIZE);
				productStatisService.dealWithStatisDate(day, productIds);
			}
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("Quantify end and cost " + costTime + "ms");
		} catch (Exception e) {
			LOGGER.error("Unexpected exception", e);
		}
	}

	public void setEditorService(EditorService editorService) {
		this.editorService = editorService;
	}

	public void setProductStatisService(ProductStatisService productStatisService) {
		this.productStatisService = productStatisService;
	}

	public void setProductRecommendDAO(ProductRecommendDAO productRecommendDAO) {
		this.productRecommendDAO = productRecommendDAO;
	}

}
