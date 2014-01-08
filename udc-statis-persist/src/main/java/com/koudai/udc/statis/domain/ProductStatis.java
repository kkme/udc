package com.koudai.udc.statis.domain;

import java.io.Serializable;

public class ProductStatis implements Serializable {

	private static final long serialVersionUID = -2958206192861956236L;

	private Long id;

	private String productId;

	private int clickedNum;

	private int collectedNum;

	private int purchasedNum;

	public ProductStatis() {
	}

	public ProductStatis(String productId) {
		this.productId = productId;
		this.clickedNum = 0;
		this.collectedNum = 0;
		this.purchasedNum = 0;
	}

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

	public int getClickedNum() {
		return clickedNum;
	}

	public void setClickedNum(int clickedNum) {
		this.clickedNum = clickedNum;
	}

	public int getCollectedNum() {
		return collectedNum;
	}

	public void setCollectedNum(int collectedNum) {
		this.collectedNum = collectedNum;
	}

	public int getPurchasedNum() {
		return purchasedNum;
	}

	public void setPurchasedNum(int purchasedNum) {
		this.purchasedNum = purchasedNum;
	}

}
