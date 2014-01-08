package com.koudai.udc.action;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import com.koudai.udc.utils.DateFormatter;

public abstract class JsonResultWithoutTransactionAction extends JsonResultAction {

	private static final long serialVersionUID = -778013722039570195L;

	private static final Logger LOGGER = Logger.getLogger(JsonResultWithoutTransactionAction.class);

	protected static final DateFormatter DATE_FROMATTER = new DateFormatter();

	protected TransactionTemplate transactionTemplate;

	@Override
	public String execute() {
		try {
			doExecute();
			result = getSuccessfulResult();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			return ERROR;
		}
	}

	protected abstract void doExecute() throws Exception;

	protected void logErrorAndSetRollbackOnly(TransactionStatus ts, Exception e) {
		ts.setRollbackOnly();
		LOGGER.error(e);
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

}
