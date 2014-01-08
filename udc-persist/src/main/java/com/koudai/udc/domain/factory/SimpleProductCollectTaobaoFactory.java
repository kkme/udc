package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductCollectTaobao;

public class SimpleProductCollectTaobaoFactory implements ProductCollectTaobaoFactory {

	@Override
	public ProductCollectTaobao newInstance(String userId, String productId, String productName, String ownerNick) {
		return new ProductCollectTaobao(userId, productId, productName, ownerNick);
	}

}
