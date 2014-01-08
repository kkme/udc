package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserQQInfo;

public class UserQQInfoDAOHibernate extends HibernateDaoSupport implements UserQQInfoDAO {

	@Override
	public void save(UserQQInfo userQQInfo) {
		getHibernateTemplate().save(userQQInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserQQInfo getUserQQInfoByUserIdAndPlaform(String userId, LoginPlatform platform) {
		List<UserQQInfo> userQQInfos = getHibernateTemplate().find("from UserQQInfo where userId = ? and platform = ?", new Object[] { userId, platform.toString() });
		if (userQQInfos.size() != 0) {
			return (UserQQInfo) userQQInfos.get(0);
		}
		return null;
	}

}
