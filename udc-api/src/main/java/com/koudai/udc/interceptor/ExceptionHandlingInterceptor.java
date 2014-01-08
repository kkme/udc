package com.koudai.udc.interceptor;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.ActionErrorDispatchException;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

public class ExceptionHandlingInterceptor extends AroundInterceptor {

	private static final long serialVersionUID = 8514631329243161845L;

	private static final Logger LOGGER = Logger.getLogger(ExceptionHandlingInterceptor.class);

	public static final String SYSTEM_ERROR_RESULT = "systemErrorResult";

	protected void after(ActionInvocation arg0, String arg1) throws Exception {
	}

	protected void before(ActionInvocation arg0) throws Exception {
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		try {
			return super.intercept(actionInvocation);
		} catch (ActionErrorDispatchException e) {
			return getResultCodeAndLog(e);
		} catch (Exception e) {
			LOGGER.error("unknow exception", e);
			return SYSTEM_ERROR_RESULT;
		}
	}

	private String getResultCodeAndLog(ActionErrorDispatchException e) {
		String resultCode = e.getResultCode();
		if (ActionConstants.RESULT_INVALID_TOKEN.equals(resultCode)) {
			LOGGER.error("Transaction rolled back in response to an exception. Action result code: '" + ActionConstants.RESULT_INVALID_TOKEN + "'");
			LOGGER.debug(e);
		} else {
			LOGGER.error("Transaction rolled back in response to an exception. Action result code: '" + resultCode + "'", e);
		}
		return resultCode;
	}

}
