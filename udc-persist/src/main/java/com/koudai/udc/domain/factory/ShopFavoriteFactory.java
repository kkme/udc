package com.koudai.udc.domain.factory;

import java.util.Date;

import com.koudai.udc.domain.ShopFavorite;

public interface ShopFavoriteFactory {

	ShopFavorite newInstance(String shopId, Date favoriteTime, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId);

	ShopFavorite newInstance(String shopId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId);

}
