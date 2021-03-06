package com.koudai.udc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.koudai.udc.utils.S;

public class LogoutController extends AbstractController {

	private String successView;

	private String allLogoutUrl;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(successView);
		String service = request.getParameter("service");
		request.getSession().invalidate();
		mv.addObject("targetUrl", service);
		mv.addObject("logoutUrlList", constructLogoutUrlList(allLogoutUrl));
		return mv;
	}

	private List<String> constructLogoutUrlList(String logoutUrl) {
		List<String> logoutUrlList = new ArrayList<String>();
		String[] urlLists = logoutUrl.split(S.COMMA_STR);
		for (String url : urlLists) {
			logoutUrlList.add(url);
		}
		return logoutUrlList;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setAllLogoutUrl(String allLogoutUrl) {
		this.allLogoutUrl = allLogoutUrl;
	}

}
