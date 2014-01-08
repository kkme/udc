package com.koudai.udc.action;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.ws.security.util.Base64;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.service.CookieControllerService;
import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoKey;

public class VerifyBijiaTaoBaoUserAction extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(VerifyBijiaTaoBaoUserAction.class);

	private TaobaoConfiguration taobaoConfiguration;

	protected CookieControllerService cookieControllerService;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		try {
			HttpServletRequest request = WebUtils.getHttpServletRequest(context);
			if (hasLogin(context, request)) {
				return result("haslogin");
			}
			String sign = request.getParameter("top_sign");
			String parameter = request.getParameter("top_parameters");
			checkSign(parameter, sign);
			String nick = getNickFromParameters(parameter);
			LOGGER.info("nick>>" + nick);
			final String userId = new StringBuffer(TaobaoKey.USER_PREFIX).append(nick).toString();
			context.getFlowScope().put(TaobaoKey.USER_ID, userId);
			context.getFlowScope().put(S.PLATFORM, S.BIJIA_PLATFORM);
			context.getFlowScope().put(S.NICK, userId);
			context.getFlowScope().put(S.SHOW_NICK_COOKIE, nick);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return success();
	}

	private boolean hasLogin(RequestContext context, HttpServletRequest request) {
		final String userId = cookieControllerService.getStringValueFromCookie(request, S.USER_ID_COOKIE);
		if (!S.isInvalidValue(userId) && !S.isInvalidUser(userId)) {
			context.getFlowScope().put(S.NICK, userId);
			LOGGER.info("user has already login and user id is:" + userId);
			return true;
		}
		return false;
	}

	protected void checkSign(String parameter, String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException, ThirdPartyErrorReturnException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		StringBuilder result = new StringBuilder();
		result.append(parameter).append(taobaoConfiguration.getBijiaAppSecret());
		byte[] bytes = md5.digest(result.toString().getBytes("UTF-8"));
		String encode = Base64.encode(bytes);
		if (!encode.equals(sign)) {
			LOGGER.info("encode>>" + encode);
			LOGGER.info("topSign>>" + sign);
			LOGGER.info("parameter>>" + parameter);
			throw new ThirdPartyErrorReturnException("get taobao nick error when check sign failed...");
		}
	}

	protected String getNickFromParameters(String top_parameters) throws ThirdPartyErrorReturnException {
		Map<String, String> map = S.convertBase64StringtoMap(top_parameters);
		if (map != null) {
			Iterator<Entry<String, String>> keyValuePairs = map.entrySet().iterator();
			for (int i = 0; i < map.size(); i++) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) keyValuePairs.next();
				String key = entry.getKey();
				String value = entry.getValue();
				if (S.NICK.equals(key)) {
					return value;
				}
			}
		}
		throw new ThirdPartyErrorReturnException("get taobao nick error from top parameters...");
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

	public void setCookieControllerService(CookieControllerService cookieControllerService) {
		this.cookieControllerService = cookieControllerService;
	}
}
