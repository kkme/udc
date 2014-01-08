package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.AndroidPushInfo;

public class AndroidPushInfoDAOHibernate extends HibernateDaoSupport implements AndroidPushInfoDAO {

	@Override
	public void save(AndroidPushInfo androidPushInfo) {
		getHibernateTemplate().save(androidPushInfo);
	}

	@Override
	public AndroidPushInfo getLatestPushInfoByMachineIdAndCreateTime(String machineId, Date createTime) {
		List<AndroidPushInfo> list = getHibernateTemplate().execute(new GetLatestPushInfoByMachineIdAndCreateTimeHibernateCallback(machineId, createTime));
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	private static class GetLatestPushInfoByMachineIdAndCreateTimeHibernateCallback implements HibernateCallback<List<AndroidPushInfo>> {

		private String machineId;

		private Date createTime;

		public GetLatestPushInfoByMachineIdAndCreateTimeHibernateCallback(String machineId, Date createTime) {
			this.machineId = machineId;
			this.createTime = createTime;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<AndroidPushInfo> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select * from android_push_info where createTime > ? and machineID = ? and state = 0 order by createTime desc,id desc");
			return session.createSQLQuery(sql.toString()).addEntity(AndroidPushInfo.class).setDate(0, createTime).setParameter(1, machineId).setFirstResult(0).setMaxResults(1).list();
		}

	}

	@Override
	public List<AndroidPushInfo> getPushInfosByMachineIdAndCreateTime(String machineId, Date createTime) {
		return getHibernateTemplate().execute(new GetPushInfosByMachineIdAndCreateTimeHibernateCallback(machineId, createTime));
	}

	private static class GetPushInfosByMachineIdAndCreateTimeHibernateCallback implements HibernateCallback<List<AndroidPushInfo>> {

		private String machineId;

		private Date createTime;

		public GetPushInfosByMachineIdAndCreateTimeHibernateCallback(String machineId, Date createTime) {
			this.machineId = machineId;
			this.createTime = createTime;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<AndroidPushInfo> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select * from android_push_info where createTime > ? and machineID = ? and state = 0 order by createTime desc,id desc");
			return session.createSQLQuery(sql.toString()).addEntity(AndroidPushInfo.class).setDate(0, createTime).setParameter(1, machineId).list();
		}

	}

}
