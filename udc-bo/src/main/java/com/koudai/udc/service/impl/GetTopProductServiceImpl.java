package com.koudai.udc.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
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

import com.koudai.udc.domain.DescendingComparator;
import com.koudai.udc.service.GetTopProductService;
import com.koudai.udc.statis.tool.TypeInfoCategory;
import com.koudai.udc.utils.JsonParseUtil;
import com.koudai.udc.utils.S;

public class GetTopProductServiceImpl implements GetTopProductService {

	private static final Logger LOGGER = Logger.getLogger(GetTopProductServiceImpl.class);

	private FilePathConfiguration filePathConfiguration;

	@Override
	public Map<String, Map<String, Integer>> getTopProduct(int limit, String filePath) {
		Map<String, Map<String, Integer>> result = new HashMap<String, Map<String, Integer>>();
		for (int i = 0; i < TypeInfoCategory.STAT_TYPES.size(); i++) {
			result.put(TypeInfoCategory.STAT_TYPES.get(i), new HashMap<String, Integer>());
		}
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String line = null;
			HashMap<String, Integer> batch = new HashMap<String, Integer>();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(S.COMMA_STR);
				final String productId = values[0];
				final int count = Integer.valueOf(values[1]);
				batch.put(productId, count);
				if (batch.size() == S.THIRTY) {
					if (isCompleted(result, limit)) {
						return result;
					}
					process(result, batch, limit);
					batch = new HashMap<String, Integer>();
				}
			}
			if (batch.size() > S.ZERO_NUMBER) {
				process(result, batch, limit);
			}
			return result;
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

	private boolean isCompleted(Map<String, Map<String, Integer>> result, int limit) {
		Iterator<Entry<String, Map<String, Integer>>> itResult = result.entrySet().iterator();
		while (itResult.hasNext()) {
			Entry<String, Map<String, Integer>> next = itResult.next();
			Map<String, Integer> value = next.getValue();
			if (value.size() < limit) {
				return false;
			}
		}
		return true;
	}

	private void process(Map<String, Map<String, Integer>> result, HashMap<String, Integer> batch, int limit) {
		HashMap<String, String> temp = new HashMap<String, String>();

		StringBuffer sb = new StringBuffer();
		List<Entry<String, Integer>> sortBatch = sort(batch);
		for (Entry<String, Integer> entry : sortBatch) {
			String productId = entry.getKey();
			sb.append("product_id=").append(productId).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);

		LOGGER.info("Get product type request is < " + sb.toString() + " >");
		JSONObject response = JsonParseUtil.parseJsonToObject(filePathConfiguration.getQueryTypeUrl(), sb.toString());
		LOGGER.info("Get product type response is < " + response.toString() + " >");

		JSONObject status = response.getJSONObject("status");
		if (status.getString("code").equals(S.ZERO)) {
			JSONArray resultArray = response.getJSONArray("result");
			for (int i = 0; i < resultArray.size(); i++) {
				JSONObject resultObject = resultArray.getJSONObject(i);
				temp.put(resultObject.getString("product_id"), resultObject.getString("combine_app_id"));
			}
		}

		for (Entry<String, Integer> entry : sortBatch) {
			String productId = entry.getKey();
			int count = entry.getValue();
			String typeId = temp.get(productId);
			Map<String, Integer> typeResult = result.get(typeId);
			if (typeResult != null && typeResult.size() < limit) {
				typeResult.put(productId, count);
			}
		}
	}

	private List<Entry<String, Integer>> sort(Map<String, Integer> classifiedMap) {
		List<Entry<String, Integer>> sortedEntryList = new ArrayList<Entry<String, Integer>>(classifiedMap.entrySet());
		Collections.sort(sortedEntryList, new DescendingComparator());
		return sortedEntryList;
	}

	public void setFilePathConfiguration(FilePathConfiguration filePathConfiguration) {
		this.filePathConfiguration = filePathConfiguration;
	}

}
