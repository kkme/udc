package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koudai.udc.utils.QQKey;
import com.koudai.udc.utils.S;

public class MobileLogoutQQController extends MobileLogoutController {

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		S.logForMap(QQKey.LOGOUT_TITLE, request.getParameterMap());
	}

}
