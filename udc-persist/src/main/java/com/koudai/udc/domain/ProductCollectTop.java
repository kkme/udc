package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class ProductCollectTop implements Serializable {

	private static final long serialVersionUID = 8112980249409704099L;

	private Long id;

	private String productId;

	private int collectedNum;

	private String typeId;

	private Date statTime;

	private int useType;

	public ProductCollectTop() {
	}

	public ProductCollectTop(String productId, int collectedNum, String typeId, Date statTime, int useType) {
		this.productId = productId;
		this.collectedNum = collectedNum;
		this.typeId = typeId;
		this.statTime = statTime;
		this.useType = useType;
	}

	public ProductCollectTop(String productId, int collectedNum, Date statTime) {
		this.productId = productId;
		this.collectedNum = collectedNum;
		this.statTime = statTime;
		this.useType = ProductTopStatus.all_top_thirty.getCode();
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

	public Date getStatTime() {
		return statTime;
	}

	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

	public int getUseType() {
		return useType;
	}

	public void setUseType(int useType) {
		this.useType = useType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductCollectTop other = (ProductCollectTop) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

}
