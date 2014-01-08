package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.service.impl.QQConfiguration;
import com.koudai.udc.utils.UrlUtil;

public class GetQQCodeAction extends GetCodeAction {

	private static final Logger LOGGER = Logger.getLogger(GetQQCodeAction.class);

	private QQConfiguration qqConfiguration;

	private String appKey;

	protected void setAppKeyByPlatform() {
		if (loginFromBiJia) {
			appKey = qqConfiguration.getBijiaAppKey();
		} else {
			appKey = qqConfiguration.getAppKey();
		}
	}

	public void getCodeUrl(RequestContext context) {
		String codeUrl = constructCodeUrl(context, appKey, qqConfiguration.getCodeUrl(), qqConfiguration.getCodeReturnUrl());
		LOGGER.info("qq code url is : " + codeUrl);
		context.getExternalContext().getRequestMap().put("getQQCodeUrl", codeUrl);
	}

	protected String constructCodeUrl(RequestContext context, String appKey, String codeUrl, String codeReturnUrl) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("response_type", "code");
		parameters.put("state", "1");
		parameters.put("redirect_uri", UrlUtil.initReturnUrl(context, codeReturnUrl));
		parameters.put("client_id", appKey);
		parameters.put("scope", qqConfiguration.getApiList());
		StringBuffer getCodeUrl = new StringBuffer(codeUrl);
		getCodeUrl.append("?");
		getCodeUrl.append(UrlUtil.getEncodedSortedContent(parameters));
		return getCodeUrl.toString();
	}

	public void setQqConfiguration(QQConfiguration qqConfiguration) {
		this.qqConfiguration = qqConfiguration;
	}

}
