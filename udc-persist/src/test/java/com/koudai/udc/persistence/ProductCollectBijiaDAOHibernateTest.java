package com.koudai.udc.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.BijiaProduct;
import com.koudai.udc.utils.SpringFactory;

public class ProductCollectBijiaDAOHibernateTest extends TestCase {

	private ProductCollectBijiaDAO productCollectBijiaDAOW;

	private ProductCollectBijiaDAO productCollectBijiaDAOR;

	private static final List<String> FILTERBALE_IDS = new ArrayList<String>();

	private static final String USER_ID = "@sina:123";

	static {
		FILTERBALE_IDS.add("p5");
	}

	@Override
	protected void setUp() throws Exception {
		productCollectBijiaDAOW = (ProductCollectBijiaDAO) SpringFactory.bean("productCollectBijiaDAOW");
		productCollectBijiaDAOR = (ProductCollectBijiaDAO) SpringFactory.bean("productCollectBijiaDAOR");
	}

	public void testGetBijiaProductsByUserIdByRD() throws Exception {
		List<BijiaProduct> products = productCollectBijiaDAOR.getBijiaProductsByUserId(USER_ID, FILTERBALE_IDS);
		assertEquals(4, products.size());
		BijiaProduct product = products.get(0);
		assertEquals("p4", product.getProductId());
		assertEquals(0, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		product = products.get(3);
		assertEquals("p1", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		products = productCollectBijiaDAOR.getBijiaProductsByUserId("@sina:456", FILTERBALE_IDS);
		assertEquals(0, products.size());
		products = productCollectBijiaDAOR.getBijiaProductsByUserId(USER_ID, new ArrayList<String>());
		assertEquals(5, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		product = products.get(4);
		assertEquals("p1", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
	}

	public void testGetBijiaProductsByUserIdByWR() throws Exception {
		List<BijiaProduct> products = productCollectBijiaDAOW.getBijiaProductsByUserId(USER_ID, FILTERBALE_IDS);
		assertEquals(4, products.size());
		BijiaProduct product = products.get(0);
		assertEquals("p4", product.getProductId());
		assertEquals(0, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		product = products.get(3);
		assertEquals("p1", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		products = productCollectBijiaDAOW.getBijiaProductsByUserId("@sina:456", FILTERBALE_IDS);
		assertEquals(0, products.size());
		products = productCollectBijiaDAOW.getBijiaProductsByUserId(USER_ID, new ArrayList<String>());
		assertEquals(5, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		product = products.get(4);
		assertEquals("p1", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
	}

	public void testGetBijiaProductsByUserIdAndStartAndEndPosByRD() throws Exception {
		List<BijiaProduct> products = productCollectBijiaDAOR.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, FILTERBALE_IDS, 0, 0);
		assertEquals(1, products.size());
		BijiaProduct product = products.get(0);
		assertEquals("p4", product.getProductId());
		assertEquals(0, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		products = productCollectBijiaDAOR.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, FILTERBALE_IDS, 0, 10);
		assertEquals(4, products.size());
		product = products.get(0);
		assertEquals("p4", product.getProductId());
		assertEquals(0, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		product = products.get(3);
		assertEquals("p1", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		products = productCollectBijiaDAOR.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, new ArrayList<String>(), 0, 0);
		assertEquals(1, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		products = productCollectBijiaDAOR.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, new ArrayList<String>(), 0, 10);
		assertEquals(5, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		product = products.get(4);
		assertEquals("p1", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
	}

	public void testGetBijiaProductsByUserIdAndStartAndEndPosByWR() throws Exception {
		List<BijiaProduct> products = productCollectBijiaDAOW.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, FILTERBALE_IDS, 0, 0);
		assertEquals(1, products.size());
		BijiaProduct product = products.get(0);
		assertEquals("p4", product.getProductId());
		assertEquals(0, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		products = productCollectBijiaDAOW.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, FILTERBALE_IDS, 0, 10);
		assertEquals(4, products.size());
		product = products.get(0);
		assertEquals("p4", product.getProductId());
		assertEquals(0, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		product = products.get(3);
		assertEquals("p1", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		products = productCollectBijiaDAOW.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, new ArrayList<String>(), 0, 0);
		assertEquals(1, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		products = productCollectBijiaDAOW.getBijiaProductsByUserIdAndStartAndEndPos(USER_ID, new ArrayList<String>(), 0, 10);
		assertEquals(5, products.size());
		product = products.get(0);
		assertEquals("p5", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
		product = products.get(4);
		assertEquals("p1", product.getProductId());
		assertEquals(1, product.getPriceReduction());
		assertEquals(0, product.getCanRemind());
		assertEquals(BigDecimal.ZERO, product.getBalance());
	}

}
