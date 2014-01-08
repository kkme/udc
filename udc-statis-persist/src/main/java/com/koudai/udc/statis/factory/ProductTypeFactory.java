package com.koudai.udc.statis.factory;

import com.koudai.udc.statis.domain.ProductType;

public interface ProductTypeFactory {

	ProductType newInstance(String productId, String typeId);

}
