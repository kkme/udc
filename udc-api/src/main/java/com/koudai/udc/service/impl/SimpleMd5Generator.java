package com.koudai.udc.service.impl;

import java.util.List;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.koudai.udc.service.Md5Generator;
import com.koudai.udc.utils.S;

public class SimpleMd5Generator implements Md5Generator {

	private PasswordEncoder passwordEncoder;

	@Override
	public String getMd5Code(List<String> parameters) {
		StringBuffer source = new StringBuffer();
		for (String parameter : parameters) {
			source.append(parameter).append(S.TAB);
		}
		source.deleteCharAt(source.length() - 1);
		return passwordEncoder.encodePassword(source.toString(), null);
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
