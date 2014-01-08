package com.koudai.udc.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public abstract class OwnAction extends ActionSupport {

	private static final long serialVersionUID = 1698306511656140855L;

	private static final Logger LOGGER = Logger.getLogger(OwnAction.class);

	protected JSONObject result;

	@Override
	public String execute() {
		try {
			doExecute();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getOwnResult(S.ERROR_CODE, e.getMessage(), S.EMPTY_STR_LIST, S.EMPTY_STR_LIST);
			return ERROR;
		}
	}

	protected abstract void doExecute() throws Exception;

	protected abstract JSONObject getOwnResult(int code, String reason, List<String> sourceList, List<String> ownerList);

	public JSONObject getResult() {
		return result;
	}

}
