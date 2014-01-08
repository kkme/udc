package com.koudai.udc.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ShopFavoriteDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetShopFavoriteV2Action extends ActionSupport {

	private static final long serialVersionUID = -1383536390791138722L;

	private static final Logger LOGGER = Logger.getLogger(GetShopFavoriteV2Action.class);

	private ShopFavoriteDAO shopFavoriteDAOR;

	private String userId;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			LOGGER.info("User < " + userId + " > request shops with version 2");
			if (S.isInvalidValue(userId) || !S.isRealUser(userId)) {
				throw new IncorrectInputParameterException("User id is invalid");
			}
			List<IdAndDate> idAndDates = shopFavoriteDAOR.getAllShopIdsAndFavoriteTimeByUserId(userId);
			result = getShopResult(S.SUCCESS_CODE, S.EMPTY_STR, idAndDates);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getShopFavorite2 cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getShopResult(S.ERROR_CODE, e.getMessage(), S.EMPTY_ID_AND_DATE_LIST);
			return ERROR;
		}
	}

	private JSONObject getShopResult(int code, String reason, List<IdAndDate> idAndDates) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray dateArray = new JSONArray();
			for (IdAndDate idAndDate : idAndDates) {
				JSONObject dateObject = new JSONObject();
				dateObject.put("shopID", idAndDate.getId());
				dateObject.put("favoriteTime", idAndDate.getFormatterTimestamp());
				dateArray.put(dateObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("shopfavorite", dateArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setShopFavoriteDAOR(ShopFavoriteDAO shopFavoriteDAOR) {
		this.shopFavoriteDAOR = shopFavoriteDAOR;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public JSONObject getResult() {
		return result;
	}

}
