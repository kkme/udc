package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class PurchaseRecord implements Serializable {

	private static final long serialVersionUID = 7912763261240720874L;

	private Long id;
	private String userId;
	private String productId;
	private Date purchaseTime;
	private String machineId;
	private String networkId;
	private String softwareVersion;
	private String softwareName;
	private String firmWareVersion;
	private String referId;

	public PurchaseRecord() {
	}

	public PurchaseRecord(String userId, String productId, Date purchaseTime, String machineId, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		this.userId = userId;
		this.productId = productId;
		this.purchaseTime = purchaseTime;
		this.machineId = machineId;
		this.networkId = networkId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
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

	public Date getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
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

}
