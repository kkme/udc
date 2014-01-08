package com.koudai.udc.statis.domain;

import java.io.Serializable;
import java.util.Date;

import com.koudai.udc.statis.tool.DateFormatter;

public class ProductStatisPeriod implements Serializable {

	private static final long serialVersionUID = 346056026415864611L;

	private Long id;

	private String productId;

	private int clickedNum;

	private int collectedNum;

	private int purchasedNum;

	private Date statTime;

	public ProductStatisPeriod() {
	}

	public ProductStatisPeriod(String productId, String statTime) {
		this.productId = productId;
		this.clickedNum = 0;
		this.collectedNum = 0;
		this.purchasedNum = 0;
		this.statTime = new DateFormatter(DateFormatter.COMMON_DATE_FORMAT).parse(statTime);
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

	public Date getStatTime() {
		return statTime;
	}

	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

}
