package com.koudai.udc.persistence;

import com.koudai.udc.domain.ProductPush;

public interface ProductPushDAO {

	void save(ProductPush productPush);

	ProductPush getProductPushByMachineAndProductId(String machineId, String productId);

}
