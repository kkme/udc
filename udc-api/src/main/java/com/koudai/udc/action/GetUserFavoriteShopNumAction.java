package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class GetUserFavoriteShopNumAction extends UserDataNumberAction {

	private static final long serialVersionUID = -9187104979714384468L;

	private static final Logger LOGGER = Logger.getLogger(GetUserFavoriteShopNumAction.class);

	@Override
	protected void getUserDataNumber() throws Exception {
		long beginTime = System.currentTimeMillis();
		int num = userDataService.getUserFavoriteNum(userId);
		result = getNumberResult(S.SUCCESS_CODE, S.EMPTY_STR, num);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("getUserFavoriteShopNum cost>>>" + costTime);
	}

	@Override
	protected JSONObject getNumberResult(int code, String reason, int num) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONObject numberObject = new JSONObject();
			numberObject.put("favorite_shop_num", num);
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
