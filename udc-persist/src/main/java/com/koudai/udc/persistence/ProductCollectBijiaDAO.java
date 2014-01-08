package com.koudai.udc.persistence;

import java.util.List;

import com.koudai.udc.domain.BijiaProduct;
import com.koudai.udc.domain.ProductCollectBijia;

public interface ProductCollectBijiaDAO {

	void save(ProductCollectBijia productCollectBijia);

	ProductCollectBijia getProductCollectBijiaByUserAndProductId(String userId, String productId);

	List<BijiaProduct> getBijiaProductsByUserId(String userId, List<String> filterableIds);

	List<BijiaProduct> getBijiaProductsByUserIdAndStartAndEndPos(String userId, List<String> filterableIds, int start, int end);

}
