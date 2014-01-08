package com.koudai.udc.persistence;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.domain.ShopFavorite;

public class ShopFavoriteDAOHibernate extends HibernateDaoSupport implements ShopFavoriteDAO {

	@Override
	public void save(ShopFavorite shopFavorite) {
		getHibernateTemplate().save(shopFavorite);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShopFavorite getShopFavoriteByUserAndShopId(String userId, String shopId) {
		List<ShopFavorite> shopFavorites = getHibernateTemplate().find("from ShopFavorite sf where sf.userId = ? and sf.shopId = ?", new Object[] { userId, shopId });
		if (shopFavorites.size() != 0) {
			return (ShopFavorite) shopFavorites.get(0);
		}
		return null;
	}

	@Override
	public List<String> getAllShopIdsByUserIdOrderByTime(String userId) {
		return getHibernateTemplate().execute(new GetAllShopIdsByUserIdOrderByTimeHibernateCallback(userId));
	}

	private static class GetAllShopIdsByUserIdOrderByTimeHibernateCallback implements HibernateCallback<List<String>> {

		private String userId;

		public GetAllShopIdsByUserIdOrderByTimeHibernateCallback(String userId) {
			this.userId = userId;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select shopID from shop_favorite where userID = ? and status = 0 order by favoriteTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setString(0, userId).list();
		}

	}

	@Override
	public List<String> getShopIdsByUserIdAndStartAndEndPosOrderByTime(String userId, int start, int end) {
		return getHibernateTemplate().execute(new GetShopIdsByUserIdAndStartAndEndPosOrderByTimeHibernateCallback(userId, start, end));
	}

	private static class GetShopIdsByUserIdAndStartAndEndPosOrderByTimeHibernateCallback implements HibernateCallback<List<String>> {

		private String userId;

		private int start;

		private int end;

		public GetShopIdsByUserIdAndStartAndEndPosOrderByTimeHibernateCallback(String userId, int start, int end) {
			this.userId = userId;
			this.start = start;
			this.end = end;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
			final int max = end - start + 1;
			StringBuffer sql = new StringBuffer("select shopID from shop_favorite where userID = ? and status = 0 order by favoriteTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setString(0, userId).setFirstResult(start).setMaxResults(max).list();
		}

	}

	@Override
	public int cancelShop(String userId, String shopId) {
		return getHibernateTemplate().execute(new CancelShopHibernateCallback(userId, shopId));
	}

	private static class CancelShopHibernateCallback implements HibernateCallback<Integer> {

		private String userId;

		private String shopId;

		public CancelShopHibernateCallback(String userId, String shopId) {
			this.userId = userId;
			this.shopId = shopId;
		}

		@Override
		public Integer doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("update shop_favorite set status = 1 where userID=:userId and shopID=:shopId");
			return session.createSQLQuery(sql.toString()).setParameter("userId", userId).setParameter("shopId", shopId).executeUpdate();
		}

	}

	@Override
	public List<String> getAllShopIdsByUserId(String userId) {
		return getHibernateTemplate().execute(new GetAllShopIdsByUserIdHibernateCallback(userId));
	}

	private static class GetAllShopIdsByUserIdHibernateCallback implements HibernateCallback<List<String>> {

		private String userId;

		public GetAllShopIdsByUserIdHibernateCallback(String userId) {
			this.userId = userId;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select shopID from shop_favorite where userID = ? and status = 0");
			return session.createSQLQuery(sql.toString()).setString(0, userId).list();
		}

	}

	@Override
	public List<ShopFavorite> getShopsByUserIdAndShopIdsAndStatus(String userId, int status, List<String> shopIds) {
		return getHibernateTemplate().execute(new getShopsByUserIdAndShopIdsAndStatusHibernateCallback(userId, status, shopIds));
	}

	private static class getShopsByUserIdAndShopIdsAndStatusHibernateCallback implements HibernateCallback<List<ShopFavorite>> {

		private String userId;

		private int status;

		private List<String> shopIds;

		public getShopsByUserIdAndShopIdsAndStatusHibernateCallback(String userId, int status, List<String> shopIds) {
			this.userId = userId;
			this.status = status;
			this.shopIds = shopIds;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<ShopFavorite> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select * from shop_favorite where userID=:userId and shopID in (:shopIds) and status=:status");
			return session.createSQLQuery(sql.toString()).addEntity(ShopFavorite.class).setParameter("userId", userId).setParameterList("shopIds", shopIds).setParameter("status", status).list();
		}

	}

	@Override
	public int getFavoriteNumByUserId(String userId) {
		BigInteger num = getHibernateTemplate().execute(new GetFavoriteNumByUserIdHibernateCallback(userId));
		if (num == null) {
			return 0;
		}
		return num.intValue();
	}

	private static class GetFavoriteNumByUserIdHibernateCallback implements HibernateCallback<BigInteger> {

		private String userId;

		public GetFavoriteNumByUserIdHibernateCallback(String userId) {
			this.userId = userId;
		}

		@Override
		public BigInteger doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select count(*) from shop_favorite where userID=:userId and status = 0");
			return (BigInteger) session.createSQLQuery(sql.toString()).setParameter("userId", userId).uniqueResult();
		}
	}

	@Override
	public List<IdAndDate> getAllShopIdsAndFavoriteTimeByUserId(String userId) {
		return getHibernateTemplate().execute(new GetAllShopIdsAndFavoriteTimeByUserIdHibernateCallback(userId));
	}

	private static class GetAllShopIdsAndFavoriteTimeByUserIdHibernateCallback implements HibernateCallback<List<IdAndDate>> {

		private String userId;

		public GetAllShopIdsAndFavoriteTimeByUserIdHibernateCallback(String userId) {
			this.userId = userId;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<IdAndDate> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select shopID as id,favoriteTime as date from shop_favorite where userID=:userId and status=0");
			return session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(IdAndDate.class)).setString("userId", userId).list();
		}

	}

}
