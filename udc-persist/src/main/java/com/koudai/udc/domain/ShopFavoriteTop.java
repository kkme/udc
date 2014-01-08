package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class ShopFavoriteTop implements Serializable {

	private static final long serialVersionUID = 5186631049157526962L;

	private Long id;

	private String shopId;

	private int favoritedNum;

	private Date statTime;

	public ShopFavoriteTop() {
	}

	public ShopFavoriteTop(String shopId, int favoritedNum, Date statTime) {
		this.shopId = shopId;
		this.favoritedNum = favoritedNum;
		this.statTime = statTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public int getFavoritedNum() {
		return favoritedNum;
	}

	public void setFavoritedNum(int favoritedNum) {
		this.favoritedNum = favoritedNum;
	}

	public Date getStatTime() {
		return statTime;
	}

	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

}
