package com.koudai.udc.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.UserTestInfo;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserTestInfoDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetUserTestInfoAction extends ActionSupport {

	private static final long serialVersionUID = -9146936237323891572L;

	private static final Logger LOGGER = Logger.getLogger(GetUserTestInfoAction.class);

	private static final List<UserTestInfo> EMPTY_LIST = new ArrayList<UserTestInfo>();

	private String userId;

	private UserTestInfoDAO userTestInfoDAOR;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(userId) || !S.isRealUser(userId)) {
				throw new IncorrectInputParameterException("User id is invalid");
			}
			LOGGER.info("GetUserTestInfo request userID is : " + userId);
			List<UserTestInfo> userTestInfoList = userTestInfoDAOR.getUserTestInfoListByUserId(userId);
			result = getTestInfoResult(S.SUCCESS_CODE, S.EMPTY_STR, userTestInfoList);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getUserTestInfo cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getTestInfoResult(S.ERROR_CODE, e.getMessage(), EMPTY_LIST);
			return ERROR;
		}
	}

	private JSONObject getTestInfoResult(int code, String reason, List<UserTestInfo> userTestInfoList) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray testinfoArray = new JSONArray();
			for (UserTestInfo userTestInfo : userTestInfoList) {
				JSONObject idObject = new JSONObject();
				idObject.put("questionid", userTestInfo.getQuestionId());
				idObject.put("answerid", userTestInfo.getAnswerId());
				testinfoArray.put(idObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("testinfo", testinfoArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public JSONObject getResult() {
		return result;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setUserTestInfoDAOR(UserTestInfoDAO userTestInfoDAOR) {
		this.userTestInfoDAOR = userTestInfoDAOR;
	}

}
