package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.UserDataService;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public abstract class UserDataNumberAction extends ActionSupport {

	private static final long serialVersionUID = 110079474813754467L;

	private static final Logger LOGGER = Logger.getLogger(UserDataNumberAction.class);

	protected String userId;

	protected Date endTime;

	protected UserDataService userDataService;

	protected JSONObject result;

	@Override
	public String execute() {
		try {
			verifyUserId();
			getUserDataNumber();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getNumberResult(S.ERROR_CODE, e.getMessage(), 0);
			return ERROR;
		}
	}

	private void verifyUserId() throws IncorrectInputParameterException {
		if (S.isInvalidValue(userId)) {
			throw new IncorrectInputParameterException("User id is null or empty");
		}
	}

	protected abstract void getUserDataNumber() throws Exception;

	protected abstract JSONObject getNumberResult(int code, String reason, int num);

	public JSONObject getResult() {
		return result;
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}

}
