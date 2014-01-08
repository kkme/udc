package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserSinaInfo;

public class UserSinaInfoDAOHibernate extends HibernateDaoSupport implements UserSinaInfoDAO {

	@Override
	public void save(UserSinaInfo userSinaInfo) {
		getHibernateTemplate().save(userSinaInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserSinaInfo getUserSinaInfoByUserIdAndPlatform(String userId, LoginPlatform platform) {
		List<UserSinaInfo> userSinaInfos = getHibernateTemplate().find("from UserSinaInfo where userId = ? and platform = ?", new Object[] { userId, platform.toString() });
		if (userSinaInfos.size() != 0) {
			return (UserSinaInfo) userSinaInfos.get(0);
		}
		return null;
	}

}
