package com.koudai.udc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ProductCollect;
import com.koudai.udc.domain.ProductStatus;
import com.koudai.udc.domain.ShopFavorite;
import com.koudai.udc.domain.ShopStatus;
import com.koudai.udc.domain.factory.ProductCollectFactory;
import com.koudai.udc.domain.factory.ShopFavoriteFactory;
import com.koudai.udc.domain.service.CounterService;
import com.koudai.udc.persistence.ProductCollectDAO;
import com.koudai.udc.persistence.ShopFavoriteDAO;
import com.koudai.udc.service.UserDataService;
import com.koudai.udc.utils.DateFormatter;
import com.koudai.udc.utils.S;

public class UserDataServiceImpl implements UserDataService {

	private static final Logger LOGGER = Logger.getLogger(UserDataServiceImpl.class);

	private CounterService counterService;

	private ProductCollectDAO productCollectDAOW;

	private ProductCollectDAO productCollectDAOR;

	private ShopFavoriteDAO shopFavoriteDAOW;

	private ShopFavoriteDAO shopFavoriteDAOR;

	private ProductCollectFactory productCollectFactory;

	private ShopFavoriteFactory shopFavoriteFactory;

	@Override
	public void synchronize(String mainId, String subId, String machineId) {
		LOGGER.info("Sychronize user data with main id < " + mainId + " > sub id < " + subId + " > machine id < " + machineId + " >");
		List<String> mainProductIds = productCollectDAOW.getAllProductIdsByUserId(mainId);
		List<String> subProductIds = productCollectDAOW.getAllProductIdsByUserId(subId);
		List<String> mainShopIds = shopFavoriteDAOW.getAllShopIdsByUserId(mainId);
		List<String> subShopIds = shopFavoriteDAOW.getAllShopIdsByUserId(subId);

		List<String> subDiffProductIds = getDifferentList(subProductIds, mainProductIds);
		List<String> subDiffShopIds = getDifferentList(subShopIds, mainShopIds);

		copySubDifferentToMain(subDiffProductIds, subDiffShopIds, mainId, subId, machineId);
	}

	@Override
	public void replace(String mainId, String subId, String machineId) {
		LOGGER.info("Replace user data with main id < " + mainId + " > sub id < " + subId + " > machine id < " + machineId + " >");
		List<String> mainProductIds = productCollectDAOW.getAllProductIdsByUserId(mainId);
		List<String> subProductIds = productCollectDAOW.getAllProductIdsByUserId(subId);
		List<String> mainShopIds = shopFavoriteDAOW.getAllShopIdsByUserId(mainId);
		List<String> subShopIds = shopFavoriteDAOW.getAllShopIdsByUserId(subId);

		List<String> mainDiffProductIds = getDifferentList(mainProductIds, subProductIds);
		List<String> subDiffProductIds = getDifferentList(subProductIds, mainProductIds);
		List<String> mainDiffShopIds = getDifferentList(mainShopIds, subShopIds);
		List<String> subDiffShopIds = getDifferentList(subShopIds, mainShopIds);

		copyMainDifferentToSub(mainDiffProductIds, mainDiffShopIds, mainId, subId, machineId);
		cancelSubDifferentData(subDiffProductIds, subDiffShopIds, subId);
	}

	@Override
	public void batchCancel(String userId) {
		LOGGER.info("Batch cancel user data with user id < " + userId + " >");
		batchCancelProducts(userId);
		batchCancelShops(userId);
	}

	@Override
	public int getUserCollectNum(String userId) {
		LOGGER.info("Get user collect number with user id < " + userId + " >");
		return productCollectDAOR.getCollectNumByUserId(userId);
	}

	@Override
	public int getUserCollectNum(String userId, Date endTime) {
		LOGGER.info("Get user collect number with user id < " + userId + " > and endTime < " + new DateFormatter().format(endTime) + " >");
		return productCollectDAOR.getCollectNumByUserIdAndEndTime(userId, endTime);
	}

	@Override
	public int getUserFavoriteNum(String userId) {
		LOGGER.info("Get user favorite number with user id < " + userId + " >");
		return shopFavoriteDAOR.getFavoriteNumByUserId(userId);
	}

