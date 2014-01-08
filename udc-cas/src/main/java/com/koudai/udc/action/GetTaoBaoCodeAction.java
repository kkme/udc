package com.koudai.udc.action;

import org.apache.log4j.Logger;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.koudai.udc.utils.TaobaoKey;
import com.koudai.udc.utils.UrlUtil;

public class GetTaoBaoCodeAction extends GetCodeAction {

	private static final Logger LOGGER = Logger.getLogger(GetTaoBaoCodeAction.class);

	private TaobaoConfiguration taobaoConfiguration;

	protected void setAppKeyByPlatform() {
	}

	public void getCodeUrl(RequestContext context) {
		String codeUrl = "";
		if (loginFromBiJia) {
			final String returnUrl = UrlUtil.initReturnUrl(context, taobaoConfiguration.getBijiaReturnUrl());
			codeUrl = TaobaoApiUtil.getBijiaLoginUrl(taobaoConfiguration.getBijiaAppKey(), taobaoConfiguration.getCodeUrl(), returnUrl);
		} else {
			final String returnUrl = UrlUtil.initReturnUrl(context, taobaoConfiguration.getCodeReturnUrl());
			codeUrl = TaobaoApiUtil.getLoginUrl(taobaoConfiguration.getCodeUrl(), taobaoConfiguration.getMeituAppKey(), returnUrl, TaobaoKey.KEY_CODE, TaobaoKey.VALUE_VIEW_WEB, null);
		}
		LOGGER.info("TaoBao code url is : " + codeUrl);
		context.getExternalContext().getRequestMap().put("getTaoBaoCodeUrl", codeUrl);
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

}
