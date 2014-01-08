package com.koudai.udc.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

public class PathToRootInterceptor extends AroundInterceptor {

	private static final long serialVersionUID = -1681625832823594703L;

	private static final Logger LOGGER = Logger.getLogger(PathToRootInterceptor.class);

	private static final String PATH_SEPARATOR = "/";

	private static final String BACK_TRACK_COMMAND = "..";

	protected void after(ActionInvocation arg0, String arg1) throws Exception {
	}

	protected void before(ActionInvocation invocation) throws Exception {
		LOGGER.debug("invocation: " + invocation);
		final ActionContext context = invocation.getInvocationContext();
		LOGGER.debug("context: " + context);
		final HttpServletRequest request = (HttpServletRequest) context.get(WebWorkStatics.HTTP_REQUEST);
		LOGGER.debug("request: " + request);
		getPathToRootByRequest(context, request);
	}

	private void getPathToRootByRequest(final ActionContext context, final HttpServletRequest request) {
		if (request != null) {
			String servletPath = getServletPathAndLogIt(request);
			int numberOfSubDirectories = 0;
			if (servletPath != null) {
				numberOfSubDirectories = servletPath.split(PATH_SEPARATOR).length - 2;
			}
			setPathToRootToActionContext(context, numberOfSubDirectories);
		}
	}

	private String getServletPathAndLogIt(final HttpServletRequest request) {
		String servletPath = request.getServletPath();
		LOGGER.debug("servletPath: " + servletPath);
		return servletPath;
	}

	private void setPathToRootToActionContext(final ActionContext context, int numberOfSubDirectories) {
		String pathToRoot = "";
		if (numberOfSubDirectories > 0) {
			pathToRoot = getPathToRoot(numberOfSubDirectories);
		}
		LOGGER.debug("pathToRoot: " + pathToRoot);
		context.getValueStack().setValue("pathToRoot", pathToRoot);
	}

	private String getPathToRoot(int numberOfSubDirectories) {
		StringBuffer pathToRoot = new StringBuffer("..");
		for (int i = 0; i < numberOfSubDirectories - 1; i++) {
			pathToRoot.append(PATH_SEPARATOR + BACK_TRACK_COMMAND);
		}
		LOGGER.info("pathToRoot value: " + pathToRoot);
		return pathToRoot.toString();
	}

}
