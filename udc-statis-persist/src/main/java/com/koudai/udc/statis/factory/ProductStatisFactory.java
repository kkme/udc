package com.koudai.udc.statis.factory;

import com.koudai.udc.statis.domain.ProductStatis;

public interface ProductStatisFactory {

	ProductStatis newInstance(String productId);

}
