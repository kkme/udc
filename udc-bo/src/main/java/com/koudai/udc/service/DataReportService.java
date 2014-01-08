package com.koudai.udc.service;

import com.koudai.udc.domain.TotalNum;

public interface DataReportService {

	TotalNum getTotalNum(String filePath);

	void initTotalNumTxt(String filePath, TotalNum totalNum);

	void uploadDailyProductTop10AndBackup(String readPath, String writePath, String uploadUrl);

	void uploadDailyProductTop1000AndBackup(String readPath, String writePath, String uploadUrl, String checkUrl);

	void initTotalNumReport(String productOldPath, String productNewPath, String shopOldPath, String shopNewPath, String reportPath);

	void initDailyProductTop10Report(String filePath, String reportPath);

	void initApiReport(String oldPath, String newPath, String reportPath);

}
