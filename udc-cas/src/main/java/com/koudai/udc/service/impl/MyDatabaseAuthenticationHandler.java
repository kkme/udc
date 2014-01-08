package com.koudai.udc.service.impl;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

public class MyDatabaseAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

	@Override
	protected boolean authenticateUsernamePasswordInternal(UsernamePasswordCredentials credentials) throws AuthenticationException {
		return true;
	}

}