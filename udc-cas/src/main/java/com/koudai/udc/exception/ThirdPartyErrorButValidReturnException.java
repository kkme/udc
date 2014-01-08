package com.koudai.udc.exception;

public class ThirdPartyErrorButValidReturnException extends DomainException {

	private static final long serialVersionUID = 602560256844075705L;

	public ThirdPartyErrorButValidReturnException(String messsage) {
		super(messsage);
	}

}
