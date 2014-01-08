package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.AndroidPushInfo;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.AndroidPushInfoDAO;
import com.koudai.udc.utils.DateFormatter;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetAndroidPushDiscountDataAction extends ActionSupport {

	private static final long serialVersionUID = -3558010268494210709L;

	private static final Logger LOGGER = Logger.getLogger(GetAndroidPushDiscountDataAction.class);

	private AndroidPushInfoDAO androidPushInfoDAOW;

	private String machineId;

	private Date createTime = DateUtil.setTimeOnDate(DateUtil.yesterday(), 0, 0, 0, 0);

	private JSONObject result;

	@Override
	public String execute() throws Exception {
		try {
			verifyInputParameters();
			long beginTime = System.currentTimeMillis();
			LOGGER.info("Get android push info with machine id < " + machineId + " > and createTime < " + new DateFormatter().format(createTime) + " >");
			AndroidPushInfo pushInfo = androidPushInfoDAOW.getLatestPushInfoByMachineIdAndCreateTime(machineId, createTime);
			if (pushInfo != null) {
				pushInfo.push();
			}
			result = getSuccessfulResult(pushInfo);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getAndroidPushDiscountData cost>>>" + costTime);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	private void verifyInputParameters() throws IncorrectInputParameterException {
		if (S.isInvalidValue(machineId)) {
			throw new IncorrectInputParameterException("MachineId is null or empty");
		}
	}

	private JSONObject getSuccessfulResult(AndroidPushInfo pushInfo) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", S.SUCCESS_CODE);
			statusObject.put("status_reason", S.EMPTY_STR);
			JSONObject pushObject = new JSONObject();
			if (pushInfo != null) {
				pushObject.put("productIDS", pushInfo.getProductIds());
				pushObject.put("firstProductName", pushInfo.getFirstProductName());
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("android_push_data", pushObject);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	private JSONObject getFailedResult(String reason) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", S.ERROR_CODE);
			statusObject.put("status_reason", reason);
			JSONObject pushObject = new JSONObject();
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("android_push_data", pushObject);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setAndroidPushInfoDAOW(AndroidPushInfoDAO androidPushInfoDAOW) {
		this.androidPushInfoDAOW = androidPushInfoDAOW;
	}

	public void setMachineID(String machineID) {
		this.machineId = machineID;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public JSONObject getResult() {
		return result;
	}

}
