package com.koudai.udc.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.koudai.udc.service.impl.QQConfiguration;
import com.koudai.udc.service.impl.SinaConfiguration;
import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.QQKey;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.SinaKey;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.koudai.udc.utils.TaobaoKey;
import com.koudai.udc.utils.UrlUtil;

public class MobileGetThirdPartyLoginUrlController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(MobileGetThirdPartyLoginUrlController.class);

	private TaobaoConfiguration taobaoConfiguration;

	private QQConfiguration qqConfiguration;

	private SinaConfiguration sinaConfiguration;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long beginTime = System.currentTimeMillis();
		final String taobaoLoginUrl = initTaobaoLoginUrl();
		final String qqLoginUrl = initQQLoginUrl();
		final String sinaLoginUrl = initSinaWeiboLoginUrl();
		response.setCharacterEncoding(HTTP.UTF_8);
		response.setContentType(S.JSON_CONTENT_TYPE);
		PrintWriter printer = response.getWriter();
		printer.print(getLoginUrlResult(S.SUCCESS_CODE, S.EMPTY_STR, taobaoLoginUrl, qqLoginUrl, sinaLoginUrl));
		printer.flush();
		printer.close();
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("get mobile third party login url cost>>>" + costTime);
		return null;
	}

	private String initTaobaoLoginUrl() {
		return TaobaoApiUtil.getLoginUrl(taobaoConfiguration.getCodeUrl(), taobaoConfiguration.getMobileAppKey(), taobaoConfiguration.getRedirectUri(), TaobaoKey.VALUE_RESPONSE_TYPE_TOKEN, TaobaoKey.VALUE_VIEW_WAP, null);
	}

	private String initQQLoginUrl() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(QQKey.KEY_CLIENT_ID, qqConfiguration.getAppKey());
		parameters.put(QQKey.KEY_REDIRECT_URI, qqConfiguration.getRedirectUri());
		parameters.put(QQKey.KEY_RESPONSE_TYPE, QQKey.VALUE_RESPONSE_TYPE);
		parameters.put(QQKey.KEY_SCOPE, QQKey.VALUE_SCOPE);
		parameters.put(QQKey.KEY_DISPLAY, QQKey.VALUE_DISPLAY);
		StringBuffer loginUrl = new StringBuffer(qqConfiguration.getCodeUrl());
		loginUrl.append("?");
		loginUrl.append(UrlUtil.getSortedContent(parameters));
		return loginUrl.toString();
	}

	private String initSinaWeiboLoginUrl() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(SinaKey.KEY_CLIENT_ID, sinaConfiguration.getAppKey());
		parameters.put(SinaKey.KEY_REDIRECT_URI, sinaConfiguration.getRedirectUri());
		parameters.put(SinaKey.KEY_RESPONSE_TYPE, SinaKey.VALUE_RESPONSE_TYPE);
		parameters.put(SinaKey.KEY_DISPLAY, SinaKey.VALUE_DISPLAY);
		parameters.put(SinaKey.KEY_WITH_OFFICAL_ACCOUNT, SinaKey.VALUE_WITH_OFFICAL_ACCOUNT);
		parameters.put(SinaKey.KEY_FORCE_LOGIN, SinaKey.VALUE_FORCE_LOGIN);
		StringBuffer loginUrl = new StringBuffer(sinaConfiguration.getCodeUrl());
		loginUrl.append("?");
		loginUrl.append(UrlUtil.getSortedContent(parameters));
		return loginUrl.toString();
	}

	private JSONObject getLoginUrlResult(int code, String reason, String taobaoLoginUrl, String qqLoginUrl, String sinaLoginUrl) {
		JSONObject result = new JSONObject();
		JSONObject statusObject = new JSONObject();
		statusObject.put("status_code", code);
		statusObject.put("status_reason", reason);
		result.put("status", statusObject);
		result.put("taobao_login_url", taobaoLoginUrl);
		result.put("qq_login_url", qqLoginUrl);
		result.put("sina_weibo_login_url", sinaLoginUrl);
		return result;
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

	public void setQqConfiguration(QQConfiguration qqConfiguration) {
		this.qqConfiguration = qqConfiguration;
	}

	public void setSinaConfiguration(SinaConfiguration sinaConfiguration) {
		this.sinaConfiguration = sinaConfiguration;
	}

}
