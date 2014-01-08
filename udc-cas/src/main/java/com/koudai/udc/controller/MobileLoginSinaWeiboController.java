package com.koudai.udc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.service.impl.SinaConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.SinaKey;
import com.koudai.udc.utils.UrlUtil;

public class MobileLoginSinaWeiboController extends AbstractController {

	private SinaConfiguration sinaConfiguration;

	private static final Logger LOGGER = Logger.getLogger(MobileLoginSinaWeiboController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		S.logForMap(SinaKey.LOGIN_TITLE, request.getParameterMap());
		long beginTime = System.currentTimeMillis();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(SinaKey.KEY_CLIENT_ID, sinaConfiguration.getAppKey());
		parameters.put(SinaKey.KEY_REDIRECT_URI, sinaConfiguration.getRedirectUri());
		parameters.put(SinaKey.KEY_RESPONSE_TYPE, SinaKey.VALUE_RESPONSE_TYPE);
		parameters.put(SinaKey.KEY_DISPLAY, SinaKey.VALUE_DISPLAY);
		parameters.put(SinaKey.KEY_WITH_OFFICAL_ACCOUNT, SinaKey.VALUE_WITH_OFFICAL_ACCOUNT);
		parameters.put(SinaKey.KEY_FORCE_LOGIN, SinaKey.VALUE_FORCE_LOGIN);
		StringBuffer link = new StringBuffer(sinaConfiguration.getCodeUrl());
		link.append("?");
		link.append(UrlUtil.getSortedContent(parameters));
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("mobile loginSinaWeibo cost>>>" + costTime);
		return new ModelAndView(new RedirectView(link.toString()));
	}

	public void setSinaConfiguration(SinaConfiguration sinaConfiguration) {
		this.sinaConfiguration = sinaConfiguration;
	}

}