	@Override
	public void dealWithAnonymousData(String userId, String machineId) {
		LOGGER.info("Deal with anonymous data with user id < " + userId + " > machine id < " + machineId + " >");
		final String anonyId = new StringBuffer(S.PREFIX_ANONYMOUS).append(machineId).toString();
		copyAnonymousDataIfNotExist(userId, anonyId, machineId);
		batchCancel(anonyId);
	}

	private void copySubDifferentToMain(List<String> subDiffProductIds, List<String> subDiffShopIds, String mainId, String subId, String machineId) {
		if (subDiffProductIds != null && subDiffProductIds.size() != 0) {
			final int count = subDiffProductIds.size();
			final int page = count / S.BATCH_SIZE;
			for (int i = 0; i <= page; i++) {
				List<String> productIds = new ArrayList<String>();
				if (i == page) {
					productIds = subDiffProductIds.subList(i * S.BATCH_SIZE, count);
				} else {
					productIds = subDiffProductIds.subList(i * S.BATCH_SIZE, (i + 1) * S.BATCH_SIZE);
				}
				if (productIds == null || productIds.isEmpty()) {
					continue;
				}
				recoverOrSaveMainProducts(mainId, subId, machineId, productIds);
			}
		}
		if (subDiffShopIds != null && subDiffShopIds.size() != 0) {
			final int count = subDiffShopIds.size();
			final int page = count / S.BATCH_SIZE;
			for (int i = 0; i <= page; i++) {
				List<String> shopIds = new ArrayList<String>();
				if (i == page) {
					shopIds = subDiffShopIds.subList(i * S.BATCH_SIZE, count);
				} else {
					shopIds = subDiffShopIds.subList(i * S.BATCH_SIZE, (i + 1) * S.BATCH_SIZE);
				}
				if (shopIds == null || shopIds.isEmpty()) {
					continue;
				}
				recoverOrSaveMainShops(mainId, subId, machineId, shopIds);
			}
		}
	}

	private void recoverOrSaveMainProducts(String mainId, String subId, String machineId, List<String> productIds) {
		List<ProductCollect> recoverProducts = productCollectDAOW.getProductsByUserIdAndProductIdsAndStatus(mainId, ProductStatus.Cancel.getCode(), productIds);
		Map<String, ProductCollect> recoverProductsMap = getProductMap(recoverProducts);
		List<ProductCollect> allProducts = productCollectDAOW.getProductsByUserIdAndProductIdsAndStatus(subId, ProductStatus.Normal.getCode(), productIds);
		for (ProductCollect product : allProducts) {
			final String productId = product.getProductId();
			if (recoverProductsMap.containsKey(productId)) {
				recoverProductsMap.get(productId).normal(machineId, product.getCollectTime(), product.getNetworkId(), product.getSoftwareVersion(), product.getSoftwareName(), product.getFirmWareVersion(), product.getReferId());
			} else {
				productCollectDAOW.save(productCollectFactory.newInstance(productId, product.getCollectTime(), machineId, product.getNetworkId(), mainId, product.getSoftwareVersion(), product.getSoftwareName(), product.getFirmWareVersion(), product.getReferId()));
				counterService.increaseProductCount(productId);
			}
		}
	}

	private void recoverOrSaveMainShops(String mainId, String subId, String machineId, List<String> shopIds) {
		List<ShopFavorite> recoverShops = shopFavoriteDAOW.getShopsByUserIdAndShopIdsAndStatus(mainId, ShopStatus.Cancel.getCode(), shopIds);
		Map<String, ShopFavorite> recoverShopsMap = getShopMap(recoverShops);
		List<ShopFavorite> allShops = shopFavoriteDAOW.getShopsByUserIdAndShopIdsAndStatus(subId, ShopStatus.Normal.getCode(), shopIds);
		for (ShopFavorite shop : allShops) {
			final String shopId = shop.getShopId();
			if (recoverShopsMap.containsKey(shopId)) {
				recoverShopsMap.get(shopId).normal(machineId, shop.getFavoriteTime(), shop.getNetworkId(), shop.getSoftwareVersion(), shop.getSoftwareName(), shop.getFirmWareVersion(), shop.getReferId());
			} else {
				shopFavoriteDAOW.save(shopFavoriteFactory.newInstance(shopId, shop.getFavoriteTime(), machineId, shop.getNetworkId(), mainId, shop.getSoftwareVersion(), shop.getSoftwareName(), shop.getFirmWareVersion(), shop.getReferId()));
				counterService.increaseShopCount(shopId);
			}
		}
	}

