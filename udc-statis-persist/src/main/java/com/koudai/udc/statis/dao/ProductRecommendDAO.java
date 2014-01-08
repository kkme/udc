package com.koudai.udc.statis.dao;

import java.util.Date;
import java.util.List;

import com.koudai.udc.statis.domain.ProductRecommend;

public interface ProductRecommendDAO {

	void save(ProductRecommend productRecommend);

	void batchSave(List<ProductRecommend> productRecommends);

	ProductRecommend getProductRecommendByProductId(String productId);

	void update(ProductRecommend productRecommend);

	void batchUpdate(List<ProductRecommend> productRecommends);

	int getCount();

	int getCountByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime);

	List<String> getProductRecommendsByStartPosAndMaxNum(int startPos, int maxNum);

	List<ProductRecommend> getProductRecommendsByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime);

	List<ProductRecommend> getLimitProductRecommendsByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime, int page, int size);

}
