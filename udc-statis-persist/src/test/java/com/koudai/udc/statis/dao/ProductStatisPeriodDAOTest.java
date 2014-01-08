package com.koudai.udc.statis.dao;

import com.koudai.udc.statis.domain.ProductStatisPeriod;
import com.koudai.udc.statis.factory.ProductStatisPeriodFactory;

public class ProductStatisPeriodDAOTest extends BaseDAOTestCase {

	private ProductStatisPeriodDAO productStatisPeriodDAO;

	private ProductStatisPeriodFactory productStatisPeriodFactory;

	public void testSaveSuccessfully() throws Exception {
		ProductStatisPeriod productStatisPeriod = productStatisPeriodFactory.newInstance("123", "2012-03-12");
		productStatisPeriodDAO.save(productStatisPeriod);
		assertNotNull(productStatisPeriod.getId());
		assertEquals(new Long(1), productStatisPeriod.getId());
	}

	public void setProductStatisPeriodDAO(ProductStatisPeriodDAO productStatisPeriodDAO) {
		this.productStatisPeriodDAO = productStatisPeriodDAO;
	}

	public void setProductStatisPeriodFactory(ProductStatisPeriodFactory productStatisPeriodFactory) {
		this.productStatisPeriodFactory = productStatisPeriodFactory;
	}

}
