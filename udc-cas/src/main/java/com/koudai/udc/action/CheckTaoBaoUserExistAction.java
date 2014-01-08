package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.exception.DomainException;
import com.koudai.udc.service.ThirdPartyUserInfoService;
import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoKey;

public class CheckTaoBaoUserExistAction extends CheckUserExistAction {

	private ThirdPartyUserInfoService taobaoUserInfoService;

	private TaobaoConfiguration taobaoConfiguration;

	@Override
	protected JSONObject uploadUserInfo(RequestContext context) throws DomainException {
		final String userId = context.getFlowScope().getString(TaobaoKey.USER_ID);
		final String taobaoUserId = context.getFlowScope().getString(TaobaoKey.TAOBAO_USER_ID);
		final String token = context.getFlowScope().getString(TaobaoKey.TOKEN);
		final String expire = context.getFlowScope().getString(TaobaoKey.TOKEN_EXPIRE);
		final String refreshToken = context.getFlowScope().getString(TaobaoKey.REFRESH_TOKEN);
		final String refreshExpire = context.getFlowScope().getString(TaobaoKey.REFRESH_TOKEN_EXPIRE);
		final String platform = context.getFlowScope().getString(S.PLATFORM);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(TaobaoKey.USER_ID, userId);
		parameters.put(TaobaoKey.TAOBAO_USER_ID, taobaoUserId);
		parameters.put(TaobaoKey.TOKEN, token);
		parameters.put(TaobaoKey.TOKEN_EXPIRE, expire);
		parameters.put(TaobaoKey.REFRESH_TOKEN, refreshToken);
		parameters.put(TaobaoKey.REFRESH_TOKEN_EXPIRE, refreshExpire);
		parameters.put(S.PLATFORM, platform);
		return taobaoUserInfoService.uploadUserInfo(parameters, taobaoConfiguration.getUploadUrl());
	}

	public void setTaobaoUserInfoService(ThirdPartyUserInfoService taobaoUserInfoService) {
		this.taobaoUserInfoService = taobaoUserInfoService;
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

}
