package com.koudai.udc.action;

import java.util.Date;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.utils.DateFormatter;

public abstract class JsonResultWithTransactionAction extends JsonResultAction {

	private static final long serialVersionUID = -3431552838920371410L;

	private static final Logger LOGGER = Logger.getLogger(JsonResultWithTransactionAction.class);

	private static final DateFormatter DATE_FROMATTER = new DateFormatter();

	@Override
	public String execute() throws Exception {
		try {
			doExecute();
			result = getSuccessfulResult();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	protected abstract void doExecute() throws Exception;

	protected Date parseDate(String srcTime) {
		if (srcTime == null) {
			return new Date();
		}
		return DATE_FROMATTER.parse(srcTime);
	}

}
