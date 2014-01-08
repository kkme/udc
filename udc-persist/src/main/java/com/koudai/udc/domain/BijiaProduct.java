package com.koudai.udc.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class BijiaProduct implements Serializable {

	private static final long serialVersionUID = 8077214813504691295L;

	private String productId;

	private int priceReduction = ConfirmStatus.NO.getCode();

	private int canRemind = ConfirmStatus.NO.getCode();

	private BigDecimal balance = BigDecimal.ZERO;

	public BijiaProduct() {
	}

	public String getFormatterPriceReduction() {
		return String.valueOf(priceReduction);
	}

	public String getFormatterCanRemind() {
		return String.valueOf(canRemind);
	}

	public String getFormatterBalance() {
		if (balance == null) {
			return String.valueOf(BigDecimal.ZERO);
		}
		return String.valueOf(balance);
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getPriceReduction() {
		return priceReduction;
	}

	public void setPriceReduction(int priceReduction) {
		this.priceReduction = priceReduction;
	}

	public int getCanRemind() {
		return canRemind;
	}

	public void setCanRemind(int canRemind) {
		this.canRemind = canRemind;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
