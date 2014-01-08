package com.koudai.udc.domain.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ProductCollectedCount;
import com.koudai.udc.domain.ShopFavoritedCount;
import com.koudai.udc.domain.factory.ProductCollectedCountFactory;
import com.koudai.udc.domain.factory.ShopFavoritedCountFactory;
import com.koudai.udc.domain.service.CounterService;
import com.koudai.udc.persistence.ProductCollectedCountDAO;
import com.koudai.udc.persistence.ShopFavoritedCountDAO;

public class CounterServiceImpl implements CounterService {

	private static final Logger LOGGER = Logger.getLogger(CounterServiceImpl.class);

	private ProductCollectedCountDAO productCollectedCountDAOW;

	private ShopFavoritedCountDAO shopFavoritedCountDAOW;

	private ProductCollectedCountFactory productCollectedCountFactory;

	private ShopFavoritedCountFactory shopFavoritedCountFactory;

	@Override
	public void increaseProductCount(String productId) {
		ProductCollectedCount productCollectedCount = productCollectedCountDAOW.getProductCollectedCountByProductId(productId);
		if (productCollectedCount == null) {
			productCollectedCountDAOW.save(productCollectedCountFactory.newInstance(productId));
			return;
		}
		productCollectedCount.increase();
	}

	@Override
	public void decreaseProductCount(String productId) {
		ProductCollectedCount productCollectedCount = productCollectedCountDAOW.getProductCollectedCountByProductId(productId);
		if (productCollectedCount == null) {
			LOGGER.error("ProductCollectedCount wiht product id < " + productId + " > not found");
			return;
		}
		productCollectedCount.decrease();
	}

	@Override
	public void decreaseMultipleProductCount(List<String> productIds) {
		for (String productId : productIds) {
			decreaseProductCount(productId);
		}
	}

	@Override
	public void increaseShopCount(String shopId) {
		ShopFavoritedCount shopFavoritedCount = shopFavoritedCountDAOW.getShopFavoritedCountByShopId(shopId);
		if (shopFavoritedCount == null) {
			shopFavoritedCountDAOW.save(shopFavoritedCountFactory.newInstance(shopId));
			return;
		}
		shopFavoritedCount.increase();
	}

	@Override
	public void decreaseShopCount(String shopId) {
		ShopFavoritedCount shopFavoritedCount = shopFavoritedCountDAOW.getShopFavoritedCountByShopId(shopId);
		if (shopFavoritedCount == null) {
			LOGGER.error("ShopFavoritedCount wiht shop id < " + shopId + " > not found");
			return;
		}
		shopFavoritedCount.decrease();
	}

	@Override
	public void decreaseMultipleShopCount(List<String> shopIds) {
		for (String shopId : shopIds) {
			decreaseShopCount(shopId);
		}
	}

	public void setProductCollectedCountDAOW(ProductCollectedCountDAO productCollectedCountDAOW) {
		this.productCollectedCountDAOW = productCollectedCountDAOW;
	}

	public void setShopFavoritedCountDAOW(ShopFavoritedCountDAO shopFavoritedCountDAOW) {
		this.shopFavoritedCountDAOW = shopFavoritedCountDAOW;
	}

	public void setProductCollectedCountFactory(ProductCollectedCountFactory productCollectedCountFactory) {
		this.productCollectedCountFactory = productCollectedCountFactory;
	}

	public void setShopFavoritedCountFactory(ShopFavoritedCountFactory shopFavoritedCountFactory) {
		this.shopFavoritedCountFactory = shopFavoritedCountFactory;
	}

}
