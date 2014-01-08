package com.koudai.udc.controller;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.mvc.AbstractController;

public abstract class MobileGetLoginUrlController extends AbstractController {

	protected JSONObject getLoginUrlResult(int code, String reason, String loginUrl) {
		JSONObject result = new JSONObject();
		JSONObject statusObject = new JSONObject();
		statusObject.put("status_code", code);
		statusObject.put("status_reason", reason);
		result.put("status", statusObject);
		result.put("login_url", loginUrl);
		return result;
	}

}
