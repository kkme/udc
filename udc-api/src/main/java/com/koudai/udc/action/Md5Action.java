package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.Md5Generator;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;
import com.opensymphony.xwork.ActionSupport;

public abstract class Md5Action extends ActionSupport {

	private static final long serialVersionUID = 385465998254677603L;

	private static final Logger LOGGER = Logger.getLogger(Md5Action.class);

	protected Md5Generator md5Generator;

	protected String userId;

	protected int start = 0;

	protected int end = 0;

	protected JSONObject result;

	@Override
	public String execute() {
		try {
			verifyInputParameters();
			generateMd5code();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getMd5Result(S.ERROR_CODE, e.getMessage(), 0, S.EMPTY_STR);
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

	protected abstract void generateMd5code() throws Exception;

	protected abstract JSONObject getMd5Result(int code, String reason, int num, String md5);

	public void setMd5Generator(Md5Generator md5Generator) {
		this.md5Generator = md5Generator;
	}

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
