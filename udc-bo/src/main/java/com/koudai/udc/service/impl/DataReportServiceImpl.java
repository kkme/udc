package com.koudai.udc.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.csvreader.CsvReader;
import com.koudai.udc.domain.Api;
import com.koudai.udc.domain.DescendingComparator;
import com.koudai.udc.domain.TotalNum;
import com.koudai.udc.service.DataReportService;
import com.koudai.udc.service.GetTopProductService;
import com.koudai.udc.statis.tool.TypeInfoCategory;
import com.koudai.udc.utils.FileUtil;
import com.koudai.udc.utils.JsonParseUtil;
import com.koudai.udc.utils.NumUtil;
import com.koudai.udc.utils.S;

public class DataReportServiceImpl implements DataReportService {

	private static final Logger LOGGER = Logger.getLogger(DataReportServiceImpl.class);

	private GetTopProductService getTopProductService;

	@Override
	public TotalNum getTotalNum(String filePath) {
		CsvReader reader = null;
		int taobaoNum = 0;
		int sinaNum = 0;
		int qqNum = 0;
		int realNum = 0;
		int anonyNum = 0;
		int allNum = 0;
		try {
			reader = new CsvReader(filePath, S.COMMA, Charset.forName(S.CHAR_SET));
			while (reader.readRecord()) {
				String[] values = reader.getValues();
				String userId = values[2];
				allNum++;
				if (S.isRealUser(userId)) {
					realNum++;
					if (S.isTaobaoUser(userId)) {
						taobaoNum++;
					} else if (S.isSinaUser(userId)) {
						sinaNum++;
					} else if (S.isQQUser(userId)) {
						qqNum++;
					}
				} else if (S.isAnonymousUser(userId)) {
					anonyNum++;
				}
			}
			return new TotalNum(taobaoNum, sinaNum, qqNum, realNum, anonyNum, allNum);
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	@Override
	public void initTotalNumTxt(String filePath, TotalNum totalNum) {
		StringBuffer content = new StringBuffer();
		content.append("taobao").append(S.COMMA).append(totalNum.getTaobaoNum()).append(S.LF);
		content.append("sina").append(S.COMMA).append(totalNum.getSinaNum()).append(S.LF);
		content.append("qq").append(S.COMMA).append(totalNum.getQqNum()).append(S.LF);
		content.append("real").append(S.COMMA).append(totalNum.getRealNum()).append(S.LF);
		content.append("anony").append(S.COMMA).append(totalNum.getAnonyNum()).append(S.LF);
		content.append("all").append(S.COMMA).append(totalNum.getAllNum());
		FileUtil.write(filePath, content.toString());
	}

	@Override
	public void uploadDailyProductTop10AndBackup(String readPath, String writePath, String uploadUrl) {
		StringBuffer content = new StringBuffer();
		Map<String, Map<String, Integer>> result = getTopProductService.getTopProduct(S.TEN, readPath);
		Iterator<Entry<String, Map<String, Integer>>> it = result.entrySet().iterator();
		while (it.hasNext()) {
			List<Map<String, String>> uploadList = new ArrayList<Map<String, String>>();

			Entry<String, Map<String, Integer>> next = it.next();
			String typeId = next.getKey();
			Map<String, Integer> values = next.getValue();
			Iterator<Entry<String, Integer>> itValue = values.entrySet().iterator();

			while (itValue.hasNext()) {
				Entry<String, Integer> nextValue = itValue.next();
				String productId = nextValue.getKey();
				int count = nextValue.getValue();

				Map<String, String> uploadMap = new HashMap<String, String>();
				uploadMap.put("productID", productId);
				uploadMap.put("collectedNum", String.valueOf(count));
				uploadMap.put("typeID", String.valueOf(typeId));
				uploadMap.put("useType", S.ZERO);
				uploadList.add(uploadMap);

				content.append(productId).append(S.COMMA);
				content.append(String.valueOf(count)).append(S.COMMA);
				content.append(typeId).append(S.LF);
			}

			JSONObject title = new JSONObject();
			title.put("productcollecttop", JSONArray.fromObject(uploadList));
			Map<String, String> requestParameters = new HashMap<String, String>();
			requestParameters.put("upload_productcollect_top_in", title.toString());
			JSONObject response = JsonParseUtil.parseJsonToObject(uploadUrl, requestParameters);
			LOGGER.info("Upload product collect top 10 response is : " + response);
		}
		if (content.length() > 0) {
			content.deleteCharAt(content.length() - 1);
		}
		FileUtil.write(writePath, content.toString());
	}

	@Override
	public void uploadDailyProductTop1000AndBackup(String readPath, String writePath, String uploadUrl, String checkUrl) {
		StringBuffer content = new StringBuffer();
		Map<String, Map<String, Integer>> result = getTopProductService.getTopProduct(S.ONE_THOUSAND, readPath);
		Iterator<Entry<String, Map<String, Integer>>> it = result.entrySet().iterator();
		while (it.hasNext()) {
			List<Map<String, String>> uploadList = new ArrayList<Map<String, String>>();
			StringBuffer checkContent = new StringBuffer();

			Entry<String, Map<String, Integer>> next = it.next();
			String typeId = next.getKey();
			Map<String, Integer> values = next.getValue();
			Iterator<Entry<String, Integer>> itValue = values.entrySet().iterator();

			while (itValue.hasNext()) {
				Entry<String, Integer> nextValue = itValue.next();
				String productId = nextValue.getKey();
				int count = nextValue.getValue();

				Map<String, String> uploadMap = new HashMap<String, String>();
				uploadMap.put("productID", productId);
				uploadMap.put("collectedNum", String.valueOf(count));
				uploadMap.put("typeID", String.valueOf(typeId));
				uploadMap.put("useType", S.TWO);
				uploadList.add(uploadMap);

				checkContent.append("product_id=").append(productId).append("&");

				content.append(productId).append(S.COMMA);
				content.append(String.valueOf(count)).append(S.COMMA);
				content.append(typeId).append(S.LF);
			}

			JSONObject title = new JSONObject();
			title.put("productcollecttop", JSONArray.fromObject(uploadList));
			Map<String, String> requestParameters = new HashMap<String, String>();
			requestParameters.put("upload_productcollect_top_in", title.toString());
			JSONObject response = JsonParseUtil.parseJsonToObject(uploadUrl, requestParameters);
			LOGGER.info("Upload product collect top 1000 response is : " + response);

			if (checkContent.length() > 0) {
				checkContent.deleteCharAt(checkContent.length() - 1);
				JSONObject checkResponse = JsonParseUtil.parseJsonToObject(checkUrl, checkContent.toString());
				LOGGER.info("App server top 1000 check response is : " + checkResponse);
			}
		}
		if (content.length() > 0) {
			content.deleteCharAt(content.length() - 1);
		}
		FileUtil.write(writePath, content.toString());
	}

	@Override
	public void initTotalNumReport(String productOldPath, String productNewPath, String shopOldPath, String shopNewPath, String reportPath) {
		Map<String, Integer> productOldMap = initTotalNumMap(productOldPath);
		Map<String, Integer> productNewMap = initTotalNumMap(productNewPath);
		Map<String, Integer> shopOldMap = initTotalNumMap(shopOldPath);
		Map<String, Integer> shopNewMap = initTotalNumMap(shopNewPath);

		StringBuffer content = new StringBuffer();
		content.append("Product").append(S.LF);
		for (String tag : S.TOTAL_NUM_TAGS) {
			int newCount = productNewMap.get(tag);
			int oldCount = productOldMap.get(tag);
			content.append(tag).append(S.COMMA).append(newCount).append(S.COMMA).append(NumUtil.getPercentage(newCount, oldCount)).append(S.LF);
		}
		content.append("Shop").append(S.LF);
		for (String tag : S.TOTAL_NUM_TAGS) {
			int newCount = shopNewMap.get(tag);
			int oldCount = shopOldMap.get(tag);
			content.append(tag).append(S.COMMA).append(newCount).append(S.COMMA).append(NumUtil.getPercentage(newCount, oldCount)).append(S.LF);
		}

		FileUtil.write(reportPath, content.toString());
	}

	@Override
	public void initDailyProductTop10Report(String filePath, String reportPath) {
		Map<String, Integer> girl = new HashMap<String, Integer>();
		Map<String, Integer> man = new HashMap<String, Integer>();
		Map<String, Integer> woman = new HashMap<String, Integer>();
		Map<String, Integer> baby = new HashMap<String, Integer>();
		Map<String, Integer> technology = new HashMap<String, Integer>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(S.COMMA_STR);
				String productId = values[0];
				int count = Integer.valueOf(values[1]);
				String typeId = values[2];
				if (TypeInfoCategory.GIRL.equals(typeId)) {
					girl.put(productId, count);
				} else if (TypeInfoCategory.MAN.equals(typeId)) {
					man.put(productId, count);
				} else if (TypeInfoCategory.WOMAN.equals(typeId)) {
					woman.put(productId, count);
				} else if (TypeInfoCategory.BABY.equals(typeId)) {
					baby.put(productId, count);
				} else if (TypeInfoCategory.TECHNOLOGY.equals(typeId)) {
					technology.put(productId, count);
				}
			}
			StringBuffer content = new StringBuffer();
			initTopContent("Girl", girl, content);
			initTopContent("Man", man, content);
			initTopContent("Woman", woman, content);
			initTopContent("Baby", baby, content);
			initTopContent("Technology", technology, content);
			FileUtil.write(reportPath, content.toString());
		} catch (Exception e) {
			LOGGER.error("Read file failed", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e1) {
				LOGGER.error("Close file reader failed", e1);
			}
		}
	}

