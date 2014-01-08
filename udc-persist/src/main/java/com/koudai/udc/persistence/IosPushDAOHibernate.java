package com.koudai.udc.persistence;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.IosPush;

public class IosPushDAOHibernate extends HibernateDaoSupport implements IosPushDAO {

	@Override
	public void save(IosPush iosPush) {
		getHibernateTemplate().save(iosPush);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IosPush getIosPushByUUID(String uuid) {
		List<IosPush> iosPushs = getHibernateTemplate().find("from IosPush where uuid = ?", new Object[] { uuid });
		if (iosPushs.size() != 0) {
			return (IosPush) iosPushs.get(0);
		}
		return null;
	}

}
