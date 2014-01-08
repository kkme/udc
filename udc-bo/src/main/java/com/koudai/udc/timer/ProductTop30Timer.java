package com.koudai.udc.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.koudai.udc.service.GetTopProductService;
import com.koudai.udc.service.impl.FilePathConfiguration;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.JsonParseUtil;
import com.koudai.udc.utils.S;

public class ProductTop30Timer {

	private static final Logger LOGGER = Logger.getLogger(ProductTop30Timer.class);

	private GetTopProductService getTopProductService;

	private FilePathConfiguration filePathConfiguration;

	public void run() {
		try {
			LOGGER.info("UploadProductCollectTop30 start");
			long beginTime = System.currentTimeMillis();
			StringBuffer path = new StringBuffer(filePathConfiguration.getShellResultDir());
			path.append(S.TOP_30_SORT_FILE_NAME);
			Map<String, Map<String, Integer>> result = getTopProductService.getTopProduct(S.THIRTY, path.toString());
			Iterator<Entry<String, Map<String, Integer>>> it = result.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Map<String, Integer>> next = it.next();
				String typeId = next.getKey();
				Map<String, Integer> resultMap = next.getValue();
				uploadData(typeId, resultMap);
			}
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("UploadProductCollectTop30 end and cost " + costTime + "ms");
		} catch (Exception e) {
			LOGGER.error("Unexpected exception", e);
		}
	}

	private void uploadData(String typeId, Map<String, Integer> resultMap) {
		Iterator<Entry<String, Integer>> iterator = resultMap.entrySet().iterator();
		List<Map<String, String>> productInfoList = new ArrayList<Map<String, String>>();
		int year = DateUtil.yearOfLastWeek();
		int week = DateUtil.lastWeek();
		while (iterator.hasNext()) {
			Entry<String, Integer> entry = iterator.next();
			final String productId = entry.getKey();
			Integer count = entry.getValue();
			Map<String, String> pushMap = new HashMap<String, String>();
			pushMap.put("productID", productId);
			pushMap.put("collectedNum", String.valueOf(count));
			pushMap.put("typeID", typeId);
			pushMap.put("week", String.valueOf(week));
			pushMap.put("year", String.valueOf(year));
			productInfoList.add(pushMap);
		}
		JSONObject title = new JSONObject();
		title.put("productcollectweektop", JSONArray.fromObject(productInfoList));
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("upload_productcollect_week_top_in", title.toString());
		JSONObject response = JsonParseUtil.parseJsonToObject(filePathConfiguration.getUploadTop30Url(), requestParameters);
		LOGGER.info("Upload top30 info response is : " + response);
	}

	public void setGetTopProductService(GetTopProductService getTopProductService) {
		this.getTopProductService = getTopProductService;
	}

	public void setFilePathConfiguration(FilePathConfiguration filePathConfiguration) {
		this.filePathConfiguration = filePathConfiguration;
	}

}
