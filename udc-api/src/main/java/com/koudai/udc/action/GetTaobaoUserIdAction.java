package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserTaobaoInfo;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserTaobaoInfoDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetTaobaoUserIdAction extends ActionSupport {

	private static final long serialVersionUID = -674767953370413634L;

	private static final Logger LOGGER = Logger.getLogger(GetTaobaoUserIdAction.class);

	private String userId;

	private UserTaobaoInfoDAO userTaobaoInfoDAOR;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(userId) || !S.isTaobaoUser(userId)) {
				throw new IncorrectInputParameterException("Koudai id is invalid");
			}
			LOGGER.info("Get taobao user id with koudai id < " + userId + " >");

			UserTaobaoInfo userTaobaoInfo = userTaobaoInfoDAOR.getUserTaobaoInfoByUserIdAndPlatform(userId, LoginPlatform.meitu);
			if (userTaobaoInfo == null || userTaobaoInfo.getTaobaoUserId() == null) {
				throw new IncorrectInputParameterException("Taobao user < " + userId + " > must be logged in");
			}
			result = getTaobaoUserIdResult(S.SUCCESS_CODE, S.EMPTY_STR, userTaobaoInfo.getTaobaoUserId());

			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getTaobaoUserId cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getTaobaoUserIdResult(S.ERROR_CODE, e.getMessage(), S.EMPTY_STR);
			return ERROR;
		}
	}

	private JSONObject getTaobaoUserIdResult(int code, String reason, String uid) {
		try {
			JSONObject result = new JSONObject();
			JSONObject content = new JSONObject();
			content.put("status_code", code);
			content.put("status_reason", reason);
			result.put("status", content);
			result.put("taobao_user_id", uid);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setUserTaobaoInfoDAOR(UserTaobaoInfoDAO userTaobaoInfoDAOR) {
		this.userTaobaoInfoDAOR = userTaobaoInfoDAOR;
	}

	public JSONObject getResult() {
		return result;
	}

}