	private void copyMainDifferentToSub(List<String> mainDiffProductIds, List<String> mainDiffShopIds, String mainId, String subId, String machineId) {
		if (mainDiffProductIds != null && mainDiffProductIds.size() != 0) {
			final int count = mainDiffProductIds.size();
			final int page = count / S.BATCH_SIZE;
			for (int i = 0; i <= page; i++) {
				List<String> productIds = new ArrayList<String>();
				if (i == page) {
					productIds = mainDiffProductIds.subList(i * S.BATCH_SIZE, count);
				} else {
					productIds = mainDiffProductIds.subList(i * S.BATCH_SIZE, (i + 1) * S.BATCH_SIZE);
				}
				if (productIds == null || productIds.isEmpty()) {
					continue;
				}
				recoverOrSaveSubProducts(mainId, subId, machineId, productIds);
			}
		}
		if (mainDiffShopIds != null && mainDiffShopIds.size() != 0) {
			final int count = mainDiffShopIds.size();
			final int page = count / S.BATCH_SIZE;
			for (int i = 0; i <= page; i++) {
				List<String> shopIds = new ArrayList<String>();
				if (i == page) {
					shopIds = mainDiffShopIds.subList(i * S.BATCH_SIZE, count);
				} else {
					shopIds = mainDiffShopIds.subList(i * S.BATCH_SIZE, (i + 1) * S.BATCH_SIZE);
				}
				if (shopIds == null || shopIds.isEmpty()) {
					continue;
				}
				recoverOrSaveSubShops(mainId, subId, machineId, shopIds);
			}
		}
	}

	private void recoverOrSaveSubProducts(String mainId, String subId, String machineId, List<String> productIds) {
		List<ProductCollect> recoverProducts = productCollectDAOW.getProductsByUserIdAndProductIdsAndStatus(subId, ProductStatus.Cancel.getCode(), productIds);
		Map<String, ProductCollect> recoverProductsMap = getProductMap(recoverProducts);
		List<ProductCollect> allProducts = productCollectDAOW.getProductsByUserIdAndProductIdsAndStatus(mainId, ProductStatus.Normal.getCode(), productIds);
		for (ProductCollect product : allProducts) {
			final String productId = product.getProductId();
			if (recoverProductsMap.containsKey(productId)) {
				recoverProductsMap.get(productId).normal(product.getCollectTime(), product.getNetworkId(), product.getSoftwareVersion(), product.getSoftwareName(), product.getFirmWareVersion(), product.getReferId());
			} else {
				productCollectDAOW.save(productCollectFactory.newInstance(productId, product.getCollectTime(), machineId, product.getNetworkId(), subId, product.getSoftwareVersion(), product.getSoftwareName(), product.getFirmWareVersion(), product.getReferId()));
				counterService.increaseProductCount(productId);
			}
		}
	}

	private void recoverOrSaveSubShops(String mainId, String subId, String machineId, List<String> shopIds) {
		List<ShopFavorite> recoverShops = shopFavoriteDAOW.getShopsByUserIdAndShopIdsAndStatus(subId, ShopStatus.Cancel.getCode(), shopIds);
		Map<String, ShopFavorite> recoverShopsMap = getShopMap(recoverShops);
		List<ShopFavorite> allShops = shopFavoriteDAOW.getShopsByUserIdAndShopIdsAndStatus(mainId, ShopStatus.Normal.getCode(), shopIds);
		for (ShopFavorite shop : allShops) {
			final String shopId = shop.getShopId();
			if (recoverShopsMap.containsKey(shopId)) {
				recoverShopsMap.get(shopId).normal(shop.getFavoriteTime(), shop.getNetworkId(), shop.getSoftwareVersion(), shop.getSoftwareName(), shop.getFirmWareVersion(), shop.getReferId());
			} else {
				shopFavoriteDAOW.save(shopFavoriteFactory.newInstance(shopId, shop.getFavoriteTime(), machineId, shop.getNetworkId(), subId, shop.getSoftwareVersion(), shop.getSoftwareName(), shop.getFirmWareVersion(), shop.getReferId()));
				counterService.increaseShopCount(shopId);
			}
		}
	}

