package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class GetUserCollectProductNumAction extends UserDataNumberAction {

	private static final long serialVersionUID = 3422392333012064495L;

	private static final Logger LOGGER = Logger.getLogger(GetUserCollectProductNumAction.class);

	@Override
	protected void getUserDataNumber() throws Exception {
		long beginTime = System.currentTimeMillis();
		int num = getUserCollectNum(userId, endTime);
		result = getNumberResult(S.SUCCESS_CODE, S.EMPTY_STR, num);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("getUserColletProductNum cost>>>" + costTime);
	}

	private int getUserCollectNum(String userId, Date endTime) {
		if (endTime == null) {
			return userDataService.getUserCollectNum(userId);
		}
		return userDataService.getUserCollectNum(userId, endTime);
	}

	@Override
	protected JSONObject getNumberResult(int code, String reason, int num) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONObject numberObject = new JSONObject();
			numberObject.put("collect_product_num", num);
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("num_info", numberObject);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

}
