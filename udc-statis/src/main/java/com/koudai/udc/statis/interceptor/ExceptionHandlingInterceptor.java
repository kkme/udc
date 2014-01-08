package com.koudai.udc.statis.interceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

public class ExceptionHandlingInterceptor extends AroundInterceptor {

	private static final long serialVersionUID = -5043460961342654245L;

	private static final Logger LOGGER = Logger.getLogger(ExceptionHandlingInterceptor.class);

	public static final String SYSTEM_ERROR_RESULT = "systemErrorResult";

	protected void after(ActionInvocation arg0, String arg1) throws Exception {
	}

	protected void before(ActionInvocation arg0) throws Exception {
	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		try {
			return super.intercept(actionInvocation);
		} catch (Exception e) {
			LOGGER.error("unknow exception", e);
			return SYSTEM_ERROR_RESULT;
		}
	}

}
