package com.koudai.udc.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public abstract class PriceRemindDataModel implements Serializable {

	private static final long serialVersionUID = 3343176287267228713L;

	protected Long id;
	protected String productId;
	protected String userId;
	protected Date subscribeTime;
	protected BigDecimal subscribePrice;
	protected String productUrl;
	protected BigDecimal currentPrice;
	protected BigDecimal targetPrice;
	protected int subscribeType;
	protected int noticeType;
	protected Date lastNoticeTime;
	protected String email;
	protected int noticeNumber;
	protected int noticeStatus;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public int getSubscribeType() {
		return subscribeType;
	}

	public void setSubscribeType(int subscribeType) {
		this.subscribeType = subscribeType;
	}

	public int getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}

	public Date getLastNoticeTime() {
		return lastNoticeTime;
	}

	public void setLastNoticeTime(Date lastNoticeTime) {
		this.lastNoticeTime = lastNoticeTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNoticeNumber() {
		return noticeNumber;
	}

	public void setNoticeNumber(int noticeNumber) {
		this.noticeNumber = noticeNumber;
	}

	public int getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(int noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public int getCanRemind() {
		return canRemind;
	}

	public void setCanRemind(int canRemind) {
		this.canRemind = canRemind;
	}

}
