package com.koudai.udc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieControllerService {

	String getStringValueFromCookie(HttpServletRequest request, String key);

	void setCookieToResponse(HttpServletRequest request, HttpServletResponse response, String domain, String key, String value, int maxAge);

}