	private void cancelSubDifferentData(List<String> subDiffProductIds, List<String> subDiffShopIds, String subId) {
		if (subDiffProductIds != null && subDiffProductIds.size() != 0) {
			List<ProductCollect> products = productCollectDAOW.getProductsByUserIdAndProductIdsAndStatus(subId, ProductStatus.Normal.getCode(), subDiffProductIds);
			for (ProductCollect product : products) {
				product.cancel();
			}
		}
		if (subDiffShopIds != null && subDiffShopIds.size() != 0) {
			List<ShopFavorite> shops = shopFavoriteDAOW.getShopsByUserIdAndShopIdsAndStatus(subId, ShopStatus.Normal.getCode(), subDiffShopIds);
			for (ShopFavorite shop : shops) {
				shop.cancel();
			}
		}
	}

	private void batchCancelProducts(String userId) {
		List<String> productIds = productCollectDAOW.getAllProductIdsByUserId(userId);
		for (String productId : productIds) {
			productCollectDAOW.cancelProduct(userId, productId);
		}
		counterService.decreaseMultipleProductCount(productIds);
		LOGGER.info("Batch cancel all anonymous user products and nums is < " + productIds.size() + " >");
	}

	private void batchCancelShops(String userId) {
		List<String> shopIds = shopFavoriteDAOW.getAllShopIdsByUserId(userId);
		for (String shopId : shopIds) {
			shopFavoriteDAOW.cancelShop(userId, shopId);
		}
		counterService.decreaseMultipleShopCount(shopIds);
		LOGGER.info("Batch cancel all anonymous user shops and nums is < " + shopIds.size() + " >");
	}

	private void copyAnonymousDataIfNotExist(String userId, String anonyId, String machineId) {
		List<String> realProductIds = productCollectDAOW.getAllProductIdsByUserId(userId);
		List<String> anonyProductIds = productCollectDAOW.getAllProductIdsByUserId(anonyId);
		List<String> realShopIds = shopFavoriteDAOW.getAllShopIdsByUserId(userId);
		List<String> anonyShopIds = shopFavoriteDAOW.getAllShopIdsByUserId(anonyId);

		List<String> anonyDiffProductIds = getDifferentList(anonyProductIds, realProductIds);
		List<String> anonyDiffShopIds = getDifferentList(anonyShopIds, realShopIds);

		copyAnonyDifferentToRealIfNotExit(anonyDiffProductIds, anonyDiffShopIds, userId, anonyId, machineId);
	}

	private void copyAnonyDifferentToRealIfNotExit(List<String> anonyDiffProductIds, List<String> anonyDiffShopIds, String userId, String anonyId, String machineId) {
		if (anonyDiffProductIds != null && anonyDiffProductIds.size() != 0) {
			final int count = anonyDiffProductIds.size();
			final int page = count / S.BATCH_SIZE;
			for (int i = 0; i <= page; i++) {
				List<String> productIds = new ArrayList<String>();
				if (i == page) {
					productIds = anonyDiffProductIds.subList(i * S.BATCH_SIZE, count);
				} else {
					productIds = anonyDiffProductIds.subList(i * S.BATCH_SIZE, (i + 1) * S.BATCH_SIZE);
				}
				if (productIds == null || productIds.isEmpty()) {
					continue;
				}
				saveRealDiffProducts(userId, anonyId, machineId, productIds);
			}
		}
		if (anonyDiffShopIds != null && anonyDiffShopIds.size() != 0) {
			final int count = anonyDiffShopIds.size();
			final int page = count / S.BATCH_SIZE;
			for (int i = 0; i <= page; i++) {
				List<String> shopIds = new ArrayList<String>();
				if (i == page) {
					shopIds = anonyDiffShopIds.subList(i * S.BATCH_SIZE, count);
				} else {
					shopIds = anonyDiffShopIds.subList(i * S.BATCH_SIZE, (i + 1) * S.BATCH_SIZE);
				}
				if (shopIds == null || shopIds.isEmpty()) {
					continue;
				}
				saveRealDiffShops(userId, anonyId, machineId, shopIds);
			}
		}
	}

