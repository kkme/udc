package com.koudai.udc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.service.CookieControllerService;
import com.koudai.udc.utils.S;

public abstract class GetCodeAction extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(GetCodeAction.class);

	protected CookieControllerService cookieControllerService;

	protected String thinkerDomain;

	protected boolean loginFromBiJia;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		if (hasLogin(context)) {
			return result("haslogin");
		}
		setLoginPlatformCookie(context);
		setAppKeyByPlatform();
		getCodeUrl(context);
		return success();
	}

	protected abstract void setAppKeyByPlatform();

	protected abstract void getCodeUrl(RequestContext context);

	protected boolean hasLogin(RequestContext context) {
		HttpServletRequest request = WebUtils.getHttpServletRequest(context);
		final String userId = cookieControllerService.getStringValueFromCookie(request, S.USER_ID_COOKIE);
		if (!S.isInvalidValue(userId) && !S.isInvalidUser(userId)) {
			context.getFlowScope().put(S.NICK, userId);
			LOGGER.info("user has already login and user id is:" + userId);
			return true;
		}
		return false;
	}

	protected void setLoginPlatformCookie(RequestContext context) {
		Object service = context.getFlowScope().get("service");
		HttpServletResponse response = WebUtils.getHttpServletResponse(context);
		HttpServletRequest request = WebUtils.getHttpServletRequest(context);
		if (service != null && service.toString().contains(thinkerDomain)) {
			loginFromBiJia = true;
			cookieControllerService.setCookieToResponse(request, response, S.COOKIE_DOMAIN, S.LOGIN_PLATFORM_COOKIE, S.BIJIA_PLATFORM, S.DEFAULT_MAX_AGE);
		} else {
			loginFromBiJia = false;
			cookieControllerService.setCookieToResponse(request, response, S.COOKIE_DOMAIN, S.LOGIN_PLATFORM_COOKIE, S.MEITU_PALTFORM, S.DEFAULT_MAX_AGE);
		}
	}

	public void setCookieControllerService(CookieControllerService cookieControllerService) {
		this.cookieControllerService = cookieControllerService;
	}

	public void setThinkerDomain(String thinkerDomain) {
		this.thinkerDomain = thinkerDomain;
	}

}
