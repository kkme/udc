package com.koudai.udc.statis.domain;

import java.io.Serializable;
import java.util.Date;

public class ProductRecommend implements Serializable {

	private static final long serialVersionUID = -5772067959009032838L;

	private Long id;

	private String userId;

	private String productId;

	private Date pushTime;

	public ProductRecommend() {
	}

	public ProductRecommend(String userId, String productId, Date pushTime) {
		this.userId = userId;
		this.productId = productId;
		this.pushTime = pushTime;
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

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

}
