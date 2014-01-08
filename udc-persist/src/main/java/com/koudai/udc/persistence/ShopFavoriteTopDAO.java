package com.koudai.udc.persistence;

import java.util.Date;
import java.util.List;

import com.koudai.udc.domain.ShopFavoriteTop;

public interface ShopFavoriteTopDAO {

	void save(ShopFavoriteTop shopFavoriteTop);

	List<ShopFavoriteTop> getTopsByStartAndEndTime(Date startTime, Date endTime);

}
