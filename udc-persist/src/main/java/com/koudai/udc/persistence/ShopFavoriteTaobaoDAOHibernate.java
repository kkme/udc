package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.ShopFavoriteTaobao;

public class ShopFavoriteTaobaoDAOHibernate extends HibernateDaoSupport implements ShopFavoriteTaobaoDAO {

	@Override
	public void save(ShopFavoriteTaobao shopFavoriteTaobao) {
		getHibernateTemplate().save(shopFavoriteTaobao);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShopFavoriteTaobao getShopByUserIdAndShopId(String userId, String shopId) {
		List<ShopFavoriteTaobao> shopFavoriteTaobaos = getHibernateTemplate().find("from ShopFavoriteTaobao sft where sft.userId = ? and sft.shopId = ?", new Object[] { userId, shopId });
		if (shopFavoriteTaobaos.size() != 0) {
			return (ShopFavoriteTaobao) shopFavoriteTaobaos.get(0);
		}
		return null;
	}

	@Override
	public int deleteShopsByUserId(String userId) {
		return getHibernateTemplate().execute(new DeleteShopsByUserIdHibernateCallback(userId));
	}

	private static class DeleteShopsByUserIdHibernateCallback implements HibernateCallback<Integer> {

		private String userId;

		public DeleteShopsByUserIdHibernateCallback(String userId) {
			this.userId = userId;
		}

		@Override
		public Integer doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("delete from shop_favorite_taobao where userID=:userId");
			return session.createSQLQuery(sql.toString()).setParameter("userId", userId).executeUpdate();
		}

	}

}
