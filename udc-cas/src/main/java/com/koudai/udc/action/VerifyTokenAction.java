package com.koudai.udc.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.service.CookieControllerService;
import com.koudai.udc.utils.S;

public abstract class VerifyTokenAction extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(VerifyTokenAction.class);

	protected CookieControllerService cookieControllerService;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		HttpServletRequest request = WebUtils.getHttpServletRequest(context);
		if (hasLogin(context, request)) {
			return result("haslogin");
		}
		final String code = request.getParameter("code");
		if (codeIsNull(request, code)) {
			return success();
		}
		final String platform = getPlatform(request);
		setKeyAndSecretByPlatform(platform);
		return getTokenAndParseNick(context, code, request, platform);
	}

	protected abstract void setKeyAndSecretByPlatform(String platform);

	protected abstract boolean codeIsNull(HttpServletRequest request, String code);

	protected abstract Event getTokenAndParseNick(RequestContext context, String code, HttpServletRequest request, String platform);

	private boolean hasLogin(RequestContext context, HttpServletRequest request) {
		final String userId = cookieControllerService.getStringValueFromCookie(request, S.USER_ID_COOKIE);
		if (!S.isInvalidValue(userId) && !S.isInvalidUser(userId)) {
			context.getFlowScope().put(S.NICK, userId);
			LOGGER.info("user has already login and user id is:" + userId);
			return true;
		}
		return false;
	}

	protected String getPlatform(HttpServletRequest request) {
		String loginPlatform = cookieControllerService.getStringValueFromCookie(request, S.LOGIN_PLATFORM_COOKIE);
		LOGGER.info("loginPlatform>>" + loginPlatform);
		if (!S.isInvalidValue(loginPlatform) && S.BIJIA_PLATFORM.equals(loginPlatform)) {
			return S.BIJIA_PLATFORM;
		} else {
			return S.MEITU_PALTFORM;
		}
	}

	protected void setConstantValueToFlowscope(RequestContext context, String platform, final String userId, final String nick) {
		context.getFlowScope().put(S.PLATFORM, platform);
		context.getFlowScope().put(S.NICK, userId);
		context.getFlowScope().put(S.SHOW_NICK_COOKIE, nick);
	}

	public void setCookieControllerService(CookieControllerService cookieControllerService) {
		this.cookieControllerService = cookieControllerService;
	}

}
