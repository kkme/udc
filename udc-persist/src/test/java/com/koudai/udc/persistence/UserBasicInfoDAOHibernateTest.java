package com.koudai.udc.persistence;

import java.util.Date;

import junit.framework.TestCase;

import com.koudai.udc.domain.UserBasicInfo;
import com.koudai.udc.domain.factory.UserBasicInfoFactory;
import com.koudai.udc.utils.SpringFactory;

public class UserBasicInfoDAOHibernateTest extends TestCase {

	private UserBasicInfoDAO userBasicInfoDAOW;

	private UserBasicInfoFactory userBasicInfoFactory;

	@Override
	protected void setUp() throws Exception {
		userBasicInfoDAOW = (UserBasicInfoDAO) SpringFactory.bean("userBasicInfoDAOW");
		userBasicInfoFactory = (UserBasicInfoFactory) SpringFactory.bean("userBasicInfoFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		UserBasicInfo userBasicInfo = userBasicInfoFactory.newInstance("a@a.com", "123", "131111111", "123", "123", "123", "123", '1', new Date(), "123", new Date(), "122222", "456", "", new Date());
		userBasicInfoDAOW.save(userBasicInfo);
		assertNotNull(userBasicInfo.getId());
	}

	public void testGetUserBasicInfoByThirdPartyIdSuccessfully() throws Exception {
		UserBasicInfo userBasicInfo = userBasicInfoDAOW.getUserBasicInfoByThirdPartyId("@taobao:456");
		assertNotNull(userBasicInfo);
		assertEquals("b@b.com", userBasicInfo.getEmail());
		assertEquals("456", userBasicInfo.getName());
		assertEquals("1", userBasicInfo.getKoudaiId());
	}

}
