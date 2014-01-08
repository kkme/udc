package com.koudai.udc.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.koudai.udc.service.impl.SinaConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.SinaKey;
import com.koudai.udc.utils.UrlUtil;

public class MobileGetSinaWeiboLoginUrlController extends MobileGetLoginUrlController {

	private static final Logger LOGGER = Logger.getLogger(MobileLoginSinaWeiboController.class);

	private SinaConfiguration sinaConfiguration;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long beginTime = System.currentTimeMillis();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(SinaKey.KEY_CLIENT_ID, sinaConfiguration.getAppKey());
		parameters.put(SinaKey.KEY_REDIRECT_URI, sinaConfiguration.getRedirectUri());
		parameters.put(SinaKey.KEY_RESPONSE_TYPE, SinaKey.VALUE_RESPONSE_TYPE);
		parameters.put(SinaKey.KEY_DISPLAY, SinaKey.VALUE_DISPLAY);
		parameters.put(SinaKey.KEY_WITH_OFFICAL_ACCOUNT, SinaKey.VALUE_WITH_OFFICAL_ACCOUNT);
		parameters.put(SinaKey.KEY_FORCE_LOGIN, SinaKey.VALUE_FORCE_LOGIN);
		StringBuffer loginUrl = new StringBuffer(sinaConfiguration.getCodeUrl());
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
		LOGGER.info("get mobile sina weibo login url cost>>>" + costTime);
		return null;
	}

	public void setSinaConfiguration(SinaConfiguration sinaConfiguration) {
		this.sinaConfiguration = sinaConfiguration;
	}

}
