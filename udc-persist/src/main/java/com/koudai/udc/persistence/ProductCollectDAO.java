package com.koudai.udc.persistence;

import java.util.Date;
import java.util.List;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.domain.ProductCollect;

public interface ProductCollectDAO {

	void save(ProductCollect productCollect);

	ProductCollect getProductCollectByUserAndProductId(String userId, String productId);

	List<String> getAllProductIdsByUserIdOrderByTime(String userId);

	List<String> getProductIdsByUserIdAndStartAndEndPosOrderByTime(String userId, int start, int end);

	int cancelProduct(String userId, String productId);

	List<String> getAllProductIdsByUserId(String userId);

	List<ProductCollect> getProductsByUserIdAndProductIdsAndStatus(String userId, int status, List<String> productIds);

	int getCollectNumByUserId(String userId);

	int getCollectNumByUserIdAndEndTime(String userId, Date endTime);

	List<IdAndDate> getProductIdAndCollectTimeByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime);

}
