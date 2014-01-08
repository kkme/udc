package com.koudai.udc.action;

import org.apache.log4j.Logger;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.utils.S;

public class GeneratorCASTicketAction extends AbstractAction {

	private final static Logger LOGGER = Logger.getLogger(GeneratorCASTicketAction.class);

	private CentralAuthenticationService centralAuthenticationService;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		final String userId = context.getFlowScope().getString(S.NICK);
		LOGGER.info("user id is:" + userId);
		try {
			WebUtils.putTicketGrantingTicketInRequestScope(context, centralAuthenticationService.createTicketGrantingTicket(creatCredentials(userId)));
		} catch (TicketException e) {
			LOGGER.error(e);
			return error();
		}
		return success();
	}

	private Credentials creatCredentials(String username) {
		UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials();
		usernamePasswordCredentials.setUsername(username);
		usernamePasswordCredentials.setPassword("");
		return usernamePasswordCredentials;
	}

	public void setCentralAuthenticationService(CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}

}
