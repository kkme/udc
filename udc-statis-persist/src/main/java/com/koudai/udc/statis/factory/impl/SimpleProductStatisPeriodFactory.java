package com.koudai.udc.statis.factory.impl;

import com.koudai.udc.statis.domain.ProductStatisPeriod;
import com.koudai.udc.statis.factory.ProductStatisPeriodFactory;

public class SimpleProductStatisPeriodFactory implements ProductStatisPeriodFactory {

	@Override
	public ProductStatisPeriod newInstance(String productId, String statTime) {
		return new ProductStatisPeriod(productId, statTime);
	}

}
