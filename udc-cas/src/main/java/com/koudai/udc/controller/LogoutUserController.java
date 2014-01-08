package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

public class LogoutUserController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(LogoutUserController.class);

	private CentralAuthenticationService centralAuthenticationService;
	private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;
	private CookieRetrievingCookieGenerator warnCookieGenerator;
	private String loginUrl;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			final String ticketGrantingTicketId = this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
			LOGGER.info("ticketGrantingTicketId>>" + ticketGrantingTicketId);
			if (ticketGrantingTicketId != null) {
				LOGGER.info("aa>>");
				this.centralAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicketId);
				LOGGER.info("bb>>");
				this.ticketGrantingTicketCookieGenerator.removeCookie(response);
				LOGGER.info("cc>>");
				this.warnCookieGenerator.removeCookie(response);
				LOGGER.info("dd>>");
			}
			LOGGER.info("ee>>");
			request.getSession().invalidate();
			StringBuffer targetUrl = new StringBuffer(loginUrl);
			String service = request.getParameter("service");
			LOGGER.info("service>>" + service);
			if (StringUtils.isNotEmpty(service)) {
				targetUrl.append("?service=");
				targetUrl.append(service);
			}
			LOGGER.info("ff>>");
			return new ModelAndView(new RedirectView(targetUrl.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("ERROR>>" + e.getMessage());
			throw e;
		}
	}

}
