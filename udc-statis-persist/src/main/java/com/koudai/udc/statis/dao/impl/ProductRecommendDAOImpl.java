package com.koudai.udc.statis.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.koudai.udc.statis.dao.ProductRecommendDAO;
import com.koudai.udc.statis.domain.ProductRecommend;
import com.koudai.udc.statis.tool.DateFormatter;

public class ProductRecommendDAOImpl extends SqlMapClientDaoSupport implements ProductRecommendDAO {

	@Override
	public void save(ProductRecommend productRecommend) {
		getSqlMapClientTemplate().insert("ProductRecommend.save", productRecommend);
	}

	@Override
	public void batchSave(List<ProductRecommend> productRecommends) {
		if (productRecommends == null || productRecommends.isEmpty()) {
			return;
		}
		getSqlMapClientTemplate().execute(new BatchSaveSqlMapClientCallback(productRecommends));
	}

	private static class BatchSaveSqlMapClientCallback implements SqlMapClientCallback<Object> {

		private List<ProductRecommend> productRecommends;

		public BatchSaveSqlMapClientCallback(List<ProductRecommend> productRecommends) {
			this.productRecommends = productRecommends;
		}

		@Override
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
			executor.startBatch();
			for (ProductRecommend productRecommend : productRecommends) {
				executor.insert("ProductRecommend.save", productRecommend);
			}
			executor.executeBatch();
			return null;
		}

	}

	@Override
	public ProductRecommend getProductRecommendByProductId(String productId) {
		return (ProductRecommend) getSqlMapClientTemplate().queryForObject("ProductRecommend.getProductRecommendByProductId", productId);
	}

	@Override
	public void update(ProductRecommend productRecommend) {
		getSqlMapClientTemplate().update("ProductRecommend.updatePushTimeByProductId", productRecommend);
	}

	@Override
	public void batchUpdate(List<ProductRecommend> productRecommends) {
		if (productRecommends == null || productRecommends.isEmpty()) {
			return;
		}
		getSqlMapClientTemplate().execute(new BatchUpdateSqlMapClientCallback(productRecommends));
	}

	private static class BatchUpdateSqlMapClientCallback implements SqlMapClientCallback<Object> {

		private List<ProductRecommend> productRecommends;

		public BatchUpdateSqlMapClientCallback(List<ProductRecommend> productRecommends) {
			this.productRecommends = productRecommends;
		}

		@Override
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
			executor.startBatch();
			for (ProductRecommend productRecommend : productRecommends) {
				executor.update("ProductRecommend.updatePushTimeByProductId", productRecommend);
			}
			executor.executeBatch();
			return null;
		}

	}

	@Override
	public int getCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject("ProductRecommend.getCount");
	}

	@Override
	public int getCountByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime) {
		final String formatterStartTime = new DateFormatter().format(startTime);
		final String formatterEndTime = new DateFormatter().format(endTime);
		return (Integer) getSqlMapClientTemplate().queryForObject("ProductRecommend.getCountByUserIdAndStartAndEndTime", new TotalLimit(userId, formatterStartTime, formatterEndTime));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProductRecommendsByStartPosAndMaxNum(int startPos, int maxNum) {
		return (List<String>) getSqlMapClientTemplate().queryForList("ProductRecommend.getProductRecommendsByStartPosAndMaxNum", new PositionLimit(startPos, maxNum));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductRecommend> getProductRecommendsByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime) {
		final String formatterStartTime = new DateFormatter().format(startTime);
		final String formatterEndTime = new DateFormatter().format(endTime);
		return (List<ProductRecommend>) getSqlMapClientTemplate().queryForList("ProductRecommend.getProductRecommendsByUserIdAndStartAndEndTime", new TotalLimit(userId, formatterStartTime, formatterEndTime));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductRecommend> getLimitProductRecommendsByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime, int page, int size) {
		final String formatterStartTime = new DateFormatter().format(startTime);
		final String formatterEndTime = new DateFormatter().format(endTime);
		return (List<ProductRecommend>) getSqlMapClientTemplate().queryForList("ProductRecommend.getLimitProductRecommendsByUserIdAndStartAndEndTime", new PageLimit(userId, formatterStartTime, formatterEndTime, page * size, size));
	}

}
