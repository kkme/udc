package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.ProductPush;

public class ProductPushDAOHibernate extends HibernateDaoSupport implements ProductPushDAO {

	@Override
	public void save(ProductPush productPush) {
		getHibernateTemplate().save(productPush);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductPush getProductPushByMachineAndProductId(String machineId, String productId) {
		List<ProductPush> productPushs = getHibernateTemplate().find("from ProductPush where machineId = ? and productId = ?", new Object[] { machineId, productId });
		if (productPushs.size() != 0) {
			return (ProductPush) productPushs.get(0);
		}
		return null;
	}

}
