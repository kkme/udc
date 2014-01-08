package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.IosPushInfo;
import com.koudai.udc.domain.Platform;
import com.koudai.udc.domain.factory.IosPushInfoFactory;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.IosPushInfoDAO;
import com.koudai.udc.utils.DateFormatter;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetIosPushDiscountDataV2Action extends ActionSupport {

	private static final long serialVersionUID = -8614906110776908074L;

	private static final Logger LOGGER = Logger.getLogger(GetIosPushDiscountDataV2Action.class);

	private IosPushInfoDAO iosPushInfoDAOW;

	private IosPushInfoFactory iosPushInfoFactory;

	private int start = 0;

	private int end = 500;

	private Date createTime = DateUtil.setTimeOnDate(DateUtil.yesterday(), 0, 0, 0, 0);

	private String platform;

	private JSONObject result;

	@Override
	public String execute() throws Exception {
		try {
			long beginTime = System.currentTimeMillis();
			LOGGER.info("Get ios push info v2 with start pos < " + start + " > and end pos < " + end + " > and createTime < " + new DateFormatter().format(createTime) + " > and platform < " + platform + " >");
			List<IosPushInfo> dbPushInfos = iosPushInfoDAOW.getPushInfosByCreateTimeAndStartAndEndPosAndPlatform(createTime, start, end, Platform.valueOf(platform));
			for (IosPushInfo pushInfo : dbPushInfos) {
				pushInfo.push();
			}
			List<IosPushInfo> allPushInfos = addListenerPushInfos(dbPushInfos);
			result = getSuccessfulResult(allPushInfos);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getIosPushDiscountData2 cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	private List<IosPushInfo> addListenerPushInfos(List<IosPushInfo> dbPushInfos) {
		List<IosPushInfo> allPushInfos = new ArrayList<IosPushInfo>();
		if (dbPushInfos != null && !dbPushInfos.isEmpty()) {
			for (String token : S.LISTENER_TOKENS) {
				IosPushInfo iosPushInfo = iosPushInfoFactory.newInstance(S.EMPTY_STR, S.LISTENER_FIRST_PRODUCT_NAME, S.LISTENER_PRODUCTIDS, 0, token, Platform.iphone);
				allPushInfos.add(iosPushInfo);
			}
			allPushInfos.addAll(dbPushInfos);
		}
		return allPushInfos;
	}

	private JSONObject getSuccessfulResult(List<IosPushInfo> allPushInfos) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", S.SUCCESS_CODE);
			statusObject.put("status_reason", S.EMPTY_STR);
			JSONArray pushArray = new JSONArray();
			for (IosPushInfo pushInfo : allPushInfos) {
				JSONObject pushObject = new JSONObject();
				pushObject.put("productIDS", pushInfo.getProductIds());
				pushObject.put("firstProductName", pushInfo.getFirstProductName());
				pushObject.put("token", pushInfo.getToken());
				pushObject.put("platform", pushInfo.getPlatform().toString());
				pushArray.put(pushObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("ios_push_data", pushArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	private JSONObject getFailedResult(String reason) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", S.ERROR_CODE);
			statusObject.put("status_reason", reason);
			JSONArray pushArray = new JSONArray();
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("ios_push_data", pushArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setIosPushInfoDAOW(IosPushInfoDAO iosPushInfoDAOW) {
		this.iosPushInfoDAOW = iosPushInfoDAOW;
	}

	public void setIosPushInfoFactory(IosPushInfoFactory iosPushInfoFactory) {
		this.iosPushInfoFactory = iosPushInfoFactory;
	}

	public void setStartPos(int startPos) {
		this.start = startPos;
	}

	public void setEndPos(int endPos) {
		this.end = endPos;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public JSONObject getResult() {
		return result;
	}

}
