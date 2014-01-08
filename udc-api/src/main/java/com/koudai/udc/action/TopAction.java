package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.utils.DateFormatter;
import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public abstract class TopAction extends ActionSupport {

	private static final long serialVersionUID = -5789660443404819191L;

	private static final Logger LOGGER = Logger.getLogger(TopAction.class);

	protected int top = 10;

	protected String day;

	protected Date startTime;

	protected Date endTime;

	protected String typeId = S.TYPE_GIRL;

	protected JSONObject result;

	@Override
	public String execute() {
		try {
			verifyInputParameters();
			getTopData();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getDefaultTopResult(S.ERROR_CODE, e.getMessage());
			return ERROR;
		}
	}

	private void verifyInputParameters() throws IncorrectInputParameterException {
		if (S.isInvalidValue(day)) {
			throw new IncorrectInputParameterException("Day queried is null or empty");
		}
		if (!DateUtil.isDate(day)) {
			throw new IncorrectInputParameterException("Day format is illegal");
		}
		startTime = DateUtil.setTimeOnDate(new DateFormatter(DateFormatter.COMMON_DATE_FORMAT).parse(day), 0, 0, 0, 0);
		endTime = DateUtil.tomorrow(startTime);
	}

	protected abstract void getTopData() throws Exception;

	protected JSONObject getDefaultTopResult(int code, String reason) {
		try {
			JSONObject statusObject = new JSONObject();
			statusObject.put("status_code", code);
			statusObject.put("status_reason", reason);
			JSONArray topArray = new JSONArray();
			JSONObject result = new JSONObject();
			result.put("status", statusObject);
			result.put("result_info", topArray);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public void setTop(int top) {
		this.top = top;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setTypeID(String typeID) {
		this.typeId = typeID;
	}

	public JSONObject getResult() {
		return result;
	}

}
