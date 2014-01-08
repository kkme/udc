package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.BijiaProduct;
import com.koudai.udc.domain.PriceRemind;

public class PriceRemindDAOHibernate extends HibernateDaoSupport implements PriceRemindDAO {

	@Override
	public void save(PriceRemind priceRemind) {
		getHibernateTemplate().save(priceRemind);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PriceRemind getPriceRemindByUserAndProductId(String userId, String productId) {
		List<PriceRemind> priceReminds = getHibernateTemplate().find("from PriceRemind where userId = ? and productId = ?", new Object[] { userId, productId });
		if (priceReminds.size() != 0) {
			return (PriceRemind) priceReminds.get(0);
		}
		return null;
	}

	@Override
	public List<BijiaProduct> getBijiaProductsByUserId(String userId) {
		return getHibernateTemplate().execute(new GetBijiaProductsByUserIdHibernateCallback(userId));
	}

	private static class GetBijiaProductsByUserIdHibernateCallback implements HibernateCallback<List<BijiaProduct>> {

		private String userId;

		public GetBijiaProductsByUserIdHibernateCallback(String userId) {
			this.userId = userId;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<BijiaProduct> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select productID as productId,noticeStatus as priceReduction,canRemind as canRemind,(subscribePrice-currentPrice) as balance from price_remind where userID = ? and canRemind = 1 and noticeStatus = 1 order by subscribeTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(BijiaProduct.class)).setString(0, userId).list();
		}

	}

	@Override
	public List<BijiaProduct> getBijiaProductsByUserIdAndStartAndEndPos(String userId, int start, int end) {
		return getHibernateTemplate().execute(new GetProductIdsByUserIdAndStartAndEndPosHibernateCallback(userId, start, end));
	}

	private static class GetProductIdsByUserIdAndStartAndEndPosHibernateCallback implements HibernateCallback<List<BijiaProduct>> {

		private String userId;

		private int start;

		private int end;

		public GetProductIdsByUserIdAndStartAndEndPosHibernateCallback(String userId, int start, int end) {
			this.userId = userId;
			this.start = start;
			this.end = end;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<BijiaProduct> doInHibernate(Session session) throws HibernateException, SQLException {
			final int max = end - start + 1;
			StringBuffer sql = new StringBuffer("select productID as productId,noticeStatus as priceReduction,canRemind as canRemind,(subscribePrice-currentPrice) as balance from price_remind where userID = ? and canRemind = 1 and noticeStatus = 1 order by subscribeTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(BijiaProduct.class)).setString(0, userId).setFirstResult(start).setMaxResults(max).list();
		}

	}

	@Override
	public List<String> getProductIdsByUserId(String userId) {
		return getHibernateTemplate().execute(new GetProductIdsByUserIdHibernateCallback(userId));
	}

	private static class GetProductIdsByUserIdHibernateCallback implements HibernateCallback<List<String>> {

		private String userId;

		public GetProductIdsByUserIdHibernateCallback(String userId) {
			this.userId = userId;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select productID from price_remind where userID = ? and canRemind = 1 and noticeStatus = 1 order by subscribeTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setString(0, userId).list();
		}

	}

}
