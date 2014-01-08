package com.koudai.udc.persistence;

import junit.framework.TestCase;

import com.koudai.udc.domain.ProductPush;
import com.koudai.udc.domain.factory.ProductPushFactory;
import com.koudai.udc.utils.SpringFactory;

public class ProductPushDAOHibernateTest extends TestCase {

	private ProductPushDAO productPushDAOW;

	private ProductPushFactory productPushFactory;

	@Override
	protected void setUp() throws Exception {
		productPushDAOW = (ProductPushDAO) SpringFactory.bean("productPushDAOW");
		productPushFactory = (ProductPushFactory) SpringFactory.bean("productPushFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		ProductPush productPush = productPushFactory.newInstance("m2", "taobao2");
		productPushDAOW.save(productPush);
		assertNotNull(productPush.getId());
	}

	public void testGetProductPushByMachineAndProductId() throws Exception {
		ProductPush productPush = productPushDAOW.getProductPushByMachineAndProductId("m1", "taobao123");
		assertNotNull(productPush);
		assertEquals(new Long(1), productPush.getId());
	}

}
