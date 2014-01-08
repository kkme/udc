package com.koudai.udc.domain.factory;

import java.math.BigDecimal;

import com.koudai.udc.domain.PriceRemind;

public interface PriceRemindFactory {

	PriceRemind newInstnce(String productId, String userId, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice, int subscribeType, int noticeType, String email);

}
