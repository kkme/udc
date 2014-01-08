package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.DeviceInfo;
import com.koudai.udc.domain.Platform;

public class DeviceInfoDAOHibernate extends HibernateDaoSupport implements DeviceInfoDAO {

	@Override
	public void save(DeviceInfo deviceInfo) {
		getHibernateTemplate().save(deviceInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeviceInfo getDeviceInfoByMachineIdAndPlatform(String machineId, Platform platform) {
		List<DeviceInfo> deviceInfos = getHibernateTemplate().find("from DeviceInfo di where di.machineId = ? and di.platform = ?", new Object[] { machineId, platform.toString() });
		if (deviceInfos.size() != 0) {
			return (DeviceInfo) deviceInfos.get(0);
		}
		return null;
	}

	@Override
	public List<String> getTokensBySoftwarVersionAndPlatform(String fromVersion, String toVersion, Platform platform, int start, int max) {
		return getHibernateTemplate().execute(new GetTokensBySoftwarVersionAndPlatformHibernateCallback(fromVersion, toVersion, platform.toString(), start, max));
	}

	private static class GetTokensBySoftwarVersionAndPlatformHibernateCallback implements HibernateCallback<List<String>> {

		private String fromVersion;

		private String toVersion;

		private String platform;

		private int start;

		private int max;

		public GetTokensBySoftwarVersionAndPlatformHibernateCallback(String fromVersion, String toVersion, String platform, int start, int max) {
			this.fromVersion = fromVersion;
			this.toVersion = toVersion;
			this.platform = platform;
			this.start = start;
			this.max = max;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select token from device_info where softwareVersion >= ? and softwareVersion <= ? and platform = ? and token is not null order by id asc");
			return session.createSQLQuery(sql.toString()).setString(0, fromVersion).setString(1, toVersion).setString(2, platform).setFirstResult(start).setMaxResults(max).list();
		}

	}

}
