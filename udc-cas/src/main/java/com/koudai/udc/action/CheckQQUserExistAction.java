package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.exception.DomainException;
import com.koudai.udc.service.ThirdPartyUserInfoService;
import com.koudai.udc.service.impl.QQConfiguration;
import com.koudai.udc.utils.QQKey;
import com.koudai.udc.utils.S;

public class CheckQQUserExistAction extends CheckUserExistAction {

	private ThirdPartyUserInfoService qqUserInfoService;

	private QQConfiguration qqConfiguration;

	@Override
	protected JSONObject uploadUserInfo(RequestContext context) throws DomainException {
		final String userId = context.getFlowScope().getString(QQKey.USER_ID);
		final String token = context.getFlowScope().getString(QQKey.TOKEN);
		final String expires = context.getFlowScope().getString(QQKey.TOKEN_EXPIRE);
		final String nick = context.getFlowScope().getString(QQKey.USER_NICK);
		final String platform = context.getFlowScope().getString(S.PLATFORM);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(QQKey.USER_ID, userId);
		parameters.put(QQKey.TOKEN, token);
		parameters.put(QQKey.TOKEN_EXPIRE, expires);
		parameters.put(QQKey.USER_NICK, nick);
		parameters.put(S.PLATFORM, platform);
		S.verifyParametersNotNull(parameters);
		return qqUserInfoService.uploadUserInfo(parameters, qqConfiguration.getUploadUrl());
	}

	public void setQqUserInfoService(ThirdPartyUserInfoService qqUserInfoService) {
		this.qqUserInfoService = qqUserInfoService;
	}

	public void setQqConfiguration(QQConfiguration qqConfiguration) {
		this.qqConfiguration = qqConfiguration;
	}

}
