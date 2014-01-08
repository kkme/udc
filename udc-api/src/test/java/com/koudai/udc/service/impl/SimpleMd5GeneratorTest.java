package com.koudai.udc.service.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class SimpleMd5GeneratorTest extends TestCase {

	private SimpleMd5Generator md5Generator;

	private PasswordEncoder mockPasswordEncoder;

	private List<String> parameters = new ArrayList<String>();

	@Override
	protected void setUp() throws Exception {
		md5Generator = new SimpleMd5Generator();
		mockPasswordEncoder = EasyMock.createStrictMock(PasswordEncoder.class);
		md5Generator.setPasswordEncoder(mockPasswordEncoder);
		parameters.add("1");
		parameters.add("2");
		parameters.add("3");
	}

	public void testGetMd5Code() throws Exception {
		EasyMock.expect(mockPasswordEncoder.encodePassword((String) EasyMock.anyObject(), EasyMock.anyObject())).andReturn("abc");
		replay();
		assertEquals("abc", md5Generator.getMd5Code(parameters));
		verify();
	}

	private void replay() {
		EasyMock.replay(mockPasswordEncoder);
	}

	private void verify() {
		EasyMock.verify(mockPasswordEncoder);
	}

}
