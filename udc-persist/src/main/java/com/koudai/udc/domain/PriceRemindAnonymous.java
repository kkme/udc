package com.koudai.udc.domain;

import java.math.BigDecimal;
import java.util.Date;

public class PriceRemindAnonymous extends PriceRemindAnonymousDataModel {

	private static final long serialVersionUID = -6888305637716799530L;

	public PriceRemindAnonymous() {
	}

	public PriceRemindAnonymous(String productId, String email, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice) {
		this.productId = productId;
		this.email = email;
		this.subscribeTime = new Date();
		this.subscribePrice = subscribePrice;
		this.productUrl = productUrl;
		this.currentPrice = subscribePrice;
		this.targetPrice = targetPrice;
		this.noticeNumber = 0;
		this.canRemind = ConfirmStatus.NO.getCode();
	}

	public void update(BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice) {
		this.subscribeTime = new Date();
		this.subscribePrice = subscribePrice;
		this.productUrl = productUrl;
		this.currentPrice = subscribePrice;
		this.targetPrice = targetPrice;
		this.noticeNumber = 0;
		this.canRemind = ConfirmStatus.NO.getCode();
	}

}
