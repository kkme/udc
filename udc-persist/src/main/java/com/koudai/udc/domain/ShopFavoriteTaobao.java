package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class ShopFavoriteTaobao implements Serializable {

	private static final long serialVersionUID = -479520263219798534L;

	private Long id;

	private String userId;

	private String shopId;

	private String shopName;

	private String ownerNick;

	private Date createTime;

	public ShopFavoriteTaobao() {
	}

	public ShopFavoriteTaobao(String userId, String shopId, String shopName, String ownerNick) {
		this.userId = userId;
		this.shopId = shopId;
		this.shopName = shopName;
		this.ownerNick = ownerNick;
		this.createTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOwnerNick() {
		return ownerNick;
	}

	public void setOwnerNick(String ownerNick) {
		this.ownerNick = ownerNick;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
