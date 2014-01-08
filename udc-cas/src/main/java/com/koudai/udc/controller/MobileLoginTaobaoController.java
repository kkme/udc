package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.koudai.udc.utils.TaobaoKey;

public class MobileLoginTaobaoController extends AbstractController {

	private TaobaoConfiguration taobaoConfiguration;

	private static final Logger LOGGER = Logger.getLogger(MobileLoginTaobaoController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		S.logForMap(TaobaoKey.LOGIN_TITLE, request.getParameterMap());
		long beginTime = System.currentTimeMillis();
		final String loginUrl = TaobaoApiUtil.getLoginUrl(taobaoConfiguration.getCodeUrl(), taobaoConfiguration.getMobileAppKey(), taobaoConfiguration.getRedirectUri(), TaobaoKey.VALUE_RESPONSE_TYPE_TOKEN, TaobaoKey.VALUE_VIEW_WAP, null);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("mobile loginTaobao cost>>>" + costTime);
		return new ModelAndView(new RedirectView(loginUrl));
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

}
