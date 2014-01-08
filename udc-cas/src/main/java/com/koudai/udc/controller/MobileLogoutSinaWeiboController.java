package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koudai.udc.utils.S;
import com.koudai.udc.utils.SinaKey;

public class MobileLogoutSinaWeiboController extends MobileLogoutController {

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		S.logForMap(SinaKey.LOGOUT_TITLE, request.getParameterMap());
	}

}
