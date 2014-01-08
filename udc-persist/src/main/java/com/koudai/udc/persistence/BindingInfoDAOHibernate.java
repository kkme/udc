package com.koudai.udc.persistence;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.BindingInfo;

public class BindingInfoDAOHibernate extends HibernateDaoSupport implements BindingInfoDAO {

	@Override
	public void save(BindingInfo bindingInfo) {
		getHibernateTemplate().save(bindingInfo);
	}

}
