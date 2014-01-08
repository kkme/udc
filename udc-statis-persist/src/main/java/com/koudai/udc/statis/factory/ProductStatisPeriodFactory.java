package com.koudai.udc.statis.factory;

import com.koudai.udc.statis.domain.ProductStatisPeriod;

public interface ProductStatisPeriodFactory {

	ProductStatisPeriod newInstance(String productId, String statTime);

}
