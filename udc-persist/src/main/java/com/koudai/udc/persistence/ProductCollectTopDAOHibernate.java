package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.ProductCollectTop;

public class ProductCollectTopDAOHibernate extends HibernateDaoSupport implements ProductCollectTopDAO {

	@Override
	public void save(ProductCollectTop productCollectTop) {
		getHibernateTemplate().save(productCollectTop);
	}

	@Override
	public List<ProductCollectTop> getTopsByTypeIdAndStartAndEndTime(String typeId, Date startTime, Date endTime, int useType) {
		return getHibernateTemplate().execute(new GetTopsByTypeIdAndStartAndEndTimeHibernateCallback(typeId, startTime, endTime, useType));
	}

	public static class GetTopsByTypeIdAndStartAndEndTimeHibernateCallback implements HibernateCallback<List<ProductCollectTop>> {

		private String typeId;

		private Date startTime;

		private Date endTime;

		private int useType;

		public GetTopsByTypeIdAndStartAndEndTimeHibernateCallback(String typeId, Date startTime, Date endTime, int useType) {
			this.typeId = typeId;
			this.startTime = startTime;
			this.endTime = endTime;
			this.useType = useType;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<ProductCollectTop> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select * from product_collect_top where typeID=:typeId and statTime>=:startTime and statTime<:endTime and useType=:useType");
			return session.createSQLQuery(sql.toString()).addEntity(ProductCollectTop.class).setParameter("typeId", typeId).setDate("startTime", startTime).setDate("endTime", endTime).setInteger("useType", useType).list();
		}

	}

	@Override
	public List<ProductCollectTop> getTop30ByStartAndEndTime(Date startTime, Date endTime) {
		return getHibernateTemplate().execute(new GetTopsByStartAndEndTimeHibernateCallback(startTime, endTime));
	}

	public static class GetTopsByStartAndEndTimeHibernateCallback implements HibernateCallback<List<ProductCollectTop>> {

		private Date startTime;

		private Date endTime;

		public GetTopsByStartAndEndTimeHibernateCallback(Date startTime, Date endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<ProductCollectTop> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select * from product_collect_top where statTime>=:startTime and statTime<:endTime and useType=1");
			return session.createSQLQuery(sql.toString()).addEntity(ProductCollectTop.class).setDate("startTime", startTime).setDate("endTime", endTime).list();
		}

	}

}
