package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.TaobaoBindingInfo;

public class TaobaoBindingInfoDAOHibernate extends HibernateDaoSupport implements TaobaoBindingInfoDAO {

	@Override
	public void save(TaobaoBindingInfo taobaoBindingInfo) {
		getHibernateTemplate().save(taobaoBindingInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TaobaoBindingInfo getTaobaoBindingInfoByUserId(String userId) {
		List<TaobaoBindingInfo> taobaoBindingInfos = getHibernateTemplate().find("from TaobaoBindingInfo where userId = ?", new Object[] { userId });
		if (taobaoBindingInfos.size() != 0) {
			return (TaobaoBindingInfo) taobaoBindingInfos.get(0);
		}
		return null;
	}

}
