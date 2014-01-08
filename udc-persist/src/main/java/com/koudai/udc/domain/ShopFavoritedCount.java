package com.koudai.udc.domain;

import java.io.Serializable;

public class ShopFavoritedCount implements Serializable {

	private static final long serialVersionUID = -3428940306378927192L;

	private Long id;

	private String shopId;

	private int favoritedCount;

	public ShopFavoritedCount() {
	}

	public ShopFavoritedCount(String shopId) {
		this.shopId = shopId;
		this.favoritedCount = 1;
	}

	public void increase() {
		this.favoritedCount++;
	}

	public void decrease() {
		if (this.favoritedCount > 0) {
			this.favoritedCount--;
		}
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

	public int getFavoritedCount() {
		return favoritedCount;
	}

	public void setFavoritedCount(int favoritedCount) {
		this.favoritedCount = favoritedCount;
	}

}
