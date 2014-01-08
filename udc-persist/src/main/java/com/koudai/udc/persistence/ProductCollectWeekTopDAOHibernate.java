package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.ProductCollectWeekTop;

public class ProductCollectWeekTopDAOHibernate extends HibernateDaoSupport implements ProductCollectWeekTopDAO {

	@Override
	public void save(ProductCollectWeekTop weekTop) {
		getHibernateTemplate().save(weekTop);
	}

	@Override
	public List<ProductCollectWeekTop> getWeekTopsByTypeIdAndWeekAndYear(String typeId, int week, int year) {
		return getHibernateTemplate().execute(new GetWeekTopsByTypeIdAndWeekAndYearHibernateCallback(typeId, week, year));
	}

	private static class GetWeekTopsByTypeIdAndWeekAndYearHibernateCallback implements HibernateCallback<List<ProductCollectWeekTop>> {

		private String typeId;

		private int week;

		private int year;

		public GetWeekTopsByTypeIdAndWeekAndYearHibernateCallback(String typeId, int week, int year) {
			this.typeId = typeId;
			this.week = week;
			this.year = year;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<ProductCollectWeekTop> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select * from product_collect_week_top where typeID=:typeId and week=:week and year=:year order by collectedNum desc");
			return session.createSQLQuery(sql.toString()).addEntity(ProductCollectWeekTop.class).setParameter("typeId", typeId).setParameter("week", week).setParameter("year", year).list();
		}

	}

}
