package com.koudai.udc.exception;

import org.jasig.cas.authentication.handler.AuthenticationException;

public class CasAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 5133229343901763250L;

	public CasAuthenticationException(String code) {
		super(code);
	}

}
