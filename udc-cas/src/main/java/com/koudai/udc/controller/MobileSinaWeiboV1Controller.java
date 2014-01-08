package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.SinaKey;

public class MobileSinaWeiboV1Controller extends MobileSinaWeiboController {

	private static final Logger LOGGER = Logger.getLogger(MobileSinaWeiboV1Controller.class);

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
			S.logForMap(SinaKey.RETURN_TITLE, request.getParameterMap());
			final String token = request.getParameter(SinaKey.MOBILE_ACCESS_TOKEN);
			final String expires = request.getParameter(SinaKey.MOBILE_EXPIRE_TIME);
			final String uid = request.getParameter(SinaKey.MOBILE_USER_ID);
			final String nick = request.getParameter(SinaKey.MOBILE_USER_NICK);
			if (S.isInvalidValue(token)) {
				throw new ThirdPartyErrorReturnException("get sina token error return with null or empty");
			}
			if (S.isInvalidValue(expires)) {
				throw new ThirdPartyErrorReturnException("get sina expires error return with null or empty");
			}
			if (S.isInvalidValue(uid)) {
				throw new ThirdPartyErrorReturnException("get sina uid error return with null or empty");
			}
			if (S.isInvalidValue(nick)) {
				throw new ThirdPartyErrorReturnException("get sina nick error return with null or empty");
			}
			uploadUserSinaInfo(token, expires, uid, nick);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("mobile sina_weibo cost>>>" + costTime);
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
