package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.UserActivation;

public class UserActivationDAOHibernate extends HibernateDaoSupport implements UserActivationDAO {

	@Override
	public void save(UserActivation userActivation) {
		getHibernateTemplate().save(userActivation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserActivation getUserActivationByUserId(String userId) {
		List<UserActivation> userActivations = getHibernateTemplate().find("from UserActivation ua where ua.userId = ?", new Object[] { userId });
		if (userActivations.size() != 0) {
			return (UserActivation) userActivations.get(0);
		}
		return null;
	}

}
