package com.koudai.udc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.service.impl.QQConfiguration;
import com.koudai.udc.utils.QQKey;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UrlUtil;

public class MobileLoginQQController extends AbstractController {

	private QQConfiguration qqConfiguration;

	private static final Logger LOGGER = Logger.getLogger(MobileLoginQQController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		S.logForMap(QQKey.LOGIN_TITLE, request.getParameterMap());
		long beginTime = System.currentTimeMillis();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(QQKey.KEY_CLIENT_ID, qqConfiguration.getAppKey());
		parameters.put(QQKey.KEY_REDIRECT_URI, qqConfiguration.getRedirectUri());
		parameters.put(QQKey.KEY_RESPONSE_TYPE, QQKey.VALUE_RESPONSE_TYPE);
		parameters.put(QQKey.KEY_SCOPE, QQKey.VALUE_SCOPE);
		parameters.put(QQKey.KEY_DISPLAY, QQKey.VALUE_DISPLAY);
		StringBuffer link = new StringBuffer(qqConfiguration.getCodeUrl());
		link.append("?");
		link.append(UrlUtil.getSortedContent(parameters));
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("mobile loginQQ cost>>>" + costTime);
		return new ModelAndView(new RedirectView(link.toString()));
	}

	public void setQqConfiguration(QQConfiguration qqConfiguration) {
		this.qqConfiguration = qqConfiguration;
	}

}
