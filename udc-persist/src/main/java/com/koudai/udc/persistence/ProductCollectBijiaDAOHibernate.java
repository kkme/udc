package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.BijiaProduct;
import com.koudai.udc.domain.ProductCollectBijia;

public class ProductCollectBijiaDAOHibernate extends HibernateDaoSupport implements ProductCollectBijiaDAO {

	@Override
	public void save(ProductCollectBijia productCollectBijia) {
		getHibernateTemplate().save(productCollectBijia);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductCollectBijia getProductCollectBijiaByUserAndProductId(String userId, String productId) {
		List<ProductCollectBijia> productCollectBijias = getHibernateTemplate().find("from ProductCollectBijia where userId = ? and productId = ?", new Object[] { userId, productId });
		if (productCollectBijias.size() != 0) {
			return (ProductCollectBijia) productCollectBijias.get(0);
		}
		return null;
	}

	@Override
	public List<BijiaProduct> getBijiaProductsByUserId(String userId, List<String> filterableIds) {
		return getHibernateTemplate().execute(new GetBijiaProductsByUserIdHibernateCallback(userId, filterableIds));
	}

	private static class GetBijiaProductsByUserIdHibernateCallback implements HibernateCallback<List<BijiaProduct>> {

		private String userId;

		private List<String> filterableIds;

		public GetBijiaProductsByUserIdHibernateCallback(String userId, List<String> filterableIds) {
			this.userId = userId;
			this.filterableIds = filterableIds;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<BijiaProduct> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select productID as productId,priceReduction as priceReduction from product_collect_bijia where userID=:userId and status=0 ");
			if (filterableIds.isEmpty()) {
				sql.append("order by collectTime desc,id desc");
				return session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(BijiaProduct.class)).setParameter("userId", userId).list();
			}
			sql.append("and productID not in (:filterableIds) order by collectTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(BijiaProduct.class)).setParameter("userId", userId).setParameterList("filterableIds", filterableIds).list();
		}

	}

	@Override
	public List<BijiaProduct> getBijiaProductsByUserIdAndStartAndEndPos(String userId, List<String> filterableIds, int start, int end) {
		return getHibernateTemplate().execute(new GetBijiaProductsByUserIdAndStartAndEndPosHibernateCallback(userId, filterableIds, start, end));
	}

	private static class GetBijiaProductsByUserIdAndStartAndEndPosHibernateCallback implements HibernateCallback<List<BijiaProduct>> {

		private String userId;

		private List<String> filterableIds;

		private int start;

		private int end;

		public GetBijiaProductsByUserIdAndStartAndEndPosHibernateCallback(String userId, List<String> filterableIds, int start, int end) {
			this.userId = userId;
			this.filterableIds = filterableIds;
			this.start = start;
			this.end = end;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<BijiaProduct> doInHibernate(Session session) throws HibernateException, SQLException {
			final int max = end - start + 1;
			StringBuffer sql = new StringBuffer("select productID as productId,priceReduction as priceReduction from product_collect_bijia where userID=:userId and status=0 ");
			if (filterableIds.isEmpty()) {
				sql.append("order by collectTime desc,id desc");
				return session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(BijiaProduct.class)).setParameter("userId", userId).setFirstResult(start).setMaxResults(max).list();
			}
			sql.append("and productID not in (:filterableIds) order by collectTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(BijiaProduct.class)).setParameter("userId", userId).setParameterList("filterableIds", filterableIds).setFirstResult(start).setMaxResults(max).list();
		}

	}

}
