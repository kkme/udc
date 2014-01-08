package com.koudai.udc.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.domain.ProductCollect;
import com.koudai.udc.domain.ProductStatus;
import com.koudai.udc.domain.factory.ProductCollectFactory;
import com.koudai.udc.utils.SpringFactory;

public class ProductCollectDAOHibernateTest extends TestCase {

	private ProductCollectDAO productCollectDAOW;

	private ProductCollectDAO productCollectDAOR;

	private ProductCollectFactory productCollectFactory;

	private List<String> userIds = new ArrayList<String>();

	@Override
	protected void setUp() throws Exception {
		userIds.add("@sina:123");
		productCollectDAOW = (ProductCollectDAO) SpringFactory.bean("productCollectDAOW");
		productCollectDAOR = (ProductCollectDAO) SpringFactory.bean("productCollectDAOR");
		productCollectFactory = (ProductCollectFactory) SpringFactory.bean("productCollectFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		ProductCollect productCollect = productCollectFactory.newInstance("123", "m1", "", "@sina:789", "", "", "", "");
		productCollectDAOW.save(productCollect);
		assertNotNull(productCollect.getId());
	}

	public void testGetProductCollectByUserAndProductIdByRD() throws Exception {
		ProductCollect productCollect = productCollectDAOR.getProductCollectByUserAndProductId("@sina:123", "p1");
		assertNotNull(productCollect.getId());
		productCollect = productCollectDAOR.getProductCollectByUserAndProductId("@sina:456", "p1");
		assertNull(productCollect);
	}

	public void testGetProductCollectByUserAndProductIdByWR() throws Exception {
		ProductCollect productCollect = productCollectDAOW.getProductCollectByUserAndProductId("@sina:123", "p1");
		assertNotNull(productCollect.getId());
		productCollect = productCollectDAOW.getProductCollectByUserAndProductId("@sina:456", "p1");
		assertNull(productCollect);
	}

	public void testGetAllProductIdsByUserIdOrderByTimeByRD() throws Exception {
		List<String> productIds = productCollectDAOR.getAllProductIdsByUserIdOrderByTime("@sina:123");
		assertEquals(3, productIds.size());
		assertEquals("p2", productIds.get(0));
		assertEquals("p3", productIds.get(1));
		assertEquals("p1", productIds.get(2));
		productIds = productCollectDAOR.getAllProductIdsByUserIdOrderByTime("@sina:456");
		assertEquals(0, productIds.size());
	}

	public void testGetAllProductIdsByUserIdOrderByTimeByWR() throws Exception {
		List<String> productIds = productCollectDAOW.getAllProductIdsByUserIdOrderByTime("@sina:123");
		assertEquals(3, productIds.size());
		assertEquals("p2", productIds.get(0));
		assertEquals("p3", productIds.get(1));
		assertEquals("p1", productIds.get(2));
		productIds = productCollectDAOW.getAllProductIdsByUserIdOrderByTime("@sina:456");
		assertEquals(0, productIds.size());
	}

	public void testGetProductIdsByUserIdAndStartAndEndPosOrderByTimeByRD() throws Exception {
		List<String> productIds = productCollectDAOR.getProductIdsByUserIdAndStartAndEndPosOrderByTime("@sina:123", 0, 1);
		assertEquals(2, productIds.size());
		assertEquals("p2", productIds.get(0));
		assertEquals("p3", productIds.get(1));
		productIds = productCollectDAOR.getProductIdsByUserIdAndStartAndEndPosOrderByTime("@sina:456", 0, 1);
		assertEquals(0, productIds.size());
	}

	public void testGetProductIdsByUserIdAndStartAndEndPosOrderByTimeByWR() throws Exception {
		List<String> productIds = productCollectDAOW.getProductIdsByUserIdAndStartAndEndPosOrderByTime("@sina:123", 0, 1);
		assertEquals(2, productIds.size());
		assertEquals("p2", productIds.get(0));
		assertEquals("p3", productIds.get(1));
		productIds = productCollectDAOW.getProductIdsByUserIdAndStartAndEndPosOrderByTime("@sina:456", 0, 1);
		assertEquals(0, productIds.size());
	}

	public void testGetAllProductIdsByUserIdByRD() throws Exception {
		List<String> productIds = productCollectDAOR.getAllProductIdsByUserId("@sina:123");
		assertEquals(3, productIds.size());
		assertEquals("p1", productIds.get(0));
		assertEquals("p2", productIds.get(1));
		assertEquals("p3", productIds.get(2));
		productIds = productCollectDAOR.getAllProductIdsByUserIdOrderByTime("@sina:456");
		assertEquals(0, productIds.size());
	}

	public void testGetAllProductIdsByUserIdByWR() throws Exception {
		List<String> productIds = productCollectDAOW.getAllProductIdsByUserId("@sina:123");
		assertEquals(3, productIds.size());
		assertEquals("p1", productIds.get(0));
		assertEquals("p2", productIds.get(1));
		assertEquals("p3", productIds.get(2));
		productIds = productCollectDAOW.getAllProductIdsByUserIdOrderByTime("@sina:456");
		assertEquals(0, productIds.size());
	}

	public void testGetProductsByMachineAndUserIdAndProductIdsAndStatusByRD() throws Exception {
		List<String> productIds = new ArrayList<String>();
		productIds.add("p1");
		productIds.add("p2");
		productIds.add("p3");
		List<ProductCollect> products = productCollectDAOR.getProductsByUserIdAndProductIdsAndStatus("@sina:123", ProductStatus.Normal.getCode(), productIds);
		assertEquals(3, products.size());
	}

	public void testGetProductsByMachineAndUserIdAndProductIdsAndStatusByWR() throws Exception {
		List<String> productIds = new ArrayList<String>();
		productIds.add("p1");
		productIds.add("p2");
		productIds.add("p3");
		List<ProductCollect> products = productCollectDAOW.getProductsByUserIdAndProductIdsAndStatus("@sina:123", ProductStatus.Normal.getCode(), productIds);
		assertEquals(3, products.size());
	}

	public void testGetCollectNumByUserIdByRD() throws Exception {
		int num = productCollectDAOR.getCollectNumByUserId("@sina:123");
		assertEquals(3, num);
		num = productCollectDAOR.getCollectNumByUserId("@sina:456");
		assertEquals(0, num);
	}

	public void testGetCollectNumByUserIdByWR() throws Exception {
		int num = productCollectDAOW.getCollectNumByUserId("@sina:123");
		assertEquals(3, num);
		num = productCollectDAOW.getCollectNumByUserId("@sina:456");
		assertEquals(0, num);
	}

	public void testGetProductIdAndCollectTimeByUserIDAndStartAndEndTimeByRD() throws Exception {
		Calendar newDate = Calendar.getInstance();
		newDate.set(Calendar.YEAR, 2011);
		newDate.set(Calendar.MONTH, 0);
		newDate.set(Calendar.DAY_OF_MONTH, 1);
		newDate.set(Calendar.HOUR_OF_DAY, 0);
		newDate.set(Calendar.MINUTE, 0);
		newDate.set(Calendar.SECOND, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		Date startTime = newDate.getTime();
		Date endTime = new Date();
		List<IdAndDate> idAndDates = productCollectDAOR.getProductIdAndCollectTimeByUserIdAndStartAndEndTime("@sina:123", startTime, endTime);
		assertNotNull(idAndDates);
		assertEquals(3, idAndDates.size());
	}

	public void testGetProductIdAndCollectTimeByUserIDAndStartAndEndTimeByWR() throws Exception {
		Calendar newDate = Calendar.getInstance();
		newDate.set(Calendar.YEAR, 2011);
		newDate.set(Calendar.MONTH, 0);
		newDate.set(Calendar.DAY_OF_MONTH, 1);
		newDate.set(Calendar.HOUR_OF_DAY, 0);
		newDate.set(Calendar.MINUTE, 0);
		newDate.set(Calendar.SECOND, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		Date startTime = newDate.getTime();
		Date endTime = new Date();
		List<IdAndDate> idAndDates = productCollectDAOW.getProductIdAndCollectTimeByUserIdAndStartAndEndTime("@sina:123", startTime, endTime);
		assertNotNull(idAndDates);
		assertEquals(3, idAndDates.size());
	}

}
