package com.koudai.udc.domain.factory;

import java.util.Date;

import com.koudai.udc.domain.ShopFavorite;

public class SimpleShopFavoriteFactory implements ShopFavoriteFactory {

	@Override
	public ShopFavorite newInstance(String shopId, Date favoriteTime, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		return new ShopFavorite(shopId, favoriteTime, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId);
	}

	@Override
	public ShopFavorite newInstance(String shopId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		return new ShopFavorite(shopId, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId);
	}

}
