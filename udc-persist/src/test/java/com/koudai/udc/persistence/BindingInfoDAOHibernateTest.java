package com.koudai.udc.persistence;

import junit.framework.TestCase;

import com.koudai.udc.domain.BindingInfo;
import com.koudai.udc.domain.factory.BindingInfoFactory;
import com.koudai.udc.utils.SpringFactory;

public class BindingInfoDAOHibernateTest extends TestCase {

	private BindingInfoDAO bindingInfoDAOW;

	private BindingInfoFactory bindingInfoFactory;

	@Override
	protected void setUp() throws Exception {
		bindingInfoDAOW = (BindingInfoDAO) SpringFactory.bean("bindingInfoDAOW");
		bindingInfoFactory = (BindingInfoFactory) SpringFactory.bean("bindingInfoFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		BindingInfo bindingInfo = bindingInfoFactory.newInstance("123", "456");
		bindingInfoDAOW.save(bindingInfo);
		assertNotNull(bindingInfo.getId());
	}

}
