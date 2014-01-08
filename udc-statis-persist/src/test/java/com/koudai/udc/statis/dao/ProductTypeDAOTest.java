package com.koudai.udc.statis.dao;

import com.koudai.udc.statis.dao.ProductTypeDAO;
import com.koudai.udc.statis.domain.ProductType;
import com.koudai.udc.statis.factory.ProductTypeFactory;

public class ProductTypeDAOTest extends BaseDAOTestCase {

	private ProductTypeDAO productTypeDAO;

	private ProductTypeFactory productTypeFactory;

	public void testSaveSuccessfully() throws Exception {
		ProductType productType = productTypeFactory.newInstance("123", "456");
		productTypeDAO.save(productType);
		assertNotNull(productType.getId());
	}

	public void setProductTypeDAO(ProductTypeDAO productTypeDAO) {
		this.productTypeDAO = productTypeDAO;
	}

	public void setProductTypeFactory(ProductTypeFactory productTypeFactory) {
		this.productTypeFactory = productTypeFactory;
	}

}
