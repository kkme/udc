package com.koudai.udc.domain.factory;

import java.math.BigDecimal;

import com.koudai.udc.domain.PriceRemind;

public class SimplePriceRemindFactory implements PriceRemindFactory {

	@Override
	public PriceRemind newInstnce(String productId, String userId, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice, int subscribeType, int noticeType, String email) {
		return new PriceRemind(productId, userId, subscribePrice, productUrl, targetPrice, subscribeType, noticeType, email);
	}

}
