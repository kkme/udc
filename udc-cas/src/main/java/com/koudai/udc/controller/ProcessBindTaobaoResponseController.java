package com.koudai.udc.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.exception.ThirdPartyErrorReturnException;
import com.koudai.udc.service.impl.TaobaoConfiguration;
import com.koudai.udc.utils.JsonParseUtil;
import com.koudai.udc.utils.S;
import com.koudai.udc.utils.TaobaoApiUtil;
import com.koudai.udc.utils.TaobaoKey;

public class ProcessBindTaobaoResponseController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(ProcessBindTaobaoResponseController.class);

	private TaobaoConfiguration taobaoConfiguration;

	private String errorView;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		try {
			final String state = URLDecoder.decode(request.getParameter(TaobaoKey.KEY_STATE), S.UTF_8);
			if (S.isInvalidValue(state)) {
				throw new IncorrectInputParameterException("state is null or empty");
			}
			final String userId = state.split(S.SPLIT)[0];
			final String service = state.split(S.SPLIT)[1];
			if (S.isInvalidValue(userId) || S.isInvalidValue(service)) {
				throw new IncorrectInputParameterException("userId or service split is null or empty");
			}
			final String errorCode = request.getParameter(TaobaoKey.MOBILE_ERROR);
			final String errorDes = request.getParameter(TaobaoKey.MOBILE_ERROR_DES);
			if (!S.isInvalidValue(errorDes)) {
				LOGGER.error("User < " + userId + " > try to bind taobao account failed with taobao error code < " + errorCode + " > and description < " + errorDes + " >");
				return new ModelAndView(new RedirectView(service));
			}
			final String code = request.getParameter(TaobaoKey.KEY_CODE);
			if (S.isInvalidValue(code)) {
				throw new IncorrectInputParameterException("code is null or empty");
			}
			uploadTaobaoBindingInfo(userId, getBindId(code));
			return new ModelAndView(new RedirectView(service));
		} catch (Exception e) {
			LOGGER.error(e);
			return new ModelAndView(errorView);
		}
	}

	private String getBindId(final String code) throws IOException, ThirdPartyErrorReturnException, IncorrectInputParameterException {
		JSONObject tokenObject = TaobaoApiUtil.getTokenObject(code, taobaoConfiguration.getMeituAppKey(), taobaoConfiguration.getMeituAppSecret(), taobaoConfiguration.getCodeReturnUrl(), taobaoConfiguration.getTokenUrl());
		final String nick = tokenObject.optString("taobao_user_nick", null);
		if (S.isInvalidValue(nick)) {
			throw new IncorrectInputParameterException("Bind id is null or empty");
		}
		final String decodedNick = URLDecoder.decode(nick, S.UTF_8);
		StringBuffer bindId = new StringBuffer(TaobaoKey.USER_PREFIX);
		bindId.append(decodedNick);
		return bindId.toString();
	}

	private void uploadTaobaoBindingInfo(final String userId, final String bindId) {
		LOGGER.info("User < " + userId + " > try to bind taobao user < " + bindId + " >");
		JSONObject contentObject = new JSONObject();
		contentObject.put(TaobaoKey.USER_ID, userId);
		contentObject.put(TaobaoKey.BIND_ID, bindId);
		JSONObject bindObject = new JSONObject();
		bindObject.put("taobaobindinginfo", contentObject.toString());
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("upload_taobao_binding_info_in", bindObject.toString());
		JSONObject resultObject = JsonParseUtil.parseJsonToObject(taobaoConfiguration.getUploadBindingUrl(), requestParameters);
		LOGGER.info("Upload taobao binding info result is: " + resultObject);
	}

	public void setTaobaoConfiguration(TaobaoConfiguration taobaoConfiguration) {
		this.taobaoConfiguration = taobaoConfiguration;
	}

	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

}
