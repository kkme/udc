package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ShopFavoriteTaobao;

public class SimpleShopFavoriteTaobaoFactory implements ShopFavoriteTaobaoFactory {

	@Override
	public ShopFavoriteTaobao newInstance(String userId, String shopId, String shopName, String ownerNick) {
		return new ShopFavoriteTaobao(userId, shopId, shopName, ownerNick);
	}

}
