package com.koudai.udc.action;

import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class LoginSuccessAction extends AbstractAction {

	private String targetUrl;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		context.getExternalContext().getRequestMap().put("targetUrl", targetUrl);
		return success();
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

}
