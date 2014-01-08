package com.koudai.udc.persistence;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.koudai.udc.domain.ShopFavorite;
import com.koudai.udc.domain.ShopStatus;
import com.koudai.udc.domain.factory.ShopFavoriteFactory;
import com.koudai.udc.utils.SpringFactory;

public class ShopFavoriteDAOHibernateTest extends TestCase {

	private ShopFavoriteDAO shopFavoriteDAOW;

	private ShopFavoriteDAO shopFavoriteDAOR;

	private ShopFavoriteFactory shopFavoriteFactory;

	private List<String> userIds = new ArrayList<String>();

	@Override
	protected void setUp() throws Exception {
		userIds.add("@sina:123");
		shopFavoriteDAOW = (ShopFavoriteDAO) SpringFactory.bean("shopFavoriteDAOW");
		shopFavoriteDAOR = (ShopFavoriteDAO) SpringFactory.bean("shopFavoriteDAOR");
		shopFavoriteFactory = (ShopFavoriteFactory) SpringFactory.bean("shopFavoriteFactory");
	}

	public void testSaveSuccessfully() throws Exception {
		ShopFavorite shopFavorite = shopFavoriteFactory.newInstance("123", "m1", "", "@sina:789", "", "", "", "");
		shopFavoriteDAOW.save(shopFavorite);
		assertNotNull(shopFavorite.getId());
	}

	public void testGetShopFavoriteByUserAndShopIdByRD() throws Exception {
		ShopFavorite shopFavorite = shopFavoriteDAOR.getShopFavoriteByUserAndShopId("@sina:123", "s1");
		assertNotNull(shopFavorite.getId());
		shopFavorite = shopFavoriteDAOR.getShopFavoriteByUserAndShopId("@sina:456", "s1");
		assertNull(shopFavorite);
	}

	public void testGetShopFavoriteByUserAndShopIdByWR() throws Exception {
		ShopFavorite shopFavorite = shopFavoriteDAOW.getShopFavoriteByUserAndShopId("@sina:123", "s1");
		assertNotNull(shopFavorite.getId());
		shopFavorite = shopFavoriteDAOW.getShopFavoriteByUserAndShopId("@sina:456", "s1");
		assertNull(shopFavorite);
	}

	public void testGetAllShopIdsByUserIdOrderByTimeByRD() throws Exception {
		List<String> shopIds = shopFavoriteDAOR.getAllShopIdsByUserIdOrderByTime("@sina:123");
		assertEquals(6, shopIds.size());
		assertEquals("s2", shopIds.get(0));
		assertEquals("s6", shopIds.get(1));
		assertEquals("s5", shopIds.get(2));
		assertEquals("s4", shopIds.get(3));
		assertEquals("s3", shopIds.get(4));
		assertEquals("s1", shopIds.get(5));
		shopIds = shopFavoriteDAOR.getAllShopIdsByUserIdOrderByTime("@sina:456");
		assertEquals(0, shopIds.size());
	}

	public void testGetAllShopIdsByUserIdOrderByTimeByWR() throws Exception {
		List<String> shopIds = shopFavoriteDAOW.getAllShopIdsByUserIdOrderByTime("@sina:123");
		assertEquals(6, shopIds.size());
		assertEquals("s2", shopIds.get(0));
		assertEquals("s6", shopIds.get(1));
		assertEquals("s5", shopIds.get(2));
		assertEquals("s4", shopIds.get(3));
		assertEquals("s3", shopIds.get(4));
		assertEquals("s1", shopIds.get(5));
		shopIds = shopFavoriteDAOW.getAllShopIdsByUserIdOrderByTime("@sina:456");
		assertEquals(0, shopIds.size());
	}

