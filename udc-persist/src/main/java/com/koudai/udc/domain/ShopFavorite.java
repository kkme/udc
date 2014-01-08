package com.koudai.udc.domain;

import java.util.Date;

import org.apache.log4j.Logger;

public class ShopFavorite extends ShopFavoriteDataModel {

	private static final long serialVersionUID = 226570832442949810L;

	private static final Logger LOGGER = Logger.getLogger(ShopFavorite.class);

	public ShopFavorite() {
	}

	public ShopFavorite(String shopId, Date favoriteTime, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		this.shopId = shopId;
		this.favoriteTime = favoriteTime;
		this.machineId = machineId;
		this.networkId = networkId;
		this.userId = userId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		this.status = ShopStatus.Normal.getCode();
	}

	public ShopFavorite(String shopId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		this.shopId = shopId;
		this.favoriteTime = new Date();
		this.machineId = machineId;
		this.networkId = networkId;
		this.userId = userId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		this.status = ShopStatus.Normal.getCode();
	}

	public void normal(String machineId, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		if (ShopStatus.Cancel.getCode() != this.status) {
			LOGGER.error("Shop < id ( " + shopId + " ) , user ( " + userId + " ) , machine ( " + machineId + " ) > has already favorited");
			return;
		}
		this.status = ShopStatus.Normal.getCode();
		this.machineId = machineId;
		this.favoriteTime = new Date();
		this.networkId = networkId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		counterService.increaseShopCount(this.shopId);
	}

	public void normal(Date favoriteTime, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		if (ShopStatus.Cancel.getCode() != this.status) {
			LOGGER.error("Shop < id ( " + shopId + " ) , user ( " + userId + " ) , machine ( " + machineId + " ) > has already favorited");
			return;
		}
		this.status = ShopStatus.Normal.getCode();
		this.favoriteTime = favoriteTime;
		this.networkId = networkId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		counterService.increaseShopCount(this.shopId);
	}

	public void normal(String machineId, Date favoriteTime, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		if (ShopStatus.Cancel.getCode() != this.status) {
			LOGGER.error("Shop < id ( " + shopId + " ) , user ( " + userId + " ) , machine ( " + machineId + " ) > has already favorited");
			return;
		}
		this.status = ShopStatus.Normal.getCode();
		this.machineId = machineId;
		this.favoriteTime = favoriteTime;
		this.networkId = networkId;
		this.softwareVersion = softwareVersion;
		this.softwareName = softwareName;
		this.firmWareVersion = firmWareVersion;
		this.referId = referId;
		counterService.increaseShopCount(this.shopId);
	}

	public void cancel() {
		if (ShopStatus.Normal.getCode() != this.status) {
			LOGGER.error("Shop < id ( " + shopId + " ) , user ( " + userId + " ) , machine ( " + machineId + " ) > has already canceled");
			return;
		}
		this.status = ShopStatus.Cancel.getCode();
		counterService.decreaseShopCount(this.shopId);
	}

}
