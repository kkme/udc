package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.TaobaoBindingInfoDAO;

public abstract class TaobaoBindingAction extends JsonResultAction {

	private static final long serialVersionUID = -6427809980737651023L;

	private static final Logger LOGGER = Logger.getLogger(TaobaoBindingAction.class);

	protected TaobaoBindingInfoDAO taobaoBindingInfoDAOW;

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

	public void setTaobaoBindingInfoDAOW(TaobaoBindingInfoDAO taobaoBindingInfoDAOW) {
		this.taobaoBindingInfoDAOW = taobaoBindingInfoDAOW;
	}

}
