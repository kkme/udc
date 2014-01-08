package com.koudai.udc.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

public class PriceRemind extends PriceRemindDataModel {

	private static final long serialVersionUID = 9171447396057506769L;

	private static final Logger LOGGER = Logger.getLogger(PriceRemind.class);

	public PriceRemind() {
	}

	public PriceRemind(String productId, String userId, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice, int subscribeType, int noticeType, String email) {
		this.productId = productId;
		this.userId = userId;
		this.subscribeTime = new Date();
		this.subscribePrice = subscribePrice;
		this.productUrl = productUrl;
		this.currentPrice = subscribePrice;
		this.targetPrice = targetPrice;
		this.subscribeType = subscribeType;
		this.noticeType = noticeType;
		this.email = email;
		this.noticeNumber = 0;
		this.noticeStatus = ConfirmStatus.YES.getCode();
		this.canRemind = ConfirmStatus.NO.getCode();
	}

	public void update(BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice, int noticeType, String email) {
		this.subscribeTime = new Date();
		this.subscribePrice = subscribePrice;
		this.productUrl = productUrl;
		this.currentPrice = subscribePrice;
		this.targetPrice = targetPrice;
		this.subscribeType = SubscribeType.Curve.getCode();
		this.noticeType = noticeType;
		this.email = email;
		this.noticeNumber = 0;
		this.noticeStatus = ConfirmStatus.YES.getCode();
		this.canRemind = ConfirmStatus.NO.getCode();
	}

	public void cancel() {
		if (ConfirmStatus.YES.getCode() != this.noticeStatus) {
			LOGGER.error("Price remind < id ( " + productId + " ) , user ( " + userId + " ) > has already canceled");
			return;
		}
		this.noticeStatus = ConfirmStatus.NO.getCode();
	}

}