	private void saveRealDiffProducts(String userId, String anonyId, String machineId, List<String> productIds) {
		List<ProductCollect> recoverProducts = productCollectDAOW.getProductsByUserIdAndProductIdsAndStatus(userId, ProductStatus.Cancel.getCode(), productIds);
		Map<String, ProductCollect> recoverProductsMap = getProductMap(recoverProducts);
		List<ProductCollect> allProducts = productCollectDAOW.getProductsByUserIdAndProductIdsAndStatus(anonyId, ProductStatus.Normal.getCode(), productIds);
		for (ProductCollect product : allProducts) {
			final String productId = product.getProductId();
			if (!recoverProductsMap.containsKey(productId)) {
				productCollectDAOW.save(productCollectFactory.newInstance(productId, product.getCollectTime(), machineId, product.getNetworkId(), userId, product.getSoftwareVersion(), product.getSoftwareName(), product.getFirmWareVersion(), product.getReferId()));
				counterService.increaseProductCount(productId);
			}
		}
	}

	private void saveRealDiffShops(String userId, String anonyId, String machineId, List<String> shopIds) {
		List<ShopFavorite> recoverShops = shopFavoriteDAOW.getShopsByUserIdAndShopIdsAndStatus(userId, ShopStatus.Cancel.getCode(), shopIds);
		Map<String, ShopFavorite> recoverShopsMap = getShopMap(recoverShops);
		List<ShopFavorite> allShops = shopFavoriteDAOW.getShopsByUserIdAndShopIdsAndStatus(anonyId, ShopStatus.Normal.getCode(), shopIds);
		for (ShopFavorite shop : allShops) {
			final String shopId = shop.getShopId();
			if (!recoverShopsMap.containsKey(shopId)) {
				shopFavoriteDAOW.save(shopFavoriteFactory.newInstance(shopId, shop.getFavoriteTime(), machineId, shop.getNetworkId(), userId, shop.getSoftwareVersion(), shop.getSoftwareName(), shop.getFirmWareVersion(), shop.getReferId()));
				counterService.increaseShopCount(shopId);
			}
		}
	}

	private List<String> getDifferentList(List<String> srcList, List<String> desList) {
		List<String> tempList = new ArrayList<String>();
		tempList.addAll(srcList);
		tempList.removeAll(desList);
		return tempList;
	}

	private Map<String, ProductCollect> getProductMap(List<ProductCollect> products) {
		Map<String, ProductCollect> temp = new HashMap<String, ProductCollect>();
		for (ProductCollect product : products) {
			temp.put(product.getProductId(), product);
		}
		return temp;
	}

	private Map<String, ShopFavorite> getShopMap(List<ShopFavorite> shops) {
		Map<String, ShopFavorite> temp = new HashMap<String, ShopFavorite>();
		for (ShopFavorite shop : shops) {
			temp.put(shop.getShopId(), shop);
		}
		return temp;
	}

	public void setProductCollectDAOW(ProductCollectDAO productCollectDAOW) {
		this.productCollectDAOW = productCollectDAOW;
	}

	public void setProductCollectDAOR(ProductCollectDAO productCollectDAOR) {
		this.productCollectDAOR = productCollectDAOR;
	}

	public void setShopFavoriteDAOW(ShopFavoriteDAO shopFavoriteDAOW) {
		this.shopFavoriteDAOW = shopFavoriteDAOW;
	}

	public void setShopFavoriteDAOR(ShopFavoriteDAO shopFavoriteDAOR) {
		this.shopFavoriteDAOR = shopFavoriteDAOR;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public void setProductCollectFactory(ProductCollectFactory productCollectFactory) {
		this.productCollectFactory = productCollectFactory;
	}

	public void setShopFavoriteFactory(ShopFavoriteFactory shopFavoriteFactory) {
		this.shopFavoriteFactory = shopFavoriteFactory;
	}

}
