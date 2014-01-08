package com.koudai.udc.persistence;

import java.math.BigDecimal;
import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.BijiaProduct;
import com.koudai.udc.utils.SpringFactory;

public class PriceRemindDAOHibernateTest extends TestCase {

	private static final String USER_ID = "@sina:123";

	private PriceRemindDAO priceRemindDAOW;

	private PriceRemindDAO priceRemindDAOR;

	@Override
	protected void setUp() throws Exception {
		priceRemindDAOW = (PriceRemindDAO) SpringFactory.bean("priceRemindDAOW");
		priceRemindDAOR = (PriceRemindDAO) SpringFactory.bean("priceRemindDAOR");
	}

	public void testGetBijiaProductsByUserIdByRD() throws Exception {
		List<BijiaProduct> products = priceRemindDAOR.getBijiaProductsByUserId(USER_ID);
		assertEquals(4, products.size());
		BijiaProduct product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		products = priceRemindDAOR.getBijiaProductsByUserId("@sina:456");
		assertEquals(0, products.size());
	}

	public void testGetBijiaProductsByUserIdByWR() throws Exception {
		List<BijiaProduct> products = priceRemindDAOW.getBijiaProductsByUserId(USER_ID);
		assertEquals(4, products.size());
		BijiaProduct product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		products = priceRemindDAOW.getBijiaProductsByUserId("@sina:456");
		assertEquals(0, products.size());
	}

	public void testGetBijiaProductsByUserIdAndStartAndEndPosByRD() throws Exception {
		List<BijiaProduct> products = priceRemindDAOR.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, 0, 4);
		assertEquals(4, products.size());
		BijiaProduct product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		products = priceRemindDAOR.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, 0, 10);
		assertEquals(4, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		products = priceRemindDAOR.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, 0, 0);
		assertEquals(1, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		products = priceRemindDAOR.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, 0, 2);
		assertEquals(3, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		product = products.get(2);
		assertEquals("p3", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
	}

	public void testGetBijiaProductsByUserIdAndStartAndEndPosByWR() throws Exception {
		List<BijiaProduct> products = priceRemindDAOW.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, 0, 4);
		assertEquals(4, products.size());
		BijiaProduct product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		products = priceRemindDAOW.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, 0, 10);
		assertEquals(4, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		products = priceRemindDAOW.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, 0, 0);
		assertEquals(1, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		products = priceRemindDAOW.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, 0, 2);
		assertEquals(3, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
		product = products.get(2);
		assertEquals("p3", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(1, product.getCanRemind());
		assertEquals(new BigDecimal("7.07"), product.getBalance());
	}

}
