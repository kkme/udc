package com.koudai.udc.statis.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.koudai.udc.statis.dao.ProductStatisDAO;
import com.koudai.udc.statis.domain.ProductStatis;

public class ProductStatisDAOImpl extends SqlMapClientDaoSupport implements ProductStatisDAO {

	@Override
	public void save(ProductStatis productStatis) {
		getSqlMapClientTemplate().insert("ProductStatis.save", productStatis);
	}

	@Override
	public void batchSave(List<ProductStatis> productStatisList) {
		if (productStatisList == null || productStatisList.isEmpty()) {
			return;
		}
		getSqlMapClientTemplate().execute(new BatchSaveSqlMapClientCallback(productStatisList));
	}

	private static class BatchSaveSqlMapClientCallback implements SqlMapClientCallback<Object> {

		private List<ProductStatis> productStatisList;

		public BatchSaveSqlMapClientCallback(List<ProductStatis> productStatisList) {
			this.productStatisList = productStatisList;
		}

		@Override
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
			executor.startBatch();
			for (ProductStatis productStatis : productStatisList) {
				executor.insert("ProductStatis.save", productStatis);
			}
			executor.executeBatch();
			return null;
		}

	}

	@Override
	public ProductStatis getProductStatisByProductId(String productId) {
		return (ProductStatis) getSqlMapClientTemplate().queryForObject("ProductStatis.getProductStatisByProductId", productId);
	}

	@Override
	public void update(ProductStatis productStatis) {
		getSqlMapClientTemplate().update("ProductStatis.updateStatisNumByProductId", productStatis);
	}

	@Override
	public void batchUpdate(List<ProductStatis> productStatisList) {
		if (productStatisList == null || productStatisList.isEmpty()) {
			return;
		}
		getSqlMapClientTemplate().execute(new BatchUpdateSqlMapClientCallback(productStatisList));
	}

	private static class BatchUpdateSqlMapClientCallback implements SqlMapClientCallback<Object> {

		private List<ProductStatis> productStatisList;

		public BatchUpdateSqlMapClientCallback(List<ProductStatis> productStatisList) {
			this.productStatisList = productStatisList;
		}

		@Override
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
			executor.startBatch();
			for (ProductStatis productStatis : productStatisList) {
				executor.update("ProductStatis.updateStatisNumByProductId", productStatis);
			}
			executor.executeBatch();
			return null;
		}
	}

}
