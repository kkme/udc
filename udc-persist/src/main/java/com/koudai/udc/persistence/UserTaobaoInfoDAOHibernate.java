package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.LoginPlatform;
import com.koudai.udc.domain.UserTaobaoInfo;

public class UserTaobaoInfoDAOHibernate extends HibernateDaoSupport implements UserTaobaoInfoDAO {

	@Override
	public void save(UserTaobaoInfo userTaobaoInfo) {
		getHibernateTemplate().save(userTaobaoInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserTaobaoInfo getUserTaobaoInfoByUserIdAndPlatform(String userId, LoginPlatform platform) {
		List<UserTaobaoInfo> userTaobaoInfos = getHibernateTemplate().find("from UserTaobaoInfo where userId = ? and platform = ?", new Object[] { userId, platform.toString() });
		if (userTaobaoInfos.size() != 0) {
			return (UserTaobaoInfo) userTaobaoInfos.get(0);
		}
		return null;
	}

}
