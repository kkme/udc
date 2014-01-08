package com.koudai.udc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.exception.DomainException;
import com.koudai.udc.service.CookieControllerService;
import com.koudai.udc.utils.S;

public abstract class CheckUserExistAction extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(CheckUserExistAction.class);

	protected int cookieMaxAge;

	protected CookieControllerService cookieControllerService;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		if (validateNickFailed(context)) {
			return error();
		}
		final String userId = context.getFlowScope().getString(S.NICK);
		final String showNick = context.getFlowScope().getString(S.SHOW_NICK_COOKIE);
		HttpServletResponse response = WebUtils.getHttpServletResponse(context);
		HttpServletRequest request = WebUtils.getHttpServletRequest(context);
		cookieControllerService.setCookieToResponse(request, response, S.COOKIE_DOMAIN, S.USER_ID_COOKIE, userId, cookieMaxAge);
		cookieControllerService.setCookieToResponse(request, response, S.COOKIE_DOMAIN, S.SHOW_NICK_COOKIE, showNick, cookieMaxAge);
		return success();
	}

	private boolean validateNickFailed(RequestContext context) {
		JSONObject resultObject;
		try {
			resultObject = uploadUserInfo(context);
		} catch (DomainException e) {
			return true;
		}
		if (resultObject == null) {
			LOGGER.error("resultObject is null...");
			return true;
		}
		JSONObject statusObject = resultObject.getJSONObject("status");
		String statusCode = statusObject.getString("status_code");
		if (S.USER_ERROR_CODE.equals(statusCode)) {
			LOGGER.error("statuscode error,statusObject>>" + statusObject.toString());
			return true;
		}
		return false;

	}

	protected abstract JSONObject uploadUserInfo(RequestContext context) throws DomainException;

	public void setCookieMaxAge(int cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}

	public void setCookieControllerService(CookieControllerService cookieControllerService) {
		this.cookieControllerService = cookieControllerService;
	}

}
