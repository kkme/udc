package com.koudai.udc.domain;

import java.io.Serializable;

public class ProductCollectWeekTop implements Serializable {

	private static final long serialVersionUID = -2875258579061234554L;

	private Long id;

	private String productId;

	private int collectedNum;

	private String typeId;

	private int week;

	private int year;

	public ProductCollectWeekTop() {
	}

	public ProductCollectWeekTop(String productId, int collectedNum, String typeId, int week, int year) {
		this.productId = productId;
		this.collectedNum = collectedNum;
		this.typeId = typeId;
		this.week = week;
		this.year = year;
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

	public int getCollectedNum() {
		return collectedNum;
	}

	public void setCollectedNum(int collectedNum) {
		this.collectedNum = collectedNum;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
