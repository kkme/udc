package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.koudai.udc.utils.RenrenKey;
import com.koudai.udc.utils.S;

public class MobileRenrenV1Controller extends MobileRenrenController {

	private static final Logger LOGGER = Logger.getLogger(MobileRenrenV1Controller.class);

	private String successView;

	private String errorView;

	private String transferView;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		try {
			final String platform = request.getParameter(S.KOUDAI_PLATFORM);
			final String version = request.getParameter(S.KOUDAI_VERSION);
			if (S.isInvalidValue(platform) && S.isInvalidValue(version)) {
				return new ModelAndView(transferView);
			}
			long beginTime = System.currentTimeMillis();
			S.logForMap(RenrenKey.RETURN_TITLE, request.getParameterMap());
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("mobile renren cost>>>" + costTime);
			return new ModelAndView(successView);
		} catch (Exception e) {
			LOGGER.error(e);
			return new ModelAndView(errorView);
		}
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

	public void setTransferView(String transferView) {
		this.transferView = transferView;
	}
}
