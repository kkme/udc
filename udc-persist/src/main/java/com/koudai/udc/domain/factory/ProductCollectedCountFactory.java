package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductCollectedCount;

public interface ProductCollectedCountFactory {

	ProductCollectedCount newInstance(String productId);

}
