package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public class JsonResultAction extends ActionSupport {

	private static final long serialVersionUID = -5393995059933723592L;

	private static final Logger LOGGER = Logger.getLogger(JsonResultAction.class);

	protected JSONObject result;

	protected JSONObject getSuccessfulResult() {
		try {
			JSONObject result = new JSONObject();
			JSONObject content = new JSONObject();
			content.put("status_code", S.SUCCESS_CODE);
			content.put("status_reason", S.EMPTY_STR);
			result.put("status", content);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	protected JSONObject getFailedResult(String reason) {
		try {
			JSONObject result = new JSONObject();
			JSONObject content = new JSONObject();
			content.put("status_code", S.ERROR_CODE);
			content.put("status_reason", reason);
			result.put("status", content);
			return result;
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
	}

	public JSONObject getResult() {
		return result;
	}

}
