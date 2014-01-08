package com.koudai.udc.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.ShopFavoriteTop;
import com.koudai.udc.domain.factory.ShopFavoriteTopFactory;
import com.koudai.udc.utils.SpringFactory;

public class ShopFavoriteTopDAOHibernateTest extends TestCase {

	private ShopFavoriteTopDAO shopFavoriteTopDAOW;

	private ShopFavoriteTopDAO shopFavoriteTopDAOR;

	private ShopFavoriteTopFactory shopFavoriteTopFactory;

	private Date startTime;

	private Date endTime;

	@Override
	protected void setUp() throws Exception {
		shopFavoriteTopDAOW = (ShopFavoriteTopDAO) SpringFactory.bean("shopFavoriteTopDAOW");
		shopFavoriteTopDAOR = (ShopFavoriteTopDAO) SpringFactory.bean("shopFavoriteTopDAOR");
		shopFavoriteTopFactory = (ShopFavoriteTopFactory) SpringFactory.bean("shopFavoriteTopFactory");
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
		ShopFavoriteTop ShopFavoriteTop = shopFavoriteTopFactory.newInstance("shop12345", 123, "2012-02-21");
		shopFavoriteTopDAOW.save(ShopFavoriteTop);
		assertNotNull(ShopFavoriteTop.getId());
	}

	public void testGetTopsByStartAndEndTimeByRD() throws Exception {
		List<ShopFavoriteTop> shopFavoriteTops = shopFavoriteTopDAOR.getTopsByStartAndEndTime(startTime, endTime);
		assertEquals(10, shopFavoriteTops.size());
		assertEquals(1, shopFavoriteTops.get(0).getFavoritedNum());
		assertEquals("shop1", shopFavoriteTops.get(0).getShopId());
		assertEquals(10, shopFavoriteTops.get(9).getFavoritedNum());
		assertEquals("shop10", shopFavoriteTops.get(9).getShopId());
	}

	public void testGetTopsByStartAndEndTimeByWR() throws Exception {
		List<ShopFavoriteTop> shopFavoriteTops = shopFavoriteTopDAOW.getTopsByStartAndEndTime(startTime, endTime);
		assertEquals(10, shopFavoriteTops.size());
		assertEquals(1, shopFavoriteTops.get(0).getFavoritedNum());
		assertEquals("shop1", shopFavoriteTops.get(0).getShopId());
		assertEquals(10, shopFavoriteTops.get(9).getFavoritedNum());
		assertEquals("shop10", shopFavoriteTops.get(9).getShopId());
	}

}
