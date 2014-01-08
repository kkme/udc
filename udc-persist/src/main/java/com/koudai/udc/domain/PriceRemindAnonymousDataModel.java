package com.koudai.udc.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public abstract class PriceRemindAnonymousDataModel implements Serializable {

	private static final long serialVersionUID = -6995198845788035744L;

	protected Long id;
	protected String productId;
	protected String email;
	protected Date subscribeTime;
	protected BigDecimal subscribePrice;
	protected String productUrl;
	protected BigDecimal currentPrice;
	protected BigDecimal targetPrice;
	protected Date lastNoticeTime;
	protected int noticeNumber;
	protected int canRemind;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public BigDecimal getSubscribePrice() {
		return subscribePrice;
	}

	public void setSubscribePrice(BigDecimal subscribePrice) {
		this.subscribePrice = subscribePrice;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(BigDecimal targetPrice) {
		this.targetPrice = targetPrice;
	}

	public Date getLastNoticeTime() {
		return lastNoticeTime;
	}

	public void setLastNoticeTime(Date lastNoticeTime) {
		this.lastNoticeTime = lastNoticeTime;
	}

	public int getNoticeNumber() {
		return noticeNumber;
	}

	public void setNoticeNumber(int noticeNumber) {
		this.noticeNumber = noticeNumber;
	}

	public int getCanRemind() {
		return canRemind;
	}

	public void setCanRemind(int canRemind) {
		this.canRemind = canRemind;
	}

}
