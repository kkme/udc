package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.ShopFavoriteTop;

public class ShopFavoriteTopDAOHibernate extends HibernateDaoSupport implements ShopFavoriteTopDAO {

	@Override
	public void save(ShopFavoriteTop shopFavoriteTop) {
		getHibernateTemplate().save(shopFavoriteTop);
	}

	@Override
	public List<ShopFavoriteTop> getTopsByStartAndEndTime(Date startTime, Date endTime) {
		return getHibernateTemplate().execute(new GetTopsByStartAndEndTimeHibernateCallback(startTime, endTime));
	}

	public static class GetTopsByStartAndEndTimeHibernateCallback implements HibernateCallback<List<ShopFavoriteTop>> {

		private Date startTime;

		private Date endTime;

		public GetTopsByStartAndEndTimeHibernateCallback(Date startTime, Date endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<ShopFavoriteTop> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select * from shop_favorite_top where statTime>=:startTime and statTime<:endTime");
			return session.createSQLQuery(sql.toString()).addEntity(ShopFavoriteTop.class).setDate("startTime", startTime).setDate("endTime", endTime).list();
		}

	}

}
