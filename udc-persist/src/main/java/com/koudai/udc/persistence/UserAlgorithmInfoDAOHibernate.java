package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.UserAlgorithmInfo;

public class UserAlgorithmInfoDAOHibernate extends HibernateDaoSupport implements UserAlgorithmInfoDAO {

	@Override
	public void save(UserAlgorithmInfo userAlgorithmInfo) {
		getHibernateTemplate().save(userAlgorithmInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserAlgorithmInfo getUserAlgorithmInfoByUserId(String userId) {
		List<UserAlgorithmInfo> userAlgorithmInfos = getHibernateTemplate().find("from UserAlgorithmInfo where userId = ?", new Object[] { userId });
		if (userAlgorithmInfos.size() != 0) {
			return (UserAlgorithmInfo) userAlgorithmInfos.get(0);
		}
		return null;
	}

}
