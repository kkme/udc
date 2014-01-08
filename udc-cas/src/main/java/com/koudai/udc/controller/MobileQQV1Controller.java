package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.utils.QQKey;
import com.koudai.udc.utils.S;

public class MobileQQV1Controller extends MobileQQController {

	private static final Logger LOGGER = Logger.getLogger(MobileQQV1Controller.class);

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
			S.logForMap(QQKey.RETURN_TITLE, request.getParameterMap());
			final String token = request.getParameter(QQKey.MOBILE_ACCESS_TOKEN);
			final String expiresIn = request.getParameter(QQKey.MOBILE_EXPIRE_DATE);
			final String openId = request.getParameter(QQKey.MOBILE_OPEN_ID);
			final String nick = request.getParameter(QQKey.MOBILE_USER_NICK);
			if (S.isInvalidValue(token)) {
				throw new ThirdPartyErrorReturnException("get qq access token error return with null or empty");
			}
			if (S.isInvalidValue(expiresIn)) {
				throw new ThirdPartyErrorReturnException("get qq expiresin error return with null or empty");
			}
			if (S.isInvalidValue(openId)) {
				throw new ThirdPartyErrorReturnException("get qq openid error return with null or empty");
			}
			if (S.isInvalidValue(nick)) {
				throw new ThirdPartyErrorReturnException("get qq nick error return with null or empty");
			}
			uploadUserQQInfo(token, expiresIn, nick, openId);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("mobile tencent_qq cost>>>" + costTime);
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
