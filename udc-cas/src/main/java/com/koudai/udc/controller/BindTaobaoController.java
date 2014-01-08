package com.koudai.udc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.koudai.udc.utils.TaobaoKey;

public class BindTaobaoController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(BindTaobaoController.class);

	private TaobaoConfiguration taobaoConfiguration;

	private String errorView;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		try {
			final String userId = request.getParameter(TaobaoKey.BIND_USER_ID);
			final String service = request.getParameter(S.SERVICE);
			if (S.isInvalidValue(userId) || TaobaoKey.isInvalidBind(userId)) {
				throw new IncorrectInputParameterException("User id is invalid");
			}
			return new ModelAndView(new RedirectView(TaobaoApiUtil.getLoginUrl(taobaoConfiguration.getCodeUrl(), taobaoConfiguration.getMeituAppKey(), taobaoConfiguration.getBindReturnUrl(), TaobaoKey.KEY_CODE, TaobaoKey.VALUE_VIEW_WEB, initState(userId, service))));
		} catch (Exception e) {
			LOGGER.error(e);
			return new ModelAndView(errorView);
		}
	}

	public String initState(String userId, String service) throws UnsupportedEncodingException {
		StringBuffer state = new StringBuffer();
		state.append(userId);
		state.append(S.SPLIT);
		state.append(service);
		return URLEncoder.encode(state.toString(), S.UTF_8);
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

}
