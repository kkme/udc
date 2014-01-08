package com.koudai.udc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.utils.S;

public class LogoutBeforeController extends AbstractController {
	private static final Logger LOGGER = Logger.getLogger(LogoutBeforeController.class);

	private String redirectUrl;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String service = request.getParameter("service");
		StringBuffer casLogoutWithServiceUrl = new StringBuffer(redirectUrl).append("?service=").append(service);
		Cookie[] cookies = request.getCookies();
		String userNick = "";
		if (!(cookies == null || cookies.length == 0)) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (S.USER_ID_COOKIE.equals(cookieName)) {
					cookie.setDomain(S.COOKIE_DOMAIN);
					cookie.setPath("/");
					cookie.setValue("");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
				if (S.SHOW_NICK_COOKIE.equals(cookieName)) {
					userNick = cookie.getValue();
					cookie.setDomain(S.COOKIE_DOMAIN);
					cookie.setPath("/");
					cookie.setValue("");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
				if (S.LOGIN_PLATFORM_COOKIE.equals(cookieName)) {
					cookie.setDomain(S.COOKIE_DOMAIN);
					cookie.setPath("/");
					cookie.setValue("");
					response.addCookie(cookie);
				}
			}
		}
		LOGGER.info("casLogoutWithServiceUrl>>" + casLogoutWithServiceUrl.toString() + ",userNick is:" + userNick);
		return new ModelAndView(new RedirectView(casLogoutWithServiceUrl.toString()));
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
