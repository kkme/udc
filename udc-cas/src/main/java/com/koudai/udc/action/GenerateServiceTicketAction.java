package com.koudai.udc.action;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.service.CookieControllerService;
import com.koudai.udc.utils.S;

public class GenerateServiceTicketAction extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(GenerateServiceTicketAction.class);

	private CentralAuthenticationService centralAuthenticationService;
	private TicketRegistry ticketRegistry;
	private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;
	private CookieRetrievingCookieGenerator warnCookieGenerator;

	private String tgtExpiredlogoutUrl;
	private CookieControllerService cookieControllerService;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		HttpServletRequest request = WebUtils.getHttpServletRequest(context);
		final String userIdFromCookie = cookieControllerService.getStringValueFromCookie(request, S.USER_ID_COOKIE);
		final String showNick = cookieControllerService.getStringValueFromCookie(request, S.SHOW_NICK_COOKIE);
		if (S.isInvalidValue(showNick) || S.isInvalidValue(userIdFromCookie)) {
			context.getExternalContext().getRequestMap().put("tgtExpiredlogoutUrl", tgtExpiredlogoutUrl);
			return error();
		}
		final Service service = WebUtils.getService(context);
		final String ticketGrantingTicket = WebUtils.getTicketGrantingTicketId(context);
		final TicketGrantingTicket ticket = (TicketGrantingTicket) ticketRegistry.getTicket(ticketGrantingTicket, TicketGrantingTicket.class);
		if (null != ticket && service == null) {
			final String userId = URLDecoder.decode(ticket.getAuthentication().getPrincipal().getId(), "utf-8");
			LOGGER.info("service is null,redirect to index page...");
			context.getFlowScope().put(S.NICK, userId);
			return result("indexPage");
		}
		try {
			String serviceTicketId = centralAuthenticationService.grantServiceTicket(ticketGrantingTicket, service);
			WebUtils.putServiceTicketInRequestScope(context, serviceTicketId);
		} catch (TicketException e) {
			LOGGER.error("The ticket is expired, the user should login again");
			removeTicketWhenTGTExpired(context, ticketGrantingTicket);
			if (!S.isInvalidValue(userIdFromCookie) && !S.isInvalidUser(userIdFromCookie)) {
				context.getFlowScope().put(S.NICK, userIdFromCookie);
				LOGGER.info("user has already login and user id is:" + userIdFromCookie);
				return result("haslogin");
			} else {
				context.getExternalContext().getRequestMap().put("tgtExpiredlogoutUrl", tgtExpiredlogoutUrl);
				return error();
			}
		}
		return success();
	}

	private void removeTicketWhenTGTExpired(final RequestContext context, final String ticketGrantingTicket) {
		centralAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicket);
		ticketGrantingTicketCookieGenerator.removeCookie(WebUtils.getHttpServletResponse(context));
		warnCookieGenerator.removeCookie(WebUtils.getHttpServletResponse(context));
		context.getFlowScope().remove(ticketGrantingTicket);
	}

	public void setCentralAuthenticationService(CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}

	public void setTicketRegistry(TicketRegistry ticketRegistry) {
		this.ticketRegistry = ticketRegistry;
	}

	public void setTicketGrantingTicketCookieGenerator(CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
		this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
	}

	public void setWarnCookieGenerator(CookieRetrievingCookieGenerator warnCookieGenerator) {
		this.warnCookieGenerator = warnCookieGenerator;
	}

	public void setTgtExpiredlogoutUrl(String tgtExpiredlogoutUrl) {
		this.tgtExpiredlogoutUrl = tgtExpiredlogoutUrl;
	}

	public void setCookieControllerService(CookieControllerService cookieControllerService) {
		this.cookieControllerService = cookieControllerService;
	}

}
