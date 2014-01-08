package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.IosPushInfo;
import com.koudai.udc.domain.Platform;

public class IosPushInfoDAOHibernate extends HibernateDaoSupport implements IosPushInfoDAO {

	@Override
	public void save(IosPushInfo iosPushInfo) {
		getHibernateTemplate().save(iosPushInfo);
	}

	@Override
	public List<IosPushInfo> getPushInfosByCreateTimeAndStartAndEndPos(Date createTime, int start, int end) {
		return getHibernateTemplate().execute(new GetPushInfosHibernateCallback(createTime, start, end));
	}

	private static class GetPushInfosHibernateCallback implements HibernateCallback<List<IosPushInfo>> {

		private Date createTime;

		private int start;

		private int end;

		public GetPushInfosHibernateCallback(Date createTime, int start, int end) {
			this.createTime = createTime;
			this.start = start;
			this.end = end;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<IosPushInfo> doInHibernate(Session session) throws HibernateException, SQLException {
			final int max = end - start + 1;
			StringBuffer sql = new StringBuffer("select * from ios_push_info where createTime > ? and state=0 order by createTime desc,id desc");
			return session.createSQLQuery(sql.toString()).addEntity(IosPushInfo.class).setDate(0, createTime).setFirstResult(start).setMaxResults(max).list();
		}

	}

	@Override
	public List<IosPushInfo> getPushInfosByCreateTimeAndStartAndEndPosAndPlatform(Date createTime, int start, int end, Platform platform) {
		return getHibernateTemplate().execute(new GetPushInfosWithPlatformHibernateCallback(createTime, start, end, platform.toString()));
	}

	private static class GetPushInfosWithPlatformHibernateCallback implements HibernateCallback<List<IosPushInfo>> {

		private Date createTime;

		private int start;

		private int end;

		private String platform;

		public GetPushInfosWithPlatformHibernateCallback(Date createTime, int start, int end, String platform) {
			this.createTime = createTime;
			this.start = start;
			this.end = end;
			this.platform = platform;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<IosPushInfo> doInHibernate(Session session) throws HibernateException, SQLException {
			final int max = end - start + 1;
			StringBuffer sql = new StringBuffer("select * from ios_push_info where createTime > ? and platform = ? and state=0 order by createTime desc,id desc");
			return session.createSQLQuery(sql.toString()).addEntity(IosPushInfo.class).setDate(0, createTime).setString(1, platform).setFirstResult(start).setMaxResults(max).list();
		}

	}

}
