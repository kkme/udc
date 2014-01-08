package com.koudai.udc.persistence;

import com.koudai.udc.domain.ShopFavoritedCount;

public interface ShopFavoritedCountDAO {

	void save(ShopFavoritedCount shopFavoritedCount);

	ShopFavoritedCount getShopFavoritedCountByShopId(String shopId);

	int getFavoritedCountByShopId(String shopId);

}
