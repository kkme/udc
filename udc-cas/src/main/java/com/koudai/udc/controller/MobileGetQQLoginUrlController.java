package com.koudai.udc.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.koudai.udc.service.impl.QQConfiguration;
import com.koudai.udc.utils.QQKey;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.UrlUtil;

public class MobileGetQQLoginUrlController extends MobileGetLoginUrlController {

	private static final Logger LOGGER = Logger.getLogger(MobileLoginQQController.class);

	private QQConfiguration qqConfiguration;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long beginTime = System.currentTimeMillis();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(QQKey.KEY_CLIENT_ID, qqConfiguration.getAppKey());
		parameters.put(QQKey.KEY_REDIRECT_URI, qqConfiguration.getRedirectUri());
		parameters.put(QQKey.KEY_RESPONSE_TYPE, QQKey.VALUE_RESPONSE_TYPE);
		parameters.put(QQKey.KEY_SCOPE, QQKey.VALUE_SCOPE);
		parameters.put(QQKey.KEY_DISPLAY, QQKey.VALUE_DISPLAY);
		StringBuffer loginUrl = new StringBuffer(qqConfiguration.getCodeUrl());
		loginUrl.append("?");
		loginUrl.append(UrlUtil.getSortedContent(parameters));
		response.setCharacterEncoding(HTTP.UTF_8);
		response.setContentType(S.JSON_CONTENT_TYPE);
		PrintWriter printer = response.getWriter();
		printer.print(getLoginUrlResult(S.SUCCESS_CODE, S.EMPTY_STR, loginUrl.toString()));
		printer.flush();
		printer.close();
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("get mobile qq login url cost>>>" + costTime);
		return null;
	}

	public void setQqConfiguration(QQConfiguration qqConfiguration) {
		this.qqConfiguration = qqConfiguration;
	}
}
