package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductCollectTaobao;

public interface ProductCollectTaobaoFactory {

	ProductCollectTaobao newInstance(String userId, String productId, String productName, String ownerNick);

}
