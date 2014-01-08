package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.ProductCollectedCount;

public class ProductCollectedCountDAOHibernate extends HibernateDaoSupport implements ProductCollectedCountDAO {

	@Override
	public void save(ProductCollectedCount productCollectedCount) {
		getHibernateTemplate().save(productCollectedCount);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductCollectedCount getProductCollectedCountByProductId(String productId) {
		List<ProductCollectedCount> productCollectedCounts = getHibernateTemplate().find("from ProductCollectedCount where productId = ?", new Object[] { productId });
		if (productCollectedCounts.size() != 0) {
			return (ProductCollectedCount) productCollectedCounts.get(0);
		}
		return null;
	}

	@Override
	public int getCollectedCountByProductId(String productId) {
		Integer count = getHibernateTemplate().execute(new GetCollectedCountByProductIdHibernateCallback(productId));
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}

	private static class GetCollectedCountByProductIdHibernateCallback implements HibernateCallback<Integer> {

		private String productId;

		public GetCollectedCountByProductIdHibernateCallback(String productId) {
			this.productId = productId;
		}

		@Override
		public Integer doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer hql = new StringBuffer("select collectedCount from product_collected_count where productID=:productId");
			return (Integer) session.createSQLQuery(hql.toString()).setParameter("productId", productId).uniqueResult();
		}

	}

}
