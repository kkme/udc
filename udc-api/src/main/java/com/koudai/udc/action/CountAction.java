package com.koudai.udc.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public abstract class CountAction extends ActionSupport {

	private static final long serialVersionUID = -4859250275009401619L;

	private static final Logger LOGGER = Logger.getLogger(CountAction.class);

	private JSONObject result;

	@Override
	public String execute() {
		try {
			verifyInputParameters();
			Map<String, Integer> itemCountMap = getItemCountMap();
			result = getCountResult(S.SUCCESS_CODE, S.EMPTY_STR, itemCountMap);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getCountResult(S.ERROR_CODE, e.getMessage(), S.EMPTY_INT_MAP);
			return ERROR;
		}
	}

	protected abstract void verifyInputParameters() throws Exception;

	protected abstract Map<String, Integer> getItemCountMap() throws Exception;

	protected abstract JSONObject getCountResult(int code, String reason, Map<String, Integer> itemCountMap);

	public JSONObject getResult() {
		return result;
	}

}
