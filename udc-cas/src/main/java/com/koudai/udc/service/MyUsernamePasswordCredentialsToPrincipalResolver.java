package com.koudai.udc.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jasig.cas.authentication.principal.AbstractPersonDirectoryCredentialsToPrincipalResolver;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */

/**
 * Implementation of CredentialsToPrincipalResolver for Credentials based on
 * UsernamePasswordCredentials when a SimplePrincipal (username only) is
 * sufficient.
 * <p>
 * Implementation extracts the username from the Credentials provided and
 * constructs a new SimplePrincipal with the unique id set to the username.
 * </p>
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.2 $ $Date: 2007/01/22 20:35:26 $
 * @since 3.0
 * @see org.jasig.cas.authentication.principal.SimplePrincipal
 */
public final class MyUsernamePasswordCredentialsToPrincipalResolver extends AbstractPersonDirectoryCredentialsToPrincipalResolver {

	protected String extractPrincipalId(final Credentials credentials) {
		final UsernamePasswordCredentials usernamePasswordCredentials = (UsernamePasswordCredentials) credentials;

		String userName = usernamePasswordCredentials.getUsername();
		try {
			userName = URLEncoder.encode(usernamePasswordCredentials.getUsername(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return userName;
	}

	/**
	 * Return true if Credentials are UsernamePasswordCredentials, false
	 * otherwise.
	 */
	public boolean supports(final Credentials credentials) {
		return credentials != null && UsernamePasswordCredentials.class.isAssignableFrom(credentials.getClass());
	}
}
