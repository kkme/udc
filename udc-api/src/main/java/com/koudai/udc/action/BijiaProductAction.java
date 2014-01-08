package com.koudai.udc.action;

import java.util.Set;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public abstract class BijiaProductAction extends ActionSupport {

	private static final long serialVersionUID = -2873438726259075354L;

	private static final Logger LOGGER = Logger.getLogger(BijiaProductAction.class);

	protected JSONObject result;

	@Override
	public String execute() throws Exception {
		try {
			doExecute();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getUploadResult(S.UPLOAD_ERROR_CODE, e.getMessage());
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	protected abstract void doExecute() throws Exception;

	protected JSONObject getSuccessfulUploadResult(Set<Boolean> allCollected) {
		if (allCollected.contains(false)) {
			return getUploadResult(S.UPLOAD_SUCCESS_AND_COLLECT_SOME_CODE, S.EMPTY_STR);
		}
		return getUploadResult(S.UPLOAD_SUCCESS_AND_COLLECT_ALL_CODE, S.EMPTY_STR);
	}

	private JSONObject getUploadResult(int code, String reason) {
		try {
			JSONObject result = new JSONObject();
			JSONObject content = new JSONObject();
			content.put("status_code", code);
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
