package com.koudai.udc.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.domain.PurchaseRecord;

public class PurchaseRecordDAOHibernate extends HibernateDaoSupport implements PurchaseRecordDAO {

	@Override
	public void save(PurchaseRecord purchaseRecord) {
		getHibernateTemplate().save(purchaseRecord);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PurchaseRecord getPurchaseRecordByUserIdAndProductId(String userId, String productId) {
		List<PurchaseRecord> purchaseRecords = getHibernateTemplate().find("from PurchaseRecord pr where pr.userId = ? and pr.productId = ?", new Object[] { userId, productId });
		if (purchaseRecords.size() != 0) {
			return (PurchaseRecord) purchaseRecords.get(0);
		}
		return null;
	}

	@Override
	public List<IdAndDate> getProductIdAndPurchaseTimeByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime) {
		return getHibernateTemplate().execute(new GetProductIdAndPurchaseTimeByUserIdAndStartAndEndTimeHibernateCallback(userId, startTime, endTime));
	}

	private static class GetProductIdAndPurchaseTimeByUserIdAndStartAndEndTimeHibernateCallback implements HibernateCallback<List<IdAndDate>> {

		private String userId;

		private Date startTime;

		private Date endTime;

		public GetProductIdAndPurchaseTimeByUserIdAndStartAndEndTimeHibernateCallback(String userId, Date startTime, Date endTime) {
			this.userId = userId;
			this.startTime = startTime;
			this.endTime = endTime;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<IdAndDate> doInHibernate(Session session) throws HibernateException, SQLException {
			StringBuffer sql = new StringBuffer("select productID as id,purchaseTime as date from purchase_record where userID=:userId and purchaseTime>=:startTime and purchaseTime<=:endTime order by purchaseTime desc,id desc");
			return session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(IdAndDate.class)).setString("userId", userId).setDate("startTime", startTime).setDate("endTime", endTime).list();
		}

	}

}
