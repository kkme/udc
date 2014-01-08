package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.UserBasicInfo;

public class UserBasicInfoDAOHibernate extends HibernateDaoSupport implements UserBasicInfoDAO {

	@Override
	public void save(UserBasicInfo userBasicInfo) {
		getHibernateTemplate().save(userBasicInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserBasicInfo getUserBasicInfoByThirdPartyId(String thirdPartyId) {
		List<UserBasicInfo> userBasicInfos = getHibernateTemplate().find("from UserBasicInfo ubi where ubi.thirdId = ?", new Object[] { thirdPartyId });
		if (userBasicInfos.size() != 0) {
			return (UserBasicInfo) userBasicInfos.get(0);
		}
		return null;
	}

}
