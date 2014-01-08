package com.koudai.udc.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.ProductCollectTop;
import com.koudai.udc.domain.ProductTopStatus;
import com.koudai.udc.domain.factory.ProductCollectTopFactory;
import com.koudai.udc.utils.SpringFactory;

public class ProductCollectTopDAOHibernateTest extends TestCase {

	private ProductCollectTopDAO productCollectTopDAOW;

	private ProductCollectTopDAO productCollectTopDAOR;

	private ProductCollectTopFactory productCollectTopFactory;

	private Date startTime;

	private Date endTime;

	@Override
	protected void setUp() throws Exception {
		productCollectTopDAOW = (ProductCollectTopDAO) SpringFactory.bean("productCollectTopDAOW");
		productCollectTopDAOR = (ProductCollectTopDAO) SpringFactory.bean("productCollectTopDAOR");
		productCollectTopFactory = (ProductCollectTopFactory) SpringFactory.bean("productCollectTopFactory");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		startTime = cal.getTime();
		cal.add(Calendar.DATE, 1);
		endTime = cal.getTime();
	}

	public void testSaveSuccessfully() throws Exception {
		ProductCollectTop productCollectTop = productCollectTopFactory.newInstance("taobao12345", 123, "Combine_Default", new Date(), ProductTopStatus.daily_top_ten.getCode());
		productCollectTopDAOW.save(productCollectTop);
		assertNotNull(productCollectTop.getId());
	}

	public void testGetTopsByTypeIdAndStartAndEndTimeByRD() throws Exception {
		List<ProductCollectTop> productCollectTops = productCollectTopDAOR.getTopsByTypeIdAndStartAndEndTime("Combine_Default", startTime, endTime, ProductTopStatus.daily_top_ten.getCode());
		assertEquals(10, productCollectTops.size());
		assertEquals(1, productCollectTops.get(0).getCollectedNum());
		assertEquals("taobao1", productCollectTops.get(0).getProductId());
		assertEquals(10, productCollectTops.get(9).getCollectedNum());
		assertEquals("taobao10", productCollectTops.get(9).getProductId());
	}

	public void testGetTopsByStartAndEndTimeByRD() throws Exception {
		List<ProductCollectTop> productCollectTops = productCollectTopDAOR.getTop30ByStartAndEndTime(startTime, endTime);
		assertEquals(10, productCollectTops.size());
		assertEquals(11, productCollectTops.get(0).getCollectedNum());
		assertEquals("taobao11", productCollectTops.get(0).getProductId());
		assertEquals(15, productCollectTops.get(4).getCollectedNum());
		assertEquals("taobao15", productCollectTops.get(4).getProductId());
	}

	public void testGetTopsByTypeIdAndStartAndEndTimeByWR() throws Exception {
		List<ProductCollectTop> productCollectTops = productCollectTopDAOR.getTopsByTypeIdAndStartAndEndTime("Combine_Default", startTime, endTime, ProductTopStatus.daily_top_ten.getCode());
		assertEquals(10, productCollectTops.size());
		assertEquals(1, productCollectTops.get(0).getCollectedNum());
		assertEquals("taobao1", productCollectTops.get(0).getProductId());
		assertEquals(10, productCollectTops.get(9).getCollectedNum());
		assertEquals("taobao10", productCollectTops.get(9).getProductId());
	}

	public void testGetTop30ByStartAndEndTimeByWR() throws Exception {
		List<ProductCollectTop> productCollectTops = productCollectTopDAOW.getTop30ByStartAndEndTime(startTime, endTime);
		assertEquals(10, productCollectTops.size());
		assertEquals(11, productCollectTops.get(0).getCollectedNum());
		assertEquals("taobao11", productCollectTops.get(0).getProductId());
		assertEquals(15, productCollectTops.get(4).getCollectedNum());
		assertEquals("taobao15", productCollectTops.get(4).getProductId());
	}

}
