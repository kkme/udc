package com.koudai.udc.domain.factory;

import java.math.BigDecimal;

import com.koudai.udc.domain.PriceRemindAnonymous;

public class SimplePriceRemindAnonymousFactory implements PriceRemindAnonymousFactory {

	@Override
	public PriceRemindAnonymous newInstance(String productId, String email, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice) {
		return new PriceRemindAnonymous(productId, email, subscribePrice, productUrl, targetPrice);
	}

}
