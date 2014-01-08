package com.koudai.udc.persistence;

import com.koudai.udc.domain.ShopFavoriteTaobao;

public interface ShopFavoriteTaobaoDAO {

	void save(ShopFavoriteTaobao shopFavoriteTaobao);

	ShopFavoriteTaobao getShopByUserIdAndShopId(String userId, String shopId);

	int deleteShopsByUserId(String userId);

}
