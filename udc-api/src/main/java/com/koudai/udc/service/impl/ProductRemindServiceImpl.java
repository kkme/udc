package com.koudai.udc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.BijiaProduct;
import com.koudai.udc.domain.ConfirmStatus;
import com.koudai.udc.domain.PriceRemind;
import com.koudai.udc.domain.ProductCollectBijia;
import com.koudai.udc.domain.SubscribeType;
import com.koudai.udc.domain.factory.PriceRemindFactory;
import com.koudai.udc.domain.factory.ProductCollectBijiaFactory;
import com.koudai.udc.persistence.PriceRemindDAO;
import com.koudai.udc.persistence.ProductCollectBijiaDAO;
import com.koudai.udc.service.ProductRemindService;

public class ProductRemindServiceImpl implements ProductRemindService {

	private static final Logger LOGGER = Logger.getLogger(ProductRemindServiceImpl.class);

	private ProductCollectBijiaDAO productCollectBijiaDAOW;

	private ProductCollectBijiaDAO productCollectBijiaDAOR;

	private PriceRemindDAO priceRemindDAOW;

	private PriceRemindDAO priceRemindDAOR;

	private ProductCollectBijiaFactory productCollectBijiaFactory;

	private PriceRemindFactory priceRemindFactory;

	@Override
	public boolean collectProductAndRemindPrice(String productId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice, int noticeType, String email) {
		boolean isCollected = dealWithProduct(productId, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId);
		dealWithPriceRemind(productId, userId, subscribePrice, productUrl, targetPrice, SubscribeType.Curve.getCode(), noticeType, email);
		return isCollected;
	}

	@Override
	public void cancelProduct(String productId, String userId) {
		ProductCollectBijia product = productCollectBijiaDAOW.getProductCollectBijiaByUserAndProductId(userId, productId);
		if (product != null) {
			product.cancel();
		}
		PriceRemind remind = priceRemindDAOW.getPriceRemindByUserAndProductId(userId, productId);
		if (remind != null) {
			remind.cancel();
		}
	}

	@Override
	public void remindPrice(String productId, String userId, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice, int subscribeType, int noticeType, String email) {
		ProductCollectBijia product = productCollectBijiaDAOW.getProductCollectBijiaByUserAndProductId(userId, productId);
		if (product == null) {
			LOGGER.error("Bijia product < id ( " + productId + " ) , user ( " + userId + " ) > not exist");
			return;
		}
		boolean productCancelled = product.remindPrice();
		if (!productCancelled) {
			dealWithPriceRemind(productId, userId, subscribePrice, productUrl, targetPrice, subscribeType, noticeType, email);
		}
	}

	@Override
	public void cancelRemind(String productId, String userId) {
		ProductCollectBijia product = productCollectBijiaDAOW.getProductCollectBijiaByUserAndProductId(userId, productId);
		if (product == null) {
			LOGGER.error("Bijia product < id ( " + productId + " ) , user ( " + userId + " ) > not exist");
			return;
		}
		product.cancelRemind();
		PriceRemind remind = priceRemindDAOW.getPriceRemindByUserAndProductId(userId, productId);
		if (remind != null) {
			remind.cancel();
		}
	}

	@Override
	public List<BijiaProduct> getBijiaProducts(String userId, int start, int end) {
		if (start == 0 && end == 0) {
			return getAllBijiaProducts(userId);
		}
		return getBijiaProductsByStartAndEndPos(userId, start, end);
	}

	private boolean dealWithProduct(String productId, String machineId, String networkId, String userId, String softwareVersion, String softwareName, String firmWareVersion, String referId) {
		ProductCollectBijia product = productCollectBijiaDAOW.getProductCollectBijiaByUserAndProductId(userId, productId);
		if (product == null) {
			productCollectBijiaDAOW.save(productCollectBijiaFactory.newInstance(productId, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId, ConfirmStatus.YES.getCode()));
			return false;
		}
		return product.normalWithPriceReduction(machineId, networkId, softwareVersion, softwareName, firmWareVersion, referId);
	}

	private void dealWithPriceRemind(String productId, String userId, BigDecimal subscribePrice, String productUrl, BigDecimal targetPrice, int subscribeType, int noticeType, String email) {
		PriceRemind remind = priceRemindDAOW.getPriceRemindByUserAndProductId(userId, productId);
		if (remind == null) {
			priceRemindDAOW.save(priceRemindFactory.newInstnce(productId, userId, subscribePrice, productUrl, targetPrice, subscribeType, noticeType, email));
			return;
		}
		remind.update(subscribePrice, productUrl, targetPrice, noticeType, email);
	}

	private List<BijiaProduct> getAllBijiaProducts(String userId) {
		List<String> filterableIds = new ArrayList<String>();
		List<BijiaProduct> products = priceRemindDAOR.getBijiaProductsByUserId(userId);
		for (BijiaProduct bijiaProduct : products) {
			filterableIds.add(bijiaProduct.getProductId());
		}
		products.addAll(productCollectBijiaDAOR.getBijiaProductsByUserId(userId, filterableIds));
		return products;
	}

	private List<BijiaProduct> getBijiaProductsByStartAndEndPos(String userId, int start, int end) {
		List<String> filterableIds = priceRemindDAOR.getProductIdsByUserId(userId);
		int total = filterableIds.size();
		if (total <= start) {
			return productCollectBijiaDAOR.getBijiaProductsByUserIdAndStartAndEndPos(userId, filterableIds, (start - total), (end - total));
		}
		if (total > end) {
			return priceRemindDAOR.getBijiaProductsByUserIdAndStartAndEndPos(userId, start, end);
		}
		List<BijiaProduct> products = priceRemindDAOR.getBijiaProductsByUserIdAndStartAndEndPos(userId, start, (total - 1));
		products.addAll(productCollectBijiaDAOR.getBijiaProductsByUserIdAndStartAndEndPos(userId, filterableIds, 0, (end - total)));
		return products;
	}

	public void setProductCollectBijiaDAOW(ProductCollectBijiaDAO productCollectBijiaDAOW) {
		this.productCollectBijiaDAOW = productCollectBijiaDAOW;
	}

	public void setProductCollectBijiaDAOR(ProductCollectBijiaDAO productCollectBijiaDAOR) {
		this.productCollectBijiaDAOR = productCollectBijiaDAOR;
	}

	public void setPriceRemindDAOW(PriceRemindDAO priceRemindDAOW) {
		this.priceRemindDAOW = priceRemindDAOW;
	}

	public void setPriceRemindDAOR(PriceRemindDAO priceRemindDAOR) {
		this.priceRemindDAOR = priceRemindDAOR;
	}

	public void setProductCollectBijiaFactory(ProductCollectBijiaFactory productCollectBijiaFactory) {
		this.productCollectBijiaFactory = productCollectBijiaFactory;
	}

	public void setPriceRemindFactory(PriceRemindFactory priceRemindFactory) {
		this.priceRemindFactory = priceRemindFactory;
	}

}
