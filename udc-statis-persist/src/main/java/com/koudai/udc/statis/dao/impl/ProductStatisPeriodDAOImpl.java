package com.koudai.udc.statis.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.koudai.udc.statis.dao.ProductStatisPeriodDAO;
import com.koudai.udc.statis.domain.ProductStatisPeriod;
import com.koudai.udc.statis.tool.DateFormatter;

public class ProductStatisPeriodDAOImpl extends SqlMapClientDaoSupport implements ProductStatisPeriodDAO {

	@Override
	public void save(ProductStatisPeriod productStatisPeriod) {
		getSqlMapClientTemplate().insert("ProductStatisPeriod.save", productStatisPeriod);
	}

	@Override
	public void batchSave(List<ProductStatisPeriod> productStatisPeriods) {
		if (productStatisPeriods == null || productStatisPeriods.isEmpty()) {
			return;
		}
		getSqlMapClientTemplate().execute(new BatchSaveSqlMapClientCallback(productStatisPeriods));
	}

	private static class BatchSaveSqlMapClientCallback implements SqlMapClientCallback<Object> {

		private List<ProductStatisPeriod> productStatisPeriods;

		public BatchSaveSqlMapClientCallback(List<ProductStatisPeriod> productStatisPeriods) {
			this.productStatisPeriods = productStatisPeriods;
		}

		@Override
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
			executor.startBatch();
			for (ProductStatisPeriod productStatisPeriod : productStatisPeriods) {
				executor.insert("ProductStatisPeriod.save", productStatisPeriod);
			}
			executor.executeBatch();
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductStatisPeriod> getProductStatisPeriodsByProductIdAndStartAndEndTime(String productId, Date startTime, Date endTime) {
		final String formatterStartTime = new DateFormatter().format(startTime);
		final String formatterEndTime = new DateFormatter().format(endTime);
		return (List<ProductStatisPeriod>) getSqlMapClientTemplate().queryForList("ProductStatisPeriod.getProductStatisPeriodsByProductIdAndStartAndEndTime", new ProductLimit(productId, formatterStartTime, formatterEndTime));
	}

}
