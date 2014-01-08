package com.koudai.udc.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceDaoImpl implements UserDetailsService {

//	private UserFactory userFactory;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		return null;
//		return userFactory.newInstance(username, "", true);
	}

//	public void setUserFactory(UserFactory userFactory) {
//		this.userFactory = userFactory;
//	}

}
