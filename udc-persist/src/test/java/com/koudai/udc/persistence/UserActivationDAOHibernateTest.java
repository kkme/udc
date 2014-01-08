package com.koudai.udc.persistence;

import junit.framework.TestCase;

import com.koudai.udc.domain.UserActivation;
import com.koudai.udc.domain.factory.UserActivationFactory;
import com.koudai.udc.utils.SpringFactory;

public class UserActivationDAOHibernateTest extends TestCase {

	private UserActivationDAO userActivationDAOW;

	private UserActivationFactory userActivationFactory;

	@Override
	protected void setUp() throws Exception {
		userActivationDAOW = (UserActivationDAO) SpringFactory.bean("userActivationDAOW");
		userActivationFactory = (UserActivationFactory) SpringFactory.bean("userActivationFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		String userId = "@taobao:taobaotest";
		UserActivation userActivation = userActivationFactory.newInstance(userId);
		userActivationDAOW.save(userActivation);
		assertNotNull(userActivation.getId());
		assertEquals(userId, userActivation.getUserId());
	}

	public void testGetUserActivationSuccessfully() throws Exception {
		String userId = "@sina:test";
		String wrongUserId = "wrong";
		UserActivation userActivation = userActivationDAOW.getUserActivationByUserId(userId);
		assertEquals(true, userActivation.isIosPay());
		assertEquals(false, userActivation.isYellowDiamond());
		UserActivation userActivation2 = userActivationDAOW.getUserActivationByUserId(wrongUserId);
		assertNull(userActivation2);
	}

}
