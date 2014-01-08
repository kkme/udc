package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoKey;

public class MobileTaobaoV1Controller extends MobileTaobaoController {

	private static final Logger LOGGER = Logger.getLogger(MobileTaobaoV1Controller.class);

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
			S.logForMap(TaobaoKey.RETURN_TITLE, request.getParameterMap());
			final String token = request.getParameter(TaobaoKey.MOBILE_ACCESS_TOKEN);
			final String expiresIn = request.getParameter(TaobaoKey.MOBILE_EXPIRES_IN);
			final String refreshToken = request.getParameter(TaobaoKey.MOBILE_REFRESH_TOKEN);
			final String refreshTokenExpire = request.getParameter(TaobaoKey.MOBILE_RE_EXPIRES_IN);
			final String nick = request.getParameter(TaobaoKey.MOBILE_TAOBAO_USER_NICK);
			final String taobaoUserId = request.getParameter(TaobaoKey.MOBILE_TAOBAO_USER_ID);
			if (S.isInvalidValue(token)) {
				throw new ThirdPartyErrorReturnException("get taobao access token error return with null or empty");
			}
			if (S.isInvalidValue(expiresIn)) {
				throw new ThirdPartyErrorReturnException("get taobao expiresin error return with null or empty");
			}
			if (S.isInvalidValue(refreshToken)) {
				throw new ThirdPartyErrorReturnException("get taobao refresh token error return with null or empty");
			}
			if (S.isInvalidValue(refreshTokenExpire)) {
				throw new ThirdPartyErrorReturnException("get taobao refresh token expire error return with null or empty");
			}
			if (S.isInvalidValue(nick)) {
				throw new ThirdPartyErrorReturnException("get taobao nick error return with null or empty");
			}
			uploadUserTaobaoInfo(token, expiresIn, refreshToken, refreshTokenExpire, nick, taobaoUserId);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("mobile taobao cost>>>" + costTime);
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
