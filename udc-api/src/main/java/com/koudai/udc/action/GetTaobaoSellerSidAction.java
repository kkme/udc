package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserTaobaoInfo;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserTaobaoInfoDAO;
import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;
import com.taobao.api.ApiException;

public class GetTaobaoSellerSidAction extends ActionSupport {

	private static final long serialVersionUID = 9125487429399211472L;

	private static final Logger LOGGER = Logger.getLogger(GetTaobaoSellerSidAction.class);

	private String userId;

	private TaobaoConfiguration taobaoConfiguration;

	private UserTaobaoInfoDAO userTaobaoInfoDAOW;

	private JSONObject result;

	@Override
	public String execute() throws Exception {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(userId) || !S.isTaobaoUser(userId)) {
				throw new IncorrectInputParameterException("userId is invalid");
			}
			LOGGER.info("Get taobao seller sid with user id < " + userId + " >");
			getSellerSid();
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getTaobaoSellerSid cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getSidResult(S.ERROR_CODE, e.getMessage(), S.EMPTY_STR);
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	public void getSellerSid() throws IncorrectInputParameterException, ApiException {
		UserTaobaoInfo userTaobaoInfo = userTaobaoInfoDAOW.getUserTaobaoInfoByUserIdAndPlatform(userId, LoginPlatform.meitu);
		if (userTaobaoInfo == null) {
			throw new IncorrectInputParameterException("User < " + userId + " > must be logged in");
		}
		if (userTaobaoInfo.getSellerSid() != null) {
			result = getSidResult(S.SUCCESS_CODE, S.EMPTY_STR, userTaobaoInfo.getSellerSid());
			return;
		}
		final String sid = TaobaoApiUtil.getShopId(taobaoConfiguration.getTaobaoApiUrl(), taobaoConfiguration.getTaobaoMeituAppKey(), taobaoConfiguration.getTaobaoMeituAppSecret(), userId);
		userTaobaoInfo.setSellerSid(sid);
		result = getSidResult(S.SUCCESS_CODE, S.EMPTY_STR, sid);
	}

	public JSONObject getSidResult(int code, String reason, String sid) {
		try {
			JSONObject result = new JSONObject();
			JSONObject content = new JSONObject();
			content.put("status_code", code);
			content.put("status_reason", reason);
			result.put("status", content);
			result.put("seller_sid", sid);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

	public void setUserTaobaoInfoDAOW(UserTaobaoInfoDAO userTaobaoInfoDAOW) {
		this.userTaobaoInfoDAOW = userTaobaoInfoDAOW;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public JSONObject getResult() {
		return result;
	}

}
