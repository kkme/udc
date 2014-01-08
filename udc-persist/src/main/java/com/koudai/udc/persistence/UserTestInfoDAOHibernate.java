package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.UserTestInfo;

public class UserTestInfoDAOHibernate extends HibernateDaoSupport implements UserTestInfoDAO {

	@Override
	public void save(UserTestInfo userTestInfo) {
		getHibernateTemplate().save(userTestInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserTestInfo> getUserTestInfoListByUserId(String userId) {
		return getHibernateTemplate().find("from UserTestInfo uti where uti.userId = ?", new Object[] { userId });
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserTestInfo getUserTestInfoByUserIdAndQuestionId(String userId, String questionId) {
		List<UserTestInfo> userTestInfos = getHibernateTemplate().find("from UserTestInfo uti where uti.userId = ? and uti.questionId = ?", new Object[] { userId, questionId });
		if (userTestInfos.size() != 0) {
			return (UserTestInfo) userTestInfos.get(0);
		}
		return null;
	}

}
