package com.koudai.udc.persistence;

import java.util.List;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.domain.ShopFavorite;

public interface ShopFavoriteDAO {

	void save(ShopFavorite shopFavorite);

	ShopFavorite getShopFavoriteByUserAndShopId(String userId, String shopId);

	List<String> getAllShopIdsByUserIdOrderByTime(String userId);

	List<String> getShopIdsByUserIdAndStartAndEndPosOrderByTime(String userId, int start, int end);

	int cancelShop(String userId, String shopId);

	List<String> getAllShopIdsByUserId(String userId);

	List<ShopFavorite> getShopsByUserIdAndShopIdsAndStatus(String userId, int status, List<String> shopIds);

	int getFavoriteNumByUserId(String userId);

	List<IdAndDate> getAllShopIdsAndFavoriteTimeByUserId(String userId);

}
