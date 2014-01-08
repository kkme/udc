package com.koudai.udc.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.service.impl.SinaConfiguration;
import com.koudai.udc.utils.UrlUtil;

public class GetSinaCodeAction extends GetCodeAction {

	private static final Logger LOGGER = Logger.getLogger(GetSinaCodeAction.class);

	private SinaConfiguration sinaConfiguration;

	private String appKey;

	protected void setAppKeyByPlatform() {
		if (loginFromBiJia) {
			appKey = sinaConfiguration.getBijiaAppKey();
		} else {
			appKey = sinaConfiguration.getAppKey();
		}
	}

	public void getCodeUrl(RequestContext context) {
		String codeUrl = constructCodeUrl(context, appKey, sinaConfiguration.getCodeUrl(), sinaConfiguration.getCodeReturnUrl());
		LOGGER.info("Sina code url is : " + codeUrl);
		context.getExternalContext().getRequestMap().put("getSinaCodeUrl", codeUrl);
	}

	protected String constructCodeUrl(RequestContext context, String appKey, String codeUrl, String codeReturnUrl) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("response_type", "code");
		parameters.put("state", "1");
		parameters.put("redirect_uri", UrlUtil.initReturnUrl(context, codeReturnUrl));
		parameters.put("client_id", appKey);
		parameters.put("with_offical_account", "1");
		StringBuffer getCodeUrl = new StringBuffer(codeUrl);
		getCodeUrl.append("?");
		getCodeUrl.append(UrlUtil.getEncodedSortedContent(parameters));
		return getCodeUrl.toString();
	}

	public void setSinaConfiguration(SinaConfiguration sinaConfiguration) {
		this.sinaConfiguration = sinaConfiguration;
	}

}
