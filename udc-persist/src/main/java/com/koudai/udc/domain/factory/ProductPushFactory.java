package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductPush;

public interface ProductPushFactory {

	ProductPush newInstance(String machineId, String productId);

}
