package com.koudai.udc.statis.dao;

import com.koudai.udc.statis.domain.ProductStatis;
import com.koudai.udc.statis.factory.ProductStatisFactory;

public class ProductStatisDAOTest extends BaseDAOTestCase {

	private ProductStatisDAO productStatisDAO;

	private ProductStatisFactory productStatisFactory;

	public void testSaveSuccessfully() throws Exception {
		ProductStatis productStatis = productStatisFactory.newInstance("123");
		productStatisDAO.save(productStatis);
		assertNotNull(productStatis.getId());
		assertEquals(new Long(2), productStatis.getId());
	}

	public void testGetProductStatisByProductId() throws Exception {
		ProductStatis productStatis = productStatisDAO.getProductStatisByProductId("taobao123");
		assertEquals(new Long(1), productStatis.getId());
		assertEquals(1, productStatis.getClickedNum());
		assertEquals(1, productStatis.getCollectedNum());
		assertEquals(1, productStatis.getPurchasedNum());
	}

	public void testUpdateSuccessfully() throws Exception {
		ProductStatis productStatis = productStatisFactory.newInstance("taobao123");
		productStatis.setClickedNum(2);
		productStatis.setCollectedNum(3);
		productStatis.setPurchasedNum(4);
		productStatisDAO.update(productStatis);
		ProductStatis updateProductStatis = productStatisDAO.getProductStatisByProductId("taobao123");
		assertNotNull(updateProductStatis);
		assertEquals(3, updateProductStatis.getClickedNum());
		assertEquals(4, updateProductStatis.getCollectedNum());
		assertEquals(5, updateProductStatis.getPurchasedNum());
	}

	public void setProductStatisDAO(ProductStatisDAO productStatisDAO) {
		this.productStatisDAO = productStatisDAO;
	}

	public void setProductStatisFactory(ProductStatisFactory productStatisFactory) {
		this.productStatisFactory = productStatisFactory;
	}

}
