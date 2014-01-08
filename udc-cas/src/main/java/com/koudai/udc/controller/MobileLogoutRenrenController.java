package com.koudai.udc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koudai.udc.utils.RenrenKey;
import com.koudai.udc.utils.S;

public class MobileLogoutRenrenController extends MobileLogoutController {

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		S.logForMap(RenrenKey.LOGOUT_TITLE, request.getParameterMap());
	}

}
