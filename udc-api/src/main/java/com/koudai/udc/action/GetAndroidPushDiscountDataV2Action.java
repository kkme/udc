package com.koudai.udc.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.AndroidPushInfo;
import com.koudai.udc.domain.PushType;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.AndroidPushInfoDAO;
import com.koudai.udc.utils.DateFormatter;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class GetAndroidPushDiscountDataV2Action extends ActionSupport {

	private static final long serialVersionUID = 3896915840968898232L;

	private static final Logger LOGGER = Logger.getLogger(GetAndroidPushDiscountDataV2Action.class);

	private AndroidPushInfoDAO androidPushInfoDAOW;

	private String machineId;

	private Date createTime = DateUtil.setTimeOnDate(DateUtil.yesterday(), 0, 0, 0, 0);

	private JSONObject result;

	@Override
	public String execute() throws Exception {
		try {
			verifyInputParameters();
			long beginTime = System.currentTimeMillis();
			LOGGER.info("Get android push info v2 with machine id < " + machineId + " > and createTime < " + new DateFormatter().format(createTime) + " >");
			List<AndroidPushInfo> pushInfos = androidPushInfoDAOW.getPushInfosByMachineIdAndCreateTime(machineId, createTime);
			for (AndroidPushInfo pushInfo : pushInfos) {
				pushInfo.push();
			}
			result = getSuccessfulResult(pushInfos);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("getAndroidPushDiscountData2 cost>>>" + costTime);
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

	private JSONObject getSuccessfulResult(List<AndroidPushInfo> pushInfos) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", S.SUCCESS_CODE);
			statusObject.put("status_reason", S.EMPTY_STR);
			JSONArray pushArray = new JSONArray();
			for (AndroidPushInfo pushInfo : pushInfos) {
				JSONObject pushObject = new JSONObject();
				pushObject.put("productIDS", pushInfo.getProductIds());
				pushObject.put("firstProductName", pushInfo.getFirstProductName());
				pushObject.put("type", pushInfo.getPushType());
				if (PushType.AUTO.getCode() == pushInfo.getPushType()) {
					pushObject.put("pushID", S.ZERO);
				} else {
					pushObject.put("pushID", pushInfo.getManualId());
				}
				pushArray.put(pushObject);
			}
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("android_push_data", pushArray);
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
			JSONArray pushArray = new JSONArray();
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("android_push_data", pushArray);
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
