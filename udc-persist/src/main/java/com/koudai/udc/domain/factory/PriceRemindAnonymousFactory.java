package com.koudai.udc.domain.factory;

import java.math.BigDecimal;

import com.koudai.udc.domain.PriceRemindAnonymous;

public interface PriceRemindAnonymousFactory {

	PriceRemindAnonymous newInstance(String productId, String email, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice);

}
