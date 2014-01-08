package com.koudai.udc.persistence;

import com.koudai.udc.domain.ProductCollectedCount;

public interface ProductCollectedCountDAO {

	void save(ProductCollectedCount productCollectedCount);

	ProductCollectedCount getProductCollectedCountByProductId(String productId);

	int getCollectedCountByProductId(String productId);

}
