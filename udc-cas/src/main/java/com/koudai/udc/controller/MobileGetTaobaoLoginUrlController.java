package com.koudai.udc.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.koudai.udc.utils.TaobaoKey;

public class MobileGetTaobaoLoginUrlController extends MobileGetLoginUrlController {

	private static final Logger LOGGER = Logger.getLogger(MobileLoginTaobaoController.class);

	private TaobaoConfiguration taobaoConfiguration;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long beginTime = System.currentTimeMillis();
		final String loginUrl = TaobaoApiUtil.getLoginUrl(taobaoConfiguration.getCodeUrl(), taobaoConfiguration.getMobileAppKey(), taobaoConfiguration.getRedirectUri(), TaobaoKey.VALUE_RESPONSE_TYPE_TOKEN, TaobaoKey.VALUE_VIEW_WAP, null);
		response.setCharacterEncoding(HTTP.UTF_8);
		response.setContentType(S.JSON_CONTENT_TYPE);
		PrintWriter printer = response.getWriter();
		printer.print(getLoginUrlResult(S.SUCCESS_CODE, S.EMPTY_STR, loginUrl));
		printer.flush();
		printer.close();
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("get mobile taobao login url cost>>>" + costTime);
		return null;
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

}
