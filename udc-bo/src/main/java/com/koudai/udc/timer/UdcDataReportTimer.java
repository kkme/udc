package com.koudai.udc.timer;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.TotalNum;
import com.koudai.udc.service.DataReportService;
import com.koudai.udc.service.impl.FilePathConfiguration;
import com.koudai.udc.statis.tool.DateFormatter;
import com.koudai.udc.statis.tool.DateUtil;
import com.koudai.udc.utils.S;

public class UdcDataReportTimer {

	private static final Logger LOGGER = Logger.getLogger(UdcDataReportTimer.class);

	private FilePathConfiguration filePathConfiguration;

	private DataReportService dataReportService;

	public void run() {
		try {
			LOGGER.info("Udc data report start");
			long beginTime = System.currentTimeMillis();

			final String yesterday = new DateFormatter(DateFormatter.COMMON_DATE_FORMAT).format(DateUtil.yesterday());
			final String dayBeforeYesterday = new DateFormatter(DateFormatter.COMMON_DATE_FORMAT).format(DateUtil.dayBeforeYesterday());

			final String productDailyPath = new StringBuffer(filePathConfiguration.getSourceDir()).append(S.PRODUCT_CSV_PREFIX).append(yesterday).append(S.CSV_FILE_SUFFIX).toString();
			final String shopDailyPath = new StringBuffer(filePathConfiguration.getSourceDir()).append(S.SHOP_CSV_PREFIX).append(yesterday).append(S.CSV_FILE_SUFFIX).toString();
			final String productTotalPath = new StringBuffer(filePathConfiguration.getCodeResultDir()).append(S.TOTAL_PRODUCT_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();
			final String shopTotalPath = new StringBuffer(filePathConfiguration.getCodeResultDir()).append(S.TOTAL_SHOP_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();
			final String productTotalPathOld = new StringBuffer(filePathConfiguration.getCodeResultDir()).append(S.TOTAL_PRODUCT_PREFIX).append(dayBeforeYesterday).append(S.TXT_FILE_SUFFIX).toString();
			final String shopTotalPathOld = new StringBuffer(filePathConfiguration.getCodeResultDir()).append(S.TOTAL_SHOP_PREFIX).append(dayBeforeYesterday).append(S.TXT_FILE_SUFFIX).toString();
			final String totalReportPath = new StringBuffer(filePathConfiguration.getReportDir()).append(S.TOTAL_RESULT_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();

			final String dailyTopPath = new StringBuffer(filePathConfiguration.getShellResultDir()).append(S.TOP_10_SORT_FILE_NAME).toString();
			final String dailyTop10Path = new StringBuffer(filePathConfiguration.getCodeResultDir()).append(S.TOP_10_WITH_TYPE_PRODUCT_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();
			final String topReportPath = new StringBuffer(filePathConfiguration.getReportDir()).append(S.TOP_RESULT_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();

			final String dailyTop1000Path = new StringBuffer(filePathConfiguration.getCodeResultDir()).append(S.TOP_1000_WITH_TYPE_PRODUCT_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();

			final String apiOldPath = new StringBuffer(filePathConfiguration.getReportDir()).append(S.API_RESULT_PREFIX).append(dayBeforeYesterday).append(S.TXT_FILE_SUFFIX).toString();
			final String apiNewPath = new StringBuffer(filePathConfiguration.getShellResultDir()).append(S.API_DATA_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();
			final String apiReportPath = new StringBuffer(filePathConfiguration.getReportDir()).append(S.API_RESULT_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();

			final String apiPeakOldPath = new StringBuffer(filePathConfiguration.getReportDir()).append(S.API_PEAK_RESULT_PREFIX).append(dayBeforeYesterday).append(S.TXT_FILE_SUFFIX).toString();
			final String apiPeakNewPath = new StringBuffer(filePathConfiguration.getShellResultDir()).append(S.API_PEAK_DATA_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();
			final String apiPeakReportPath = new StringBuffer(filePathConfiguration.getReportDir()).append(S.API_PEAK_RESULT_PREFIX).append(yesterday).append(S.TXT_FILE_SUFFIX).toString();

			TotalNum productTotalNum = dataReportService.getTotalNum(productDailyPath);
			TotalNum shopTotalNum = dataReportService.getTotalNum(shopDailyPath);
			dataReportService.initTotalNumTxt(productTotalPath, productTotalNum);
			dataReportService.initTotalNumTxt(shopTotalPath, shopTotalNum);

			dataReportService.uploadDailyProductTop10AndBackup(dailyTopPath, dailyTop10Path, filePathConfiguration.getUploadTopUrl());

			dataReportService.uploadDailyProductTop1000AndBackup(dailyTopPath, dailyTop1000Path, filePathConfiguration.getUploadTopUrl(), filePathConfiguration.getAppTopCheckUrl());

			dataReportService.initTotalNumReport(productTotalPathOld, productTotalPath, shopTotalPathOld, shopTotalPath, totalReportPath);
			dataReportService.initDailyProductTop10Report(dailyTop10Path, topReportPath);

			dataReportService.initApiReport(apiOldPath, apiNewPath, apiReportPath);
			dataReportService.initApiReport(apiPeakOldPath, apiPeakNewPath, apiPeakReportPath);

			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("Udc data report end and cost " + costTime + "ms");
		} catch (Exception e) {
			LOGGER.error("Unexpected exception", e);
		}
	}

	public void setFilePathConfiguration(FilePathConfiguration filePathConfiguration) {
		this.filePathConfiguration = filePathConfiguration;
	}

	public void setDataReportService(DataReportService dataReportService) {
		this.dataReportService = dataReportService;
	}

}
