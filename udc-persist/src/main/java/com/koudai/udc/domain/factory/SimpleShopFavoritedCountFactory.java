package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ShopFavoritedCount;

public class SimpleShopFavoritedCountFactory implements ShopFavoritedCountFactory {

	@Override
	public ShopFavoritedCount newInstance(String shopId) {
		return new ShopFavoritedCount(shopId);
	}

}
