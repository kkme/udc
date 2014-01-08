package com.koudai.udc.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.UserTestInfo;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ProductCollectDAO;
import com.koudai.udc.persistence.UserTestInfoDAO;
import com.koudai.udc.service.impl.MyStreetConfiguration;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class IsUserColdStartAction extends ActionSupport {

	private static final long serialVersionUID = -4383748673201841355L;

	private static final Logger LOGGER = Logger.getLogger(IsUserColdStartAction.class);

	private String userId;

	private ProductCollectDAO productCollectDAOR;

	private UserTestInfoDAO userTestInfoDAOR;

	private MyStreetConfiguration myStreetConfiguration;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(userId) || !S.isRealUser(userId)) {
				throw new IncorrectInputParameterException("User id is invalid");
			}
			LOGGER.info("Is user cold start with id < " + userId + " >");
			long endTime = System.currentTimeMillis();
			initUserColdStartResult();
			long costTime = endTime - beginTime;
			LOGGER.info("isUserGoldStart cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getUserColdStartResult(S.ERROR_START, e.getMessage(), S.EMPTY_STR);
			return ERROR;
		}
	}

	private void initUserColdStartResult() {
		final String dressStyle = getUserDressSytle();
		if (myStreetConfiguration.getColdList().contains(userId.toLowerCase())) {
			result = getUserColdStartResult(S.COLD_START, S.EMPTY_STR, dressStyle);
			return;
		}
		int total = productCollectDAOR.getCollectNumByUserIdAndEndTime(userId, DateUtil.maxYesterday());
		if (total < 8) {
			result = getUserColdStartResult(S.COLD_START, S.EMPTY_STR, dressStyle);
		} else {
			result = getUserColdStartResult(S.HOT_START, S.EMPTY_STR, dressStyle);
		}
	}

	private String getUserDressSytle() {
		List<UserTestInfo> testInfos = userTestInfoDAOR.getUserTestInfoListByUserId(userId);
		for (UserTestInfo userTestInfo : testInfos) {
			if (S.STYLE_QID.contains(userTestInfo.getQuestionId())) {
				return userTestInfo.getFormatDressStyle();
			}
		}
		return null;
	}

	private JSONObject getUserColdStartResult(int code, String reason, String dressStyle) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONObject infoObject = new JSONObject();
			infoObject.put("dress_style", dressStyle);
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("testinfo", infoObject);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setProductCollectDAOR(ProductCollectDAO productCollectDAOR) {
		this.productCollectDAOR = productCollectDAOR;
	}

	public void setUserTestInfoDAOR(UserTestInfoDAO userTestInfoDAOR) {
		this.userTestInfoDAOR = userTestInfoDAOR;
	}

	public void setMyStreetConfiguration(MyStreetConfiguration myStreetConfiguration) {
		this.myStreetConfiguration = myStreetConfiguration;
	}

	public JSONObject getResult() {
		return result;
	}

}
