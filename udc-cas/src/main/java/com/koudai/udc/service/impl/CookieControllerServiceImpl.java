package com.koudai.udc.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.GetValueFromCookieFailedException;
import com.koudai.udc.service.CookieControllerService;
import com.koudai.udc.utils.S;

public class CookieControllerServiceImpl implements CookieControllerService {

	private static final Logger LOGGER = Logger.getLogger(CookieControllerServiceImpl.class);

	@Override
	public String getStringValueFromCookie(HttpServletRequest request, String key) {
		try {
			for (Cookie cookie : getCookies(request)) {
				if (cookie != null) {
					String cookieValue = "";
					try {
						cookieValue = URLDecoder.decode(cookie.getValue(), S.UTF_8);
					} catch (UnsupportedEncodingException e) {
						LOGGER.error("UnsupportedEncodingException>>" + e.getMessage());
					}
					LOGGER.debug(String.format("name:<%s>\tvalue:<%s>", cookie.getName(), cookieValue));
					if (key.equals(cookie.getName())) {
						return cookieValue;
					}
				}
			}
		} catch (GetValueFromCookieFailedException e) {
			LOGGER.info("GetValueFromCookieFailedException>>" + e.getMessage());
		}
		return null;
	}

	private Cookie[] getCookies(HttpServletRequest request) throws GetValueFromCookieFailedException {
		verifyRequestIsNull(request);
		final Cookie[] cookies = request.getCookies();
		verifyCookiesIsNull(cookies);
		return cookies;
	}

	private void verifyRequestIsNull(HttpServletRequest request) throws GetValueFromCookieFailedException {
		if (request == null) {
			throw new GetValueFromCookieFailedException("HttpServletRequest from action is null");
		}
	}

	private void verifyCookiesIsNull(final Cookie[] cookies) throws GetValueFromCookieFailedException {
		if (cookies == null) {
			throw new GetValueFromCookieFailedException("Cookies from request is null");
		}
	}

	@Override
	public void setCookieToResponse(HttpServletRequest request, HttpServletResponse response, String domain, String key, String value, int maxAge) {
		if (!cookieExist(request, response, domain, key, value, maxAge)) {
			addNewCookie(request, response, domain, key, value, maxAge);
		}
	}

	private boolean cookieExist(HttpServletRequest request, HttpServletResponse response, String domain, String key, String value, int maxAge) {
		for (Cookie cookie : extractCookiesFromRequest(request)) {
			if (key.equals(cookie.getName())) {
				updateExistCookie(request, response, domain, value, cookie, maxAge);
				return true;
			}
		}
		return false;
	}

	private Cookie[] extractCookiesFromRequest(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			cookies = new Cookie[0];
		}
		return cookies;
	}

	private void updateExistCookie(HttpServletRequest request, HttpServletResponse response, String domain, String value, Cookie cookie, int maxAge) {
		try {
			value = URLEncoder.encode(value, S.UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException>>" + e.getMessage());
		}
		if (domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		cookie.setValue(value);
		response.addCookie(cookie);
	}

	private void addNewCookie(HttpServletRequest request, HttpServletResponse response, String domain, String key, String value, int maxAge) {
		try {
			value = URLEncoder.encode(value, S.UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("UnsupportedEncodingException>>" + e.getMessage());
		}

		Cookie cookie = new Cookie(key, value);
		if (domain != null) {
			cookie.setDomain(domain);
		}
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

}
