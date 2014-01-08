package com.koudai.udc.persistence;

import java.util.List;

import com.koudai.udc.domain.BijiaProduct;
import com.koudai.udc.domain.PriceRemind;

public interface PriceRemindDAO {

	void save(PriceRemind priceRemind);

	PriceRemind getPriceRemindByUserAndProductId(String userId, String productId);

	List<BijiaProduct> getBijiaProductsByUserId(String userId);

	List<BijiaProduct> getBijiaProductsByUserIdAndStartAndEndPos(String userId, int start, int end);

	List<String> getProductIdsByUserId(String userId);

}
