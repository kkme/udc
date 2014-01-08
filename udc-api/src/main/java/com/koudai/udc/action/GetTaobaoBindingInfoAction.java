package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.TaobaoBindingInfo;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.TaobaoBindingInfoDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetTaobaoBindingInfoAction extends ActionSupport {

	private static final long serialVersionUID = 1849868670564830146L;

	private static final Logger LOGGER = Logger.getLogger(GetTaobaoBindingInfoAction.class);

	private TaobaoBindingInfoDAO taobaoBindingInfoDAOR;

	private String userId;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			if (S.isInvalidValue(userId) || !S.isBindUser(userId)) {
				throw new IncorrectInputParameterException("User id is invalid");
			}
			long beginTime = System.currentTimeMillis();
			LOGGER.info("User < " + userId + " > try to get taobao binding info");
			getTaobaoBindingInfo();
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getTaobaoBindingInfo cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getTaobaoBindingInfoResult(S.ERROR_CODE, e.getMessage(), S.EMPTY_STR);
			return ERROR;
		}
	}

	private void getTaobaoBindingInfo() {
		TaobaoBindingInfo taobaoBindingInfo = taobaoBindingInfoDAOR.getTaobaoBindingInfoByUserId(userId);
		if (taobaoBindingInfo == null || !taobaoBindingInfo.isBound()) {
			result = getTaobaoBindingInfoResult(S.USER_NOT_BOUND, S.EMPTY_STR, S.EMPTY_STR);
			return;
		}
		result = getTaobaoBindingInfoResult(S.USER_HAS_BOUND, S.EMPTY_STR, taobaoBindingInfo.getBindId());
	}

	private JSONObject getTaobaoBindingInfoResult(int code, String reason, String bindId) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("bind_id", bindId);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setTaobaoBindingInfoDAOR(TaobaoBindingInfoDAO taobaoBindingInfoDAOR) {
		this.taobaoBindingInfoDAOR = taobaoBindingInfoDAOR;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public JSONObject getResult() {
		return result;
	}

}
