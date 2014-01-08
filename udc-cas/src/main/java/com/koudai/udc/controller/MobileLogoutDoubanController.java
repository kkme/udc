package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koudai.udc.utils.DoubanKey;
import com.koudai.udc.utils.S;

public class MobileLogoutDoubanController extends MobileLogoutController {

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		S.logForMap(DoubanKey.LOGOUT_TITLE, request.getParameterMap());
	}

}
