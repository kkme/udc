package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ShopFavorite;
import com.koudai.udc.domain.factory.ShopFavoriteFactory;
import com.koudai.udc.domain.service.CounterService;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ShopFavoriteDAO;

public abstract class ShopFavoriteAction extends JsonResultAction {

	private static final long serialVersionUID = -3619076519047585543L;

	private static final Logger LOGGER = Logger.getLogger(ShopFavoriteAction.class);

	private CounterService counterService;

	private ShopFavoriteDAO shopFavoriteDAOW;

	private ShopFavoriteFactory shopFavoriteFactory;

	@Override
	public String execute() throws Exception {
		try {
			doExecute();
			result = getSuccessfulResult();
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error(e);
			result = getFailedResult(e.getMessage());
			throw new ActionErrorDispatchException(ERROR, e);
		}
	}

	protected abstract void doExecute() throws Exception;

	protected void favoriteAndCount(String userId, String shopId, String machineId, String networkId, String softwareName, String softwareVersion, String firmWareVersion, String referId) {
		ShopFavorite shopFavorite = shopFavoriteDAOW.getShopFavoriteByUserAndShopId(userId, shopId);
		if (shopFavorite != null) {
			if (shopFavorite.getCounterService() == null) {
				LOGGER.error("CounterService is null");
				shopFavorite.setCounterService(counterService);
			}
			shopFavorite.normal(machineId, networkId, softwareVersion, softwareName, firmWareVersion, referId);
			return;
		}
		shopFavoriteDAOW.save(shopFavoriteFactory.newInstance(shopId, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId));
		counterService.increaseShopCount(shopId);
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public void setShopFavoriteDAOW(ShopFavoriteDAO shopFavoriteDAOW) {
		this.shopFavoriteDAOW = shopFavoriteDAOW;
	}

	public void setShopFavoriteFactory(ShopFavoriteFactory shopFavoriteFactory) {
		this.shopFavoriteFactory = shopFavoriteFactory;
	}

}