	private void initTopContent(String title, Map<String, Integer> unsortedMap, StringBuffer content) {
		List<Entry<String, Integer>> sortedList = new ArrayList<Entry<String, Integer>>(unsortedMap.entrySet());
		Collections.sort(sortedList, new DescendingComparator());
		content.append(title).append(S.LF);
		for (Entry<String, Integer> entry : sortedList) {
			final String taoBaoId = entry.getKey().split("taobao")[1];
			content.append(entry.getKey()).append(S.COMMA).append(String.valueOf(entry.getValue())).append(S.COMMA);
			content.append(S.TAOBAO_ITEM_URL_PREFIX).append(taoBaoId).append(S.LF);
		}
	}

	@Override
	public void initApiReport(String oldPath, String newPath, String reportPath) {
		Map<String, Api> beforeData = getApiData(oldPath);
		Map<String, Api> yesterData = getApiDataByDay(newPath);
		StringBuffer result = new StringBuffer();
		Iterator<Entry<String, Api>> it = yesterData.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Api> next = it.next();
			Api api = next.getValue();
			result.append(api.getName()).append(S.COMMA);
			result.append(api.getCount()).append(S.COMMA);
			result.append(api.getFormatterTotalTime()).append(S.COMMA);
			result.append(api.getAverage()).append(S.COMMA);
			Api beforeApi = beforeData.get(next.getKey());
			if (beforeApi == null) {
				result.append(S.NA).append(S.LF);
			} else {
				result.append(api.getPercentage(beforeApi.getCount())).append(S.LF);
			}
		}
		FileUtil.write(reportPath, result.toString());
	}

	private Map<String, Integer> initTotalNumMap(String filePath) {
		Map<String, Integer> tempMap = new HashMap<String, Integer>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(S.COMMA_STR);
				String tag = values[0];
				int count = Integer.valueOf(values[1]);
				tempMap.put(tag, count);
			}
			return tempMap;
		} catch (Exception e) {
			LOGGER.error("Read file failed", e);
			return null;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e1) {
				LOGGER.error("Close file reader failed", e1);
			}
		}
	}

	private Map<String, Api> getApiData(String filePath) {
		Map<String, Api> data = new HashMap<String, Api>();
		List<String> multipleLine = FileUtil.readMultipleLine(filePath);
		for (String singleLine : multipleLine) {
			if (S.EMPTY_STR.equals(singleLine.trim())) {
				continue;
			}
			String[] apiData = singleLine.split(S.COMMA_STR);
			String name = apiData[0].trim();
			int count = Integer.valueOf(apiData[1].trim());
			int totalTime = Integer.valueOf(apiData[2].replace(S.TIME_SUFFIX, S.EMPTY_STR).trim());
			data.put(name, new Api(name, count, totalTime));
		}
		return data;
	}

	private Map<String, Api> getApiDataByDay(String filePath) {
		Map<String, Api> result = new HashMap<String, Api>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] apiData = line.split("cost>>>");
				String apiName = apiData[0].trim();
				int amount = Integer.valueOf(apiData[1].trim());
				if (result.containsKey(apiName)) {
					Api api = result.get(apiName);
					api.add(amount);
					result.put(apiName, api);
				} else {
					result.put(apiName, new Api(apiName, amount));
				}
			}
		} catch (Exception e) {
			LOGGER.error("Read file failed", e);
			return null;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (Exception e1) {
				LOGGER.error("Close file reader failed", e1);
			}
		}
		return result;
	}

	public void setGetTopProductService(GetTopProductService getTopProductService) {
		this.getTopProductService = getTopProductService;
	}

}
