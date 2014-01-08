package com.koudai.udc.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.Platform;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.DeviceInfoDAO;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetDeviceTokenAction extends ActionSupport {

	private static final long serialVersionUID = 8618921290042517542L;

	private static final Logger LOGGER = Logger.getLogger(GetDeviceTokenAction.class);

	private DeviceInfoDAO deviceInfoDAOR;

	private String fromVersion;

	private String toVersion;

	private String platform;

	private int start;

	private int max;

	private JSONObject result;

	@Override
	public String execute() {
		try {
			long beginTime = System.currentTimeMillis();
			LOGGER.info("Get device token with fromVersion < " + fromVersion + " > toVersion < " + toVersion + " > platform < " + platform + " > from < " + start + " > num < " + max + " >");
			verifyInputParameters();
			List<String> tokens = deviceInfoDAOR.getTokensBySoftwarVersionAndPlatform(fromVersion, toVersion, Platform.valueOf(platform), start, max);
			result = getTokenResult(S.SUCCESS_CODE, S.EMPTY_STR, tokens);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getDeviceToken cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getTokenResult(S.ERROR_CODE, e.getMessage(), S.EMPTY_STR_LIST);
			return ERROR;
		}
	}

	private void verifyInputParameters() throws IncorrectInputParameterException {
		if (S.isInvalidValue(fromVersion)) {
			throw new IncorrectInputParameterException("fromVersion is null or empty");
		}
		if (S.isInvalidValue(toVersion)) {
			throw new IncorrectInputParameterException("toVersion is null or empty");
		}
		if (S.isInvalidValue(platform)) {
			throw new IncorrectInputParameterException("platform is null or empty");
		}
		if (start < 0) {
			throw new IncorrectInputParameterException("from is less than zero");
		}
		if (max < 0) {
			throw new IncorrectInputParameterException("max is less than zero");
		}
	}

	private JSONObject getTokenResult(int code, String reason, List<String> tokens) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray tokenArray = new JSONArray();
			for (String token : tokens) {
				JSONObject pushObject = new JSONObject();
				pushObject.put("token", token);
				pushObject.put("platform", platform);
				tokenArray.put(pushObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("token_info", tokenArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setDeviceInfoDAOR(DeviceInfoDAO deviceInfoDAOR) {
		this.deviceInfoDAOR = deviceInfoDAOR;
	}

	public void setFromVersion(String fromVersion) {
		this.fromVersion = fromVersion;
	}

	public void setToVersion(String toVersion) {
		this.toVersion = toVersion;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setFrom(int from) {
		this.start = from;
	}

	public void setNum(int num) {
		this.max = num;
	}

	public JSONObject getResult() {
		return result;
	}

}
