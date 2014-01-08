package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.ShopFavoritedCount;

public class ShopFavoritedCountDAOHibernate extends HibernateDaoSupport implements ShopFavoritedCountDAO {

	@Override
	public void save(ShopFavoritedCount shopFavoritedCount) {
		getHibernateTemplate().save(shopFavoritedCount);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShopFavoritedCount getShopFavoritedCountByShopId(String shopId) {
		List<ShopFavoritedCount> shopFavoritedCounts = getHibernateTemplate().find("from ShopFavoritedCount where shopId = ?", new Object[] { shopId });
		if (shopFavoritedCounts.size() != 0) {
			return (ShopFavoritedCount) shopFavoritedCounts.get(0);
		}
		return null;
	}

	@Override
	public int getFavoritedCountByShopId(String shopId) {
		Integer count = getHibernateTemplate().execute(new GetFavoritedCountByShopIdHibernateCallback(shopId));
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}

	private static class GetFavoritedCountByShopIdHibernateCallback implements HibernateCallback<Integer> {

		private String shopId;

		public GetFavoritedCountByShopIdHibernateCallback(String shopId) {
			this.shopId = shopId;
		}

		@Override
		public Integer doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer hql = new StringBuffer("select favoritedCount from shop_favorited_count where shopID=:shopId");
			return (Integer) session.createSQLQuery(hql.toString()).setParameter("shopId", shopId).uniqueResult();
		}

	}

}
