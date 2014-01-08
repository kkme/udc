package com.koudai.udc.statis.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.koudai.udc.statis.dao.ProductTypeDAO;
import com.koudai.udc.statis.domain.ProductType;

public class ProductTypeDAOImpl extends SqlMapClientDaoSupport implements ProductTypeDAO {

	@Override
	public void save(ProductType productType) {
		getSqlMapClientTemplate().insert("ProductType.save", productType);
	}

	@Override
	public void batchSave(List<ProductType> productTypes) {
		if (productTypes == null || productTypes.isEmpty()) {
			return;
		}
		getSqlMapClientTemplate().execute(new BatchSaveSqlMapClientCallback(productTypes));
	}

	private static class BatchSaveSqlMapClientCallback implements SqlMapClientCallback<Object> {

		private List<ProductType> productTypes;

		public BatchSaveSqlMapClientCallback(List<ProductType> productTypes) {
			this.productTypes = productTypes;
		}

		@Override
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
			executor.startBatch();
			for (ProductType productType : productTypes) {
				executor.insert("ProductType.save", productType);
			}
			executor.executeBatch();
			return null;
		}

	}

	@Override
	public void batchDelete(List<ProductType> productTypes) {
		if (productTypes == null || productTypes.isEmpty()) {
			return;
		}
		getSqlMapClientTemplate().execute(new BatchDeleteSqlMapClientCallback(productTypes));
	}

	private static class BatchDeleteSqlMapClientCallback implements SqlMapClientCallback<Object> {

		private List<ProductType> productTypes;

		public BatchDeleteSqlMapClientCallback(List<ProductType> productTypes) {
			this.productTypes = productTypes;
		}

		@Override
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
			executor.startBatch();
			for (ProductType productType : productTypes) {
				executor.delete("ProductType.delete", productType);
			}
			executor.executeBatch();
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTypeIdsByProductId(String productId) {
		return (List<String>) getSqlMapClientTemplate().queryForList("ProductType.getTypeIdsByProductId", productId);
	}

}
