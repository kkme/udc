package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ShopFavoriteTaobao;

public interface ShopFavoriteTaobaoFactory {

	ShopFavoriteTaobao newInstance(String userId, String shopId, String shopName, String ownerNick);

}
