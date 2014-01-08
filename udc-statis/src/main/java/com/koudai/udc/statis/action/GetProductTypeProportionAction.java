package com.koudai.udc.statis.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.koudai.udc.statis.dao.ProductRecommendDAO;
import com.koudai.udc.statis.dao.ProductStatisPeriodDAO;
import com.koudai.udc.statis.dao.ProductTypeDAO;
import com.koudai.udc.statis.domain.ProductRecommend;
import com.koudai.udc.statis.domain.ProductStatisPeriod;
import com.koudai.udc.statis.tool.DateFormatter;
import com.koudai.udc.statis.tool.DateUtil;
import com.koudai.udc.statis.tool.TypeInfoCategory;
import com.koudai.udc.statis.utils.NumUtil;
import com.koudai.udc.statis.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetProductTypeProportionAction extends ActionSupport {

	private static final long serialVersionUID = 2207050328431122133L;

	private static final Logger LOGGER = Logger.getLogger(GetProductTypeProportionAction.class);

	private static final DateFormatter DATE_FORMAT = new DateFormatter();

	private ProductRecommendDAO productRecommendDAO;

	private ProductStatisPeriodDAO productStatisPeriodDAO;

	private ProductTypeDAO productTypeDAO;

	private JSONObject result;

	private Date startTime;

	private Date endTime;

	@Override
	public String execute() {
		try {
			LOGGER.info("GetProductTypeProportion with startTime < " + DATE_FORMAT.format(startTime) + " > and endTime < " + DATE_FORMAT.format(endTime) + " >");
			long beginTime = System.currentTimeMillis();
			Map<String, Integer> girlPush = new HashMap<String, Integer>();
			Map<String, Integer> girlClick = new HashMap<String, Integer>();
			Map<String, Integer> girlCollect = new HashMap<String, Integer>();
			Map<String, Integer> girlPurchase = new HashMap<String, Integer>();
			initAllGirlMap(girlPush, girlClick, girlCollect, girlPurchase);
			Map<String, Integer> manPush = new HashMap<String, Integer>();
			Map<String, Integer> manClick = new HashMap<String, Integer>();
			Map<String, Integer> manCollect = new HashMap<String, Integer>();
			Map<String, Integer> manPurchase = new HashMap<String, Integer>();
			initAllManMap(manPush, manClick, manCollect, manPurchase);

			List<ProductRecommend> recommends = productRecommendDAO.getProductRecommendsByUserIdAndStartAndEndTime(null, startTime, endTime);
			for (ProductRecommend recommend : recommends) {
				List<ProductStatisPeriod> periods = productStatisPeriodDAO.getProductStatisPeriodsByProductIdAndStartAndEndTime(recommend.getProductId(), startTime, endTime);
				int clickedNum = 0;
				int collectedNum = 0;
				int purchasedNum = 0;
				for (ProductStatisPeriod period : periods) {
					clickedNum = clickedNum + period.getClickedNum();
					collectedNum = collectedNum + period.getCollectedNum();
					purchasedNum = purchasedNum + period.getPurchasedNum();
				}
				List<String> typeIds = productTypeDAO.getTypeIdsByProductId(recommend.getProductId());
				for (String typeId : typeIds) {
					Set<String> girlSet = TypeInfoCategory.GIRL_TYPES.keySet();
					if (girlSet.contains(typeId)) {
						girlPush.put(typeId, girlPush.get(typeId) + 1);
						girlClick.put(typeId, girlClick.get(typeId) + clickedNum);
						girlCollect.put(typeId, girlCollect.get(typeId) + collectedNum);
						girlPurchase.put(typeId, girlPurchase.get(typeId) + purchasedNum);
					}
					Set<String> manSet = TypeInfoCategory.MAN_TYPES.keySet();
					if (manSet.contains(typeId)) {
						manPush.put(typeId, manPush.get(typeId) + 1);
						manClick.put(typeId, manClick.get(typeId) + clickedNum);
						manCollect.put(typeId, manCollect.get(typeId) + collectedNum);
						manPurchase.put(typeId, manPurchase.get(typeId) + purchasedNum);
					}
				}
			}

			Map<String, Integer> girlTotal = new HashMap<String, Integer>();
			initTotalMap(girlTotal, girlPush, girlClick, girlCollect, girlPurchase);
			Map<String, Integer> manTotal = new HashMap<String, Integer>();
			initTotalMap(manTotal, manPush, manClick, manCollect, manPurchase);

			result = getSuccessfulResult(girlTotal, girlPush, girlClick, girlCollect, girlPurchase, manTotal, manPush, manClick, manCollect, manPurchase);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getProductTypeProportion cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			return ERROR;
		}
	}

	private void initAllGirlMap(Map<String, Integer> girlPush, Map<String, Integer> girlClick, Map<String, Integer> girlCollect, Map<String, Integer> girlPurchase) {
		initGirlMap(girlPush);
		initGirlMap(girlClick);
		initGirlMap(girlCollect);
		initGirlMap(girlPurchase);
	}

	private void initGirlMap(Map<String, Integer> girlMap) {
		Set<String> manTypes = TypeInfoCategory.GIRL_TYPES.keySet();
		for (String typeId : manTypes) {
			girlMap.put(typeId, 0);
		}
	}

	private void initAllManMap(Map<String, Integer> manPush, Map<String, Integer> manClick, Map<String, Integer> manCollect, Map<String, Integer> manPurchase) {
		initManMap(manPush);
		initManMap(manClick);
		initManMap(manCollect);
		initManMap(manPurchase);
	}

	private void initManMap(Map<String, Integer> manMap) {
		Set<String> manTypes = TypeInfoCategory.MAN_TYPES.keySet();
		for (String typeId : manTypes) {
			manMap.put(typeId, 0);
		}
	}

	private void initTotalMap(Map<String, Integer> total, Map<String, Integer> push, Map<String, Integer> click, Map<String, Integer> collect, Map<String, Integer> purchase) {
		int totalPush = 0;
		Iterator<Entry<String, Integer>> itPush = push.entrySet().iterator();
		while (itPush.hasNext()) {
			Entry<String, Integer> entry = itPush.next();
			totalPush = totalPush + entry.getValue();
		}
		int totalClick = 0;
		Iterator<Entry<String, Integer>> itClick = click.entrySet().iterator();
		while (itClick.hasNext()) {
			Entry<String, Integer> entry = itClick.next();
			totalClick = totalClick + entry.getValue();
		}
		int totalCollect = 0;
		Iterator<Entry<String, Integer>> itCollect = collect.entrySet().iterator();
		while (itCollect.hasNext()) {
			Entry<String, Integer> entry = itCollect.next();
			totalCollect = totalCollect + entry.getValue();
		}
		int totalPurchase = 0;
		Iterator<Entry<String, Integer>> itPurchase = purchase.entrySet().iterator();
		while (itPurchase.hasNext()) {
			Entry<String, Integer> entry = itPurchase.next();
			totalPurchase = totalPurchase + entry.getValue();
		}
		total.put(S.PUSH_TAG, totalPush);
		total.put(S.CLICK_TAG, totalClick);
		total.put(S.COLLECT_TAG, totalCollect);
		total.put(S.PURCHASE_TAG, totalPurchase);
	}

	private JSONObject getSuccessfulResult(Map<String, Integer> girlTotal, Map<String, Integer> girlPush, Map<String, Integer> girlClick, Map<String, Integer> girlCollect, Map<String, Integer> girlPurchase, Map<String, Integer> manTotal, Map<String, Integer> manPush, Map<String, Integer> manClick, Map<String, Integer> manCollect, Map<String, Integer> manPurchase) {
		try {
			JSONObject result = new JSONObject();
			JSONObject status = new JSONObject();
			status.put("status_code", S.SUCCESS_CODE);
			status.put("status_reason", S.EMPTY_STR);
			JSONObject content = new JSONObject();
			JSONObject manContent = new JSONObject();
			initInfoObject("recommend_info", "recommend_num", "recommend_proportion", S.PUSH_TAG, manContent, manTotal, manPush, TypeInfoCategory.MAN_TYPES);
			initInfoObject("clicked_info", "clicked_num", "clicked_proportion", S.CLICK_TAG, manContent, manTotal, manClick, TypeInfoCategory.MAN_TYPES);
			initInfoObject("collected_info", "clollected_num", "collected_proportion", S.COLLECT_TAG, manContent, manTotal, manCollect, TypeInfoCategory.MAN_TYPES);
			initInfoObject("purchased_info", "purchased_num", "purchased_proportion", S.PURCHASE_TAG, manContent, manTotal, manPurchase, TypeInfoCategory.MAN_TYPES);
			content.put("product_man", manContent);
			JSONObject girlContent = new JSONObject();
			initInfoObject("recommend_info", "recommend_num", "recommend_proportion", S.PUSH_TAG, girlContent, girlTotal, girlPush, TypeInfoCategory.GIRL_TYPES);
			initInfoObject("clicked_info", "clicked_num", "clicked_proportion", S.CLICK_TAG, girlContent, girlTotal, girlClick, TypeInfoCategory.GIRL_TYPES);
			initInfoObject("collected_info", "clollected_num", "collected_proportion", S.COLLECT_TAG, girlContent, girlTotal, girlCollect, TypeInfoCategory.GIRL_TYPES);
			initInfoObject("purchased_info", "purchased_num", "purchased_proportion", S.PURCHASE_TAG, girlContent, girlTotal, girlPurchase, TypeInfoCategory.GIRL_TYPES);
			content.put("product_women", girlContent);
			result.put("status", status);
			result.put("product_type_proportion", content);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	private void initInfoObject(String contentKey, String numKey, String proKey, String totalKey, JSONObject content, Map<String, Integer> total, Map<String, Integer> single, Map<String, String> category) {
		try {
			JSONObject infoObject = new JSONObject();
			JSONObject numObject = new JSONObject();
			numObject.put("total_num", total.get(totalKey));
			JSONArray proObject = new JSONArray();
			Iterator<Entry<String, Integer>> it = single.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Integer> entry = it.next();
				String typeId = entry.getKey();
				int count = entry.getValue();
				JSONObject perObject = new JSONObject();
				perObject.put("type_name", category.get(typeId));
				perObject.put("total_num", count);
				perObject.put("proportion", NumUtil.getPercentage(count, total.get(totalKey)));
				proObject.put(perObject);
			}
			infoObject.put(numKey, numObject);
			infoObject.put(proKey, proObject);
			content.put(contentKey, infoObject);
		} catch (JSONException e) {
			LOGGER.error(e);
		}
	}

	private JSONObject getFailedResult(String reason) {
		try {
			JSONObject result = new JSONObject();
			JSONObject status = new JSONObject();
			status.put("status_code", S.ERROR_CODE);
			status.put("status_reason", reason);
			JSONObject content = new JSONObject();
			result.put("status", status);
			result.put("product_type_proportion", content);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setProductRecommendDAO(ProductRecommendDAO productRecommendDAO) {
		this.productRecommendDAO = productRecommendDAO;
	}

	public void setProductStatisPeriodDAO(ProductStatisPeriodDAO productStatisPeriodDAO) {
		this.productStatisPeriodDAO = productStatisPeriodDAO;
	}

	public void setProductTypeDAO(ProductTypeDAO productTypeDAO) {
		this.productTypeDAO = productTypeDAO;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setStartTime(Date startTime) {
		this.startTime = DateUtil.setTimeOnDate(startTime, 0, 0, 0);
	}

	public void setEndTime(Date endTime) {
		this.endTime = DateUtil.setTimeOnDate(endTime, 23, 59, 59);
	}

}
