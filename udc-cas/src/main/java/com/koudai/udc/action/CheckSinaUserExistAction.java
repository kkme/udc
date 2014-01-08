package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.exception.DomainException;
import com.koudai.udc.service.ThirdPartyUserInfoService;
import com.koudai.udc.service.impl.SinaConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.SinaKey;

public class CheckSinaUserExistAction extends CheckUserExistAction {

	private ThirdPartyUserInfoService sinaWeiboUserInfoService;

	private SinaConfiguration sinaConfiguration;

	@Override
	protected JSONObject uploadUserInfo(RequestContext context) throws DomainException {
		final String userId = context.getFlowScope().getString(SinaKey.USER_ID);
		final String token = context.getFlowScope().getString(SinaKey.TOKEN);
		final String expires = context.getFlowScope().getString(SinaKey.TOKEN_EXPIRE);
		final String nick = context.getFlowScope().getString(SinaKey.USER_NICK);
		final String platform = context.getFlowScope().getString(S.PLATFORM);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(SinaKey.USER_ID, userId);
		parameters.put(SinaKey.TOKEN, token);
		parameters.put(SinaKey.TOKEN_EXPIRE, expires);
		parameters.put(SinaKey.USER_NICK, nick);
		parameters.put(S.PLATFORM, platform);
		S.verifyParametersNotNull(parameters);
		return sinaWeiboUserInfoService.uploadUserInfo(parameters, sinaConfiguration.getUploadUrl());
	}

	public void setSinaWeiboUserInfoService(ThirdPartyUserInfoService sinaWeiboUserInfoService) {
		this.sinaWeiboUserInfoService = sinaWeiboUserInfoService;
	}

	public void setSinaConfiguration(SinaConfiguration sinaConfiguration) {
		this.sinaConfiguration = sinaConfiguration;
	}

}
