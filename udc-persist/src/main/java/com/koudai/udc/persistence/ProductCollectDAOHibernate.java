package com.koudai.udc.persistence;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.domain.ProductCollect;

public class ProductCollectDAOHibernate extends HibernateDaoSupport implements ProductCollectDAO {

	@Override
	public void save(ProductCollect productCollect) {
		getHibernateTemplate().save(productCollect);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductCollect getProductCollectByUserAndProductId(String userId, String productId) {
		List<ProductCollect> productCollects = getHibernateTemplate().find("from ProductCollect pc where pc.userId = ? and pc.productId = ?", new Object[] { userId, productId });
		if (productCollects.size() != 0) {
			return (ProductCollect) productCollects.get(0);
		}
		return null;
	}

	@Override
	public List<String> getAllProductIdsByUserIdOrderByTime(String userId) {
		return getHibernateTemplate().execute(new GetAllProductIdsByUserIdOrderByTimeHibernateCallback(userId));
	}

	private static class GetAllProductIdsByUserIdOrderByTimeHibernateCallback implements HibernateCallback<List<String>> {

		private String userId;

		public GetAllProductIdsByUserIdOrderByTimeHibernateCallback(String userId) {
			this.userId = userId;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select productID from product_collect where userID = ? and status = 0 order by collectTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setString(0, userId).list();
		}

	}

	@Override
	public List<String> getProductIdsByUserIdAndStartAndEndPosOrderByTime(String userId, int start, int end) {
		return getHibernateTemplate().execute(new GetProductIdsByUserIdAndStartAndEndPosOrderByTimeHibernateCallback(userId, start, end));
	}

	private static class GetProductIdsByUserIdAndStartAndEndPosOrderByTimeHibernateCallback implements HibernateCallback<List<String>> {

		private String userId;

		private int start;

		private int end;

		public GetProductIdsByUserIdAndStartAndEndPosOrderByTimeHibernateCallback(String userId, int start, int end) {
			this.userId = userId;
			this.start = start;
			this.end = end;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
			final int max = end - start + 1;
			StringBuffer sql = new StringBuffer("select productID from product_collect where userID = ? and status = 0 order by collectTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setString(0, userId).setFirstResult(start).setMaxResults(max).list();
		}

	}

	@Override
	public int cancelProduct(String userId, String productId) {
		return getHibernateTemplate().execute(new CancelProductHibernateCallback(userId, productId));
	}

	private static class CancelProductHibernateCallback implements HibernateCallback<Integer> {

		private String userId;

		private String productId;

		public CancelProductHibernateCallback(String userId, String productId) {
			this.userId = userId;
			this.productId = productId;
		}

		@Override
		public Integer doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("update product_collect set status = 1 where userID=:userId and productID=:productId");
			return session.createSQLQuery(sql.toString()).setParameter("userId", userId).setParameter("productId", productId).executeUpdate();
		}

	}

	@Override
	public List<String> getAllProductIdsByUserId(String userId) {
		return getHibernateTemplate().execute(new GetAllProductIdsByUserIdHibernateCallback(userId));
	}

	private static class GetAllProductIdsByUserIdHibernateCallback implements HibernateCallback<List<String>> {

		private String userId;

		public GetAllProductIdsByUserIdHibernateCallback(String userId) {
			this.userId = userId;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select productID from product_collect where userID = ? and status = 0");
			return session.createSQLQuery(sql.toString()).setString(0, userId).list();
		}

	}

	@Override
	public List<ProductCollect> getProductsByUserIdAndProductIdsAndStatus(String userId, int status, List<String> productIds) {
		return getHibernateTemplate().execute(new GetProductsByUserIdAndProductIdsStatusHibernateCallback(userId, status, productIds));
	}

	public static class GetProductsByUserIdAndProductIdsStatusHibernateCallback implements HibernateCallback<List<ProductCollect>> {

		private String userId;

		private int status;

		private List<String> productIds;

		public GetProductsByUserIdAndProductIdsStatusHibernateCallback(String userId, int status, List<String> productIds) {
			this.userId = userId;
			this.status = status;
			this.productIds = productIds;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<ProductCollect> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select * from product_collect where userID=:userId and productID in (:productIds) and status=:status");
			return session.createSQLQuery(sql.toString()).addEntity(ProductCollect.class).setParameter("userId", userId).setParameterList("productIds", productIds).setParameter("status", status).list();
		}

	}

	@Override
	public int getCollectNumByUserId(String userId) {
		BigInteger num = getHibernateTemplate().execute(new GetCollectNumByUserIdHibernateCallback(userId));
		if (num == null) {
			return 0;
		}
		return num.intValue();
	}

	private static class GetCollectNumByUserIdHibernateCallback implements HibernateCallback<BigInteger> {

		private String userId;

		public GetCollectNumByUserIdHibernateCallback(String userId) {
			this.userId = userId;
		}

		@Override
		public BigInteger doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select count(*) from product_collect where userID=:userId and status = 0");
			return (BigInteger) session.createSQLQuery(sql.toString()).setParameter("userId", userId).uniqueResult();
		}
	}

	@Override
	public int getCollectNumByUserIdAndEndTime(String userId, Date endTime) {
		BigInteger num = getHibernateTemplate().execute(new GetCollectNumByUserIdAndEndTimeHibernateCallback(userId, endTime));
		if (num == null) {
			return 0;
		}
		return num.intValue();
	}

	private static class GetCollectNumByUserIdAndEndTimeHibernateCallback implements HibernateCallback<BigInteger> {

		private String userId;

		private Date endTime;

		public GetCollectNumByUserIdAndEndTimeHibernateCallback(String userId, Date endTime) {
			this.userId = userId;
			this.endTime = endTime;
		}

		@Override
		public BigInteger doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select count(*) from product_collect where userID=:userId and collectTime<=:endTime and status = 0");
			return (BigInteger) session.createSQLQuery(sql.toString()).setString("userId", userId).setDate("endTime", endTime).uniqueResult();
		}
	}

	@Override
	public List<IdAndDate> getProductIdAndCollectTimeByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime) {
		return getHibernateTemplate().execute(new GetProductIdAndCollectTimeByUserIdAndStartAndEndTimeHibernateCallback(userId, startTime, endTime));
	}

	private static class GetProductIdAndCollectTimeByUserIdAndStartAndEndTimeHibernateCallback implements HibernateCallback<List<IdAndDate>> {

		private String userId;

		private Date startTime;

		private Date endTime;

		public GetProductIdAndCollectTimeByUserIdAndStartAndEndTimeHibernateCallback(String userId, Date startTime, Date endTime) {
			this.userId = userId;
			this.startTime = startTime;
			this.endTime = endTime;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<IdAndDate> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select productID as id,collectTime as date from product_collect where userID=:userId and collectTime>=:startTime and collectTime<=:endTime and status=0 order by collectTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(IdAndDate.class)).setString("userId", userId).setDate("startTime", startTime).setDate("endTime", endTime).list();
		}

	}

}