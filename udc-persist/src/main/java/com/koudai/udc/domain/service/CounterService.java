package com.koudai.udc.domain.service;

import java.util.List;

public interface CounterService {

	void increaseProductCount(String productId);

	void decreaseProductCount(String productId);

	void decreaseMultipleProductCount(List<String> productIds);

	void increaseShopCount(String shopId);

	void decreaseShopCount(String shopId);

	void decreaseMultipleShopCount(List<String> shopIds);

}
