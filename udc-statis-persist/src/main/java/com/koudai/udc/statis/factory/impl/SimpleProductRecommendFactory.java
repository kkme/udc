package com.koudai.udc.statis.factory.impl;

import java.util.Date;

import com.koudai.udc.statis.domain.ProductRecommend;
import com.koudai.udc.statis.factory.ProductRecommendFactory;

public class SimpleProductRecommendFactory implements ProductRecommendFactory {

	@Override
	public ProductRecommend newInstance(String userId, String productId, Date pushTime) {
		return new ProductRecommend(userId, productId, pushTime);
	}

}
