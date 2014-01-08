package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.PriceRemindAnonymous;

public class PriceRemindAnonymousDAOHibernate extends HibernateDaoSupport implements PriceRemindAnonymousDAO {

	@Override
	public void save(PriceRemindAnonymous priceRemindAnonymous) {
		getHibernateTemplate().save(priceRemindAnonymous);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PriceRemindAnonymous getPriceRemindAnonymousByEmailAndProductId(String email, String productId) {
		List<PriceRemindAnonymous> priceReminds = getHibernateTemplate().find("from PriceRemindAnonymous where email = ? and productId = ?", new Object[] { email, productId });
		if (priceReminds.size() != 0) {
			return (PriceRemindAnonymous) priceReminds.get(0);
		}
		return null;
	}

}
