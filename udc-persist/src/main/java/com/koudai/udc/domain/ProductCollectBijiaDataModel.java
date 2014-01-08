package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class ProductCollectBijiaDataModel implements Serializable {

	private static final long serialVersionUID = 6404971112726907030L;

	protected Long id;
	protected String productId;
	protected Date collectTime;
	protected String machineId;
	protected String networkId;
	protected String userId;
	protected String softwareVersion;
	protected String softwareName;
	protected String firmWareVersion;
	protected String referId;
	protected int priceReduction;
	protected int stockNotice;
	protected int status;

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

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getFirmWareVersion() {
		return firmWareVersion;
	}

	public void setFirmWareVersion(String firmWareVersion) {
		this.firmWareVersion = firmWareVersion;
	}

	public String getReferId() {
		return referId;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public int getPriceReduction() {
		return priceReduction;
	}

	public void setPriceReduction(int priceReduction) {
		this.priceReduction = priceReduction;
	}

	public int getStockNotice() {
		return stockNotice;
	}

	public void setStockNotice(int stockNotice) {
		this.stockNotice = stockNotice;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
