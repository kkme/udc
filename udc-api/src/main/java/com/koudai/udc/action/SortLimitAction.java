package com.koudai.udc.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public abstract class SortLimitAction extends ActionSupport {

	private static final long serialVersionUID = -6397183783126284614L;

	private static final Logger LOGGER = Logger.getLogger(SortLimitAction.class);

	protected String userId;

	protected int start = 0;

	protected int end = 0;

	protected JSONObject result;

	@Override
	public String execute() {
		try {
			verifyInputParameters();
			getItemIdsByStartAndEndPos();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getSortLimitResult(S.ERROR_CODE, e.getMessage(), 0, S.EMPTY_STR_LIST);
			return ERROR;
		}
	}

	private void verifyInputParameters() throws IncorrectInputParameterException {
		if (S.isInvalidValue(userId)) {
			throw new IncorrectInputParameterException("User id is null or empty");
		}
		if (start > end) {
			throw new IncorrectInputParameterException("Start position should less than end position");
		}
	}

	protected abstract void getItemIdsByStartAndEndPos() throws Exception;

	protected abstract JSONObject getSortLimitResult(int code, String reason, int num, List<String> itemIds);

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setStartPos(int startPos) {
		this.start = startPos;
	}

	public void setEndPos(int endPos) {
		this.end = endPos;
	}

	public JSONObject getResult() {
		return result;
	}

}
