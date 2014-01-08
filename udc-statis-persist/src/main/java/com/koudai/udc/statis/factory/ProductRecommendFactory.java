package com.koudai.udc.statis.factory;

import java.util.Date;

import com.koudai.udc.statis.domain.ProductRecommend;

public interface ProductRecommendFactory {

	ProductRecommend newInstance(String userId, String productId, Date pushTime);

}
