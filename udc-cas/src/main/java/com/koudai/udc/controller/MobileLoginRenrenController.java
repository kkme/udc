package com.koudai.udc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.service.impl.RenrenConfiguration;
import com.koudai.udc.utils.RenrenKey;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UrlUtil;

public class MobileLoginRenrenController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(MobileLoginRenrenController.class);

	private RenrenConfiguration renrenConfiguration;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		S.logForMap(RenrenKey.LOGIN_TITLE, request.getParameterMap());
		long beginTime = System.currentTimeMillis();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(RenrenKey.KEY_CLIENT_ID, renrenConfiguration.getAppApiKey());
		parameters.put(RenrenKey.KEY_REDIRECT_URI, renrenConfiguration.getRedirectUri());
		parameters.put(RenrenKey.KEY_RESPONSE_TYPE, RenrenKey.VALUE_RESPONSE_TYPE);
		parameters.put(RenrenKey.KEY_SCOPE, renrenConfiguration.getApiList());
		parameters.put(RenrenKey.KEY_DISPLAY, RenrenKey.VALUE_DISPLAY);
		StringBuffer link = new StringBuffer(renrenConfiguration.getCodeUrl());
		link.append("?");
		link.append(UrlUtil.getSortedContent(parameters));
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("mobile loginRenren cost>>>" + costTime);
		return new ModelAndView(new RedirectView(link.toString()));
	}

	public void setRenrenConfiguration(RenrenConfiguration renrenConfiguration) {
		this.renrenConfiguration = renrenConfiguration;
	}

}
