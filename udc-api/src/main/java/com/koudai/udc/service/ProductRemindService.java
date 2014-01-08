package com.koudai.udc.service;

import java.math.BigDecimal;
import java.util.List;

import com.koudai.udc.domain.BijiaProduct;

public interface ProductRemindService {

	boolean collectProductAndRemindPrice(String productId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice, int noticeType, String email);

	void cancelProduct(String productId, String userId);

	void remindPrice(String productId, String userId, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice, int subscribeType, int noticeType, String email);

	void cancelRemind(String productId, String userId);

	List<BijiaProduct> getBijiaProducts(String userId, int start, int end);

}
