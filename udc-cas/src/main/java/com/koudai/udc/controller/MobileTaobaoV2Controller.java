package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.exception.ThirdPartyErrorButValidReturnException;
import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoKey;
import com.koudai.udc.utils.UrlUtil;

public class MobileTaobaoV2Controller extends MobileTaobaoController {

	private static final Logger LOGGER = Logger.getLogger(MobileTaobaoV2Controller.class);

	private String successUrl;

	private String errorUrl;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		try {
			long beginTime = System.currentTimeMillis();
			S.logForMap(TaobaoKey.RETURN_TITLE, request.getParameterMap());
			final String errorCode = request.getParameter(TaobaoKey.MOBILE_ERROR);
			final String errorDes = request.getParameter(TaobaoKey.MOBILE_ERROR_DES);
			if (!S.isInvalidValue(errorDes)) {
				if (errorDes.contains(TaobaoKey.ERROR_AUTHORIZE_REJECT)) {
					throw new ThirdPartyErrorButValidReturnException("taobao user denies our app");
				}
				if (errorDes.contains(TaobaoKey.ERROR_SUBUSER)) {
					throw new ThirdPartyErrorButValidReturnException("taobao subuser can't access our app");
				}
				throw new ThirdPartyErrorReturnException("get taobao access token error return with code < " + errorCode + " > and description < " + errorDes + " >");
			}
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
			final String koudaiId = new StringBuffer(TaobaoKey.USER_PREFIX).append(nick).toString();
			successUrl = UrlUtil.initResultUrl(successUrl, request.getParameterMap(), nick, koudaiId);
			LOGGER.info("Taobao transfer url is < " + successUrl + " >");
			uploadUserTaobaoInfo(token, expiresIn, refreshToken, refreshTokenExpire, nick, taobaoUserId);
			updateHeadUrlIfNecessary(token, nick);
			long endTime = System.currentTimeMillis();
			long costTime = endTime - beginTime;
			LOGGER.info("mobile taobao_v2 cost>>>" + costTime);
			return new ModelAndView(new RedirectView(successUrl));
		} catch (ThirdPartyErrorButValidReturnException e) {
			LOGGER.info(e);
			return new ModelAndView(new RedirectView(errorUrl));
		} catch (Exception e) {
			LOGGER.error(e);
			return new ModelAndView(new RedirectView(errorUrl));
		}
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

}
