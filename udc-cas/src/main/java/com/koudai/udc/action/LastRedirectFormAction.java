package com.koudai.udc.action;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.principal.WebApplicationService;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class LastRedirectFormAction extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(LastRedirectFormAction.class);

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		final WebApplicationService service = WebUtils.getService(context);
		final String ticketId = WebUtils.getServiceTicketFromRequestScope(context);
		LOGGER.info("ticket ... " + ticketId);
		LOGGER.info("next url must be " + service.getResponse(ticketId).getUrl());
		context.getExternalContext().getRequestMap().put("ticketId", ticketId);
		return success();
	}

}
