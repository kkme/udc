package com.koudai.udc.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.UserAlgorithmInfo;
import com.koudai.udc.domain.factory.UserAlgorithmInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.UserAlgorithmInfoDAO;
import com.koudai.udc.service.impl.MyStreetConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UserAlgorithmInfoKey;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetUserAlgorithmInfoAction extends ActionSupport {

	private static final long serialVersionUID = -7306862782266689672L;

	private static final Logger LOGGER = Logger.getLogger(GetUserAlgorithmInfoAction.class);

	private UserAlgorithmInfoDAO userAlgorithmInfoDAOR;

	private UserAlgorithmInfoFactory userAlgorithmInfoFactory;

	private MyStreetConfiguration myStreetConfiguration;

	private String userId;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			if (S.isInvalidValue(userId) || !S.isRealUser(userId)) {
				throw new IncorrectInputParameterException("User id is invalid");
			}
			UserAlgorithmInfo userAlgorithmInfo = initCurrentUserAlgorithmInfo();
			Map<String, String> infos = new HashMap<String, String>();
			infos.put(UserAlgorithmInfoKey.USER_ID, userAlgorithmInfo.getUserId());
			infos.put(UserAlgorithmInfoKey.ALGORITHM_VERSION, userAlgorithmInfo.getFormatAlgorithmVersion());
			infos.put(UserAlgorithmInfoKey.SESSION_VERSION, userAlgorithmInfo.getSessionVersion());
			infos.put(UserAlgorithmInfoKey.USER_STYLE, userAlgorithmInfo.getUserStyle());
			result = getUserAlgorithmInfoResult(S.SUCCESS_CODE, S.EMPTY_STR, infos);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getUserAlgorithmInfo cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getUserAlgorithmInfoResult(S.ERROR_CODE, e.getMessage(), S.EMPTY_STR_MAP);
			return ERROR;
		}
	}

	private UserAlgorithmInfo initCurrentUserAlgorithmInfo() throws IncorrectInputParameterException {
		Date currentDate = new Date();
		UserAlgorithmInfo userAlgorithmInfo = userAlgorithmInfoDAOR.getUserAlgorithmInfoByUserId(userId);
		if (userAlgorithmInfo == null) {
			return userAlgorithmInfoFactory.newInstance(S.EMPTY_STR, 0, S.EMPTY_STR, S.EMPTY_STR);
		}
		if (userAlgorithmInfo.getAlgorithmVersion() != 5 || (userAlgorithmInfo.getAlgorithmVersion() == 5 && ((currentDate.getTime() - userAlgorithmInfo.getUpdateTime().getTime()) > myStreetConfiguration.getSessionDelay()))) {
			userAlgorithmInfo.setSessionVersion(null);
		}
		return userAlgorithmInfo;
	}

	private JSONObject getUserAlgorithmInfoResult(int code, String reason, Map<String, String> infos) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONObject infoObject = new JSONObject();
			Iterator<Entry<String, String>> it = infos.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> next = it.next();
				infoObject.put(next.getKey(), next.getValue());
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("algorithminfo", infoObject);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setUserAlgorithmInfoDAOR(UserAlgorithmInfoDAO userAlgorithmInfoDAOR) {
		this.userAlgorithmInfoDAOR = userAlgorithmInfoDAOR;
	}

	public void setUserAlgorithmInfoFactory(UserAlgorithmInfoFactory userAlgorithmInfoFactory) {
		this.userAlgorithmInfoFactory = userAlgorithmInfoFactory;
	}

	public void setMyStreetConfiguration(MyStreetConfiguration myStreetConfiguration) {
		this.myStreetConfiguration = myStreetConfiguration;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public JSONObject getResult() {
		return result;
	}

}
