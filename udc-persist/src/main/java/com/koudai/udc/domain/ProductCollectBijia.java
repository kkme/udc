package com.koudai.udc.domain;

import java.util.Date;

import org.apache.log4j.Logger;

public class ProductCollectBijia extends ProductCollectBijiaDataModel {

	private static final long serialVersionUID = -4918420173333959797L;

	private static final Logger LOGGER = Logger.getLogger(ProductCollectBijia.class);

	public ProductCollectBijia() {
	}

	public ProductCollectBijia(String productId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId, int priceReduction) {
		this.productId = productId;
		this.collectTime = new Date();
		this.machineId = machineId;
		this.networkId = networkId;
		this.userId = userId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		this.priceReduction = priceReduction;
		this.stockNotice = ConfirmStatus.NO.getCode();
		this.status = ProductStatus.Normal.getCode();
	}

	public boolean normal(String machineId, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		if (ProductStatus.Cancel.getCode() != this.status) {
			LOGGER.error("Bijia product < id ( " + productId + " ) , user ( " + userId + " ) > has already collected");
			return true;
		}
		this.collectTime = new Date();
		this.machineId = machineId;
		this.networkId = networkId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		this.priceReduction = ConfirmStatus.NO.getCode();
		this.stockNotice = ConfirmStatus.NO.getCode();
		this.status = ProductStatus.Normal.getCode();
		return false;
	}

	public boolean normalWithPriceReduction(String machineId, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		this.priceReduction = ConfirmStatus.YES.getCode();
		if (ProductStatus.Cancel.getCode() != this.status) {
			LOGGER.error("Bijia product < id ( " + productId + " ) , user ( " + userId + " ) > has already collected");
			return true;
		}
		this.collectTime = new Date();
		this.machineId = machineId;
		this.networkId = networkId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		this.stockNotice = ConfirmStatus.NO.getCode();
		this.status = ProductStatus.Normal.getCode();
		return false;
	}

	public void cancel() {
		if (ProductStatus.Normal.getCode() != this.status) {
			LOGGER.error("Bijia product < id ( " + productId + " ) , user ( " + userId + " ) > has already canceled");
			return;
		}
		this.priceReduction = ConfirmStatus.NO.getCode();
		this.status = ProductStatus.Cancel.getCode();
	}

	public boolean remindPrice() {
		if (ProductStatus.Normal.getCode() != this.status) {
			LOGGER.error("Bijia product < id ( " + productId + " ) , user ( " + userId + " ) > has already canceled , so can not remind reduction price");
			return true;
		}
		if (ConfirmStatus.NO.getCode() == this.priceReduction) {
			this.priceReduction = ConfirmStatus.YES.getCode();
		}
		return false;
	}

	public void cancelRemind() {
		if (ProductStatus.Normal.getCode() != this.status) {
			LOGGER.error("Bijia product < id ( " + productId + " ) , user ( " + userId + " ) > has already canceled , so can not cancel price remind");
			return;
		}
		if (ConfirmStatus.YES.getCode() != this.priceReduction) {
			LOGGER.error("Bijia product < id ( " + productId + " ) , user ( " + userId + " ) > with price remind has already canceled");
			return;
		}
		this.priceReduction = ConfirmStatus.NO.getCode();
	}

}
