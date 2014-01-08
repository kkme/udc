package com.koudai.udc.persistence;

import com.koudai.udc.domain.ProductCollectTaobao;

public interface ProductCollectTaobaoDAO {

	void save(ProductCollectTaobao productCollectTaobao);

	ProductCollectTaobao getProductByUserIdAndProductId(String userId, String productId);

	int deleteProductsByUserId(String userId);

}
