package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public abstract class MobileLogoutController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(MobileLogoutController.class);

	private String successView;

	private String errorView;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			handle(request, response);
			return new ModelAndView(successView);
		} catch (Exception e) {
			LOGGER.error(e);
			return new ModelAndView(errorView);
		}
	}

	protected abstract void handle(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

}
