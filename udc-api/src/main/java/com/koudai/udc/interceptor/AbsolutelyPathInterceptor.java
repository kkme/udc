package com.koudai.udc.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

public class AbsolutelyPathInterceptor extends AroundInterceptor {

	private static final long serialVersionUID = -7557061605889872644L;

	private static final String SEPERATOR = "://";

	private static final String COLON = ":";

	protected void before(ActionInvocation invocation) throws Exception {
		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context.get(WebWorkStatics.HTTP_REQUEST);
		if (request != null) {
			String absoluteContextPath = request.getScheme() + SEPERATOR + request.getServerName() + COLON + request.getServerPort() + request.getContextPath();
			context.put("absoluteContextPath", absoluteContextPath);
		}
	}

	@Override
	protected void after(ActionInvocation arg0, String arg1) throws Exception {

	}
}
