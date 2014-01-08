package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.utils.DateUtil;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONArray;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public abstract class WeekTopAction extends ActionSupport {

	private static final long serialVersionUID = -998492988882818927L;

	private static final Logger LOGGER = Logger.getLogger(WeekTopAction.class);

	protected String typeId = S.TYPE_GIRL;

	protected int week = DateUtil.lastWeek();

	protected int year = DateUtil.yearOfLastWeek();

	protected JSONObject result;

	@Override
	public String execute() {
		try {
			getWeekTopData();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getDefaultWeekTopResult(S.ERROR_CODE, e.getMessage());
			return ERROR;
		}
	}

	protected abstract void getWeekTopData() throws Exception;

	protected JSONObject getDefaultWeekTopResult(int code, String reason) {
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

	public void setTypeID(String typeID) {
		this.typeId = typeID;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public JSONObject getResult() {
		return result;
	}

}
