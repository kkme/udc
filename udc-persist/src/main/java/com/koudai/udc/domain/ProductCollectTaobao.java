package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class ProductCollectTaobao implements Serializable {

	private static final long serialVersionUID = 510531711953857677L;

	private Long id;

	private String userId;

	private String productId;

	private String productName;

	private String ownerNick;

	private Date createTime;

	public ProductCollectTaobao() {
	}

	public ProductCollectTaobao(String userId, String productId, String productName, String ownerNick) {
		this.userId = userId;
		this.productId = productId;
		this.productName = productName;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
