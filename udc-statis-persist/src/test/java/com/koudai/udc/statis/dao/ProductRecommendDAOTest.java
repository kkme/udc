package com.koudai.udc.statis.dao;

import java.util.Date;

import com.koudai.udc.statis.domain.ProductRecommend;
import com.koudai.udc.statis.factory.ProductRecommendFactory;
import com.koudai.udc.statis.tool.DateFormatter;
import com.koudai.udc.statis.tool.DateUtil;

public class ProductRecommendDAOTest extends BaseDAOTestCase {

	private ProductRecommendDAO productRecommendDAO;

	private ProductRecommendFactory productRecommendFactory;

	public void testSaveSuccessfully() throws Exception {
		ProductRecommend productRecommend = productRecommendFactory.newInstance("123", "456", new Date());
		productRecommendDAO.save(productRecommend);
		assertNotNull(productRecommend.getId());
		assertEquals(new Long(2), productRecommend.getId());
	}

	public void testGetProductRecommendByProductId() throws Exception {
		ProductRecommend productRecommend = productRecommendDAO.getProductRecommendByProductId("taobao123");
		assertNotNull(productRecommend);
		assertEquals(new Long(1), productRecommend.getId());
		assertEquals("abc", productRecommend.getUserId());
		assertEquals("taobao123", productRecommend.getProductId());
		assertEquals("2011-01-01 00:00:00", new DateFormatter().format(productRecommend.getPushTime()));
		productRecommend = productRecommendDAO.getProductRecommendByProductId("taobao456");
		assertNull(productRecommend);
	}

	public void testUpdateSuccessfully() throws Exception {
		Date date = DateUtil.createDate(2012, 3, 3, 0, 0, 0, 0);
		ProductRecommend productRecommend = productRecommendFactory.newInstance("abc", "taobao123", date);
		productRecommendDAO.update(productRecommend);
		ProductRecommend updateProductRecommend = productRecommendDAO.getProductRecommendByProductId("taobao123");
		assertNotNull(updateProductRecommend);
		assertEquals("2012-04-03 00:00:00", new DateFormatter().format(updateProductRecommend.getPushTime()));
	}


	public void setProductRecommendDAO(ProductRecommendDAO productRecommendDAO) {
		this.productRecommendDAO = productRecommendDAO;
	}

	public void setProductRecommendFactory(ProductRecommendFactory productRecommendFactory) {
		this.productRecommendFactory = productRecommendFactory;
	}

}