	public void testGetShopIdsByUserIdAndStartAndEndPosOrderByTimeByRD() throws Exception {
		List<String> shopIds = shopFavoriteDAOR.getShopIdsByUserIdAndStartAndEndPosOrderByTime("@sina:123", 0, 2);
		assertEquals(3, shopIds.size());
		assertEquals("s2", shopIds.get(0));
		assertEquals("s6", shopIds.get(1));
		assertEquals("s5", shopIds.get(2));
		shopIds = shopFavoriteDAOR.getShopIdsByUserIdAndStartAndEndPosOrderByTime("@sina:456", 0, 2);
		assertEquals(0, shopIds.size());
	}

	public void testGetShopIdsByUserIdAndStartAndEndPosOrderByTimeByWR() throws Exception {
		List<String> shopIds = shopFavoriteDAOW.getShopIdsByUserIdAndStartAndEndPosOrderByTime("@sina:123", 0, 2);
		assertEquals(3, shopIds.size());
		assertEquals("s2", shopIds.get(0));
		assertEquals("s6", shopIds.get(1));
		assertEquals("s5", shopIds.get(2));
		shopIds = shopFavoriteDAOW.getShopIdsByUserIdAndStartAndEndPosOrderByTime("@sina:456", 0, 2);
		assertEquals(0, shopIds.size());
	}

	public void testGetAllShopIdsByUserIdByRD() throws Exception {
		List<String> shopIds = shopFavoriteDAOR.getAllShopIdsByUserId("@sina:123");
		assertEquals(6, shopIds.size());
		assertEquals("s1", shopIds.get(0));
		assertEquals("s2", shopIds.get(1));
		assertEquals("s3", shopIds.get(2));
		assertEquals("s4", shopIds.get(3));
		assertEquals("s5", shopIds.get(4));
		assertEquals("s6", shopIds.get(5));
		shopIds = shopFavoriteDAOR.getAllShopIdsByUserIdOrderByTime("@sina:456");
		assertEquals(0, shopIds.size());
	}

	public void testGetAllShopIdsByUserIdByWR() throws Exception {
		List<String> shopIds = shopFavoriteDAOW.getAllShopIdsByUserId("@sina:123");
		assertEquals(6, shopIds.size());
		assertEquals("s1", shopIds.get(0));
		assertEquals("s2", shopIds.get(1));
		assertEquals("s3", shopIds.get(2));
		assertEquals("s4", shopIds.get(3));
		assertEquals("s5", shopIds.get(4));
		assertEquals("s6", shopIds.get(5));
		shopIds = shopFavoriteDAOW.getAllShopIdsByUserIdOrderByTime("@sina:456");
		assertEquals(0, shopIds.size());
	}

	public void testGetShopsByMachineAndUserIdAndShopIdsAndStatusByRD() throws Exception {
		List<String> shopIds = new ArrayList<String>();
		shopIds.add("s1");
		shopIds.add("s2");
		shopIds.add("s3");
		List<ShopFavorite> shops = shopFavoriteDAOR.getShopsByUserIdAndShopIdsAndStatus("@sina:123", ShopStatus.Normal.getCode(), shopIds);
		assertEquals(3, shops.size());
	}

	public void testGetShopsByMachineAndUserIdAndShopIdsAndStatusByWR() throws Exception {
		List<String> shopIds = new ArrayList<String>();
		shopIds.add("s1");
		shopIds.add("s2");
		shopIds.add("s3");
		List<ShopFavorite> shops = shopFavoriteDAOW.getShopsByUserIdAndShopIdsAndStatus("@sina:123", ShopStatus.Normal.getCode(), shopIds);
		assertEquals(3, shops.size());
	}

	public void testGetFavoriteNumByUserIdByRD() throws Exception {
		int num = shopFavoriteDAOR.getFavoriteNumByUserId("@sina:123");
		assertEquals(6, num);
		num = shopFavoriteDAOR.getFavoriteNumByUserId("@sina:456");
		assertEquals(0, num);
	}

	public void testGetFavoriteNumByUserIdByWR() throws Exception {
		int num = shopFavoriteDAOW.getFavoriteNumByUserId("@sina:123");
		assertEquals(6, num);
		num = shopFavoriteDAOW.getFavoriteNumByUserId("@sina:456");
		assertEquals(0, num);
	}

}
