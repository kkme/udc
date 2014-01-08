package com.koudai.udc.domain;

import java.util.Date;

import org.apache.log4j.Logger;

public class ProductCollect extends ProductCollectDataModel {

	private static final long serialVersionUID = 8340756348682937609L;

	private static final Logger LOGGER = Logger.getLogger(ProductCollect.class);

	public ProductCollect() {
	}

	public ProductCollect(String productId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		this.productId = productId;
		this.collectTime = new Date();
		this.machineId = machineId;
		this.networkId = networkId;
		this.userId = userId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		this.status = ProductStatus.Normal.getCode();
	}

	public ProductCollect(String productId, Date collectTime, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		this.productId = productId;
		this.collectTime = collectTime;
		this.machineId = machineId;
		this.networkId = networkId;
		this.userId = userId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		this.status = ProductStatus.Normal.getCode();
	}

	public void normal(String machineId, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		if (ProductStatus.Cancel.getCode() != this.status) {
			LOGGER.error("Product < id ( " + productId + " ) , user ( " + userId + " ) , machine ( " + machineId + " ) > has already collected");
			return;
		}
		this.status = ProductStatus.Normal.getCode();
		this.machineId = machineId;
		this.collectTime = new Date();
		this.networkId = networkId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		counterService.increaseProductCount(this.productId);
	}

	public void normal(Date collectTime, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		if (ProductStatus.Cancel.getCode() != this.status) {
			LOGGER.error("Product < id ( " + productId + " ) , user ( " + userId + " ) , machine ( " + machineId + " ) > has already collected");
			return;
		}
		this.status = ProductStatus.Normal.getCode();
		this.collectTime = collectTime;
		this.networkId = networkId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		counterService.increaseProductCount(this.productId);
	}

	public void normal(String machineId, Date collectTime, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		if (ProductStatus.Cancel.getCode() != this.status) {
			LOGGER.error("Product < id ( " + productId + " ) , user ( " + userId + " ) , machine ( " + machineId + " ) > has already collected");
			return;
		}
		this.status = ProductStatus.Normal.getCode();
		this.machineId = machineId;
		this.collectTime = collectTime;
		this.networkId = networkId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		counterService.increaseProductCount(this.productId);
	}

	public void cancel() {
		if (ProductStatus.Normal.getCode() != this.status) {
			LOGGER.error("Product < id ( " + productId + " ) , user ( " + userId + " ) , machine ( " + machineId + " ) > has already canceled");
			return;
		}
		this.status = ProductStatus.Cancel.getCode();
		counterService.decreaseProductCount(this.productId);
	}

}
