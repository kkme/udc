package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.ProductCollectTaobao;

public class ProductCollectTaobaoDAOHibernate extends HibernateDaoSupport implements ProductCollectTaobaoDAO {

	@Override
	public void save(ProductCollectTaobao productCollectTaobao) {
		getHibernateTemplate().save(productCollectTaobao);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductCollectTaobao getProductByUserIdAndProductId(String userId, String productId) {
		List<ProductCollectTaobao> productCollectTaobaos = getHibernateTemplate().find("from ProductCollectTaobao pct where pct.userId = ? and pct.productId = ?", new Object[] { userId, productId });
		if (productCollectTaobaos.size() != 0) {
			return (ProductCollectTaobao) productCollectTaobaos.get(0);
		}
		return null;
	}

	@Override
	public int deleteProductsByUserId(String userId) {
		return getHibernateTemplate().execute(new DeleteProductsByUserIdHibernateCallback(userId));
	}

	private static class DeleteProductsByUserIdHibernateCallback implements HibernateCallback<Integer> {

		private String userId;

		public DeleteProductsByUserIdHibernateCallback(String userId) {
			this.userId = userId;
		}

		@Override
		public Integer doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("delete from product_collect_taobao where userID=:userId");
			return session.createSQLQuery(sql.toString()).setParameter("userId", userId).executeUpdate();
		}

	}

}
