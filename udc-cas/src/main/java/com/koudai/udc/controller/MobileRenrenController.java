package com.koudai.udc.controller;

import org.springframework.web.servlet.mvc.AbstractController;

import com.koudai.udc.service.impl.RenrenConfiguration;

public abstract class MobileRenrenController extends AbstractController {

	protected RenrenConfiguration renrenConfiguration;

	public void setRenrenConfiguration(RenrenConfiguration renrenConfiguration) {
		this.renrenConfiguration = renrenConfiguration;
	}

}
