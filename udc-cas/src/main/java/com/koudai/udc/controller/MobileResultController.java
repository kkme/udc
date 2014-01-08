package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.koudai.udc.utils.S;

public class MobileResultController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(MobileResultController.class);

	private String universalViewName;

	private String windows8ViewName;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String userAgent = request.getHeader(S.USER_AGENT);
		LOGGER.info("User agent info is < " + userAgent + " >");
		if (!S.isInvalidValue(userAgent) && userAgent.contains(S.WINDOWS_8)) {
			return new ModelAndView(windows8ViewName);
		}
		return new ModelAndView(universalViewName);
	}

	public void setUniversalViewName(String universalViewName) {
		this.universalViewName = universalViewName;
	}

	public void setWindows8ViewName(String windows8ViewName) {
		this.windows8ViewName = windows8ViewName;
	}

}
