package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.ProductCollect;
import com.koudai.udc.domain.factory.ProductCollectFactory;
import com.koudai.udc.domain.service.CounterService;
import com.koudai.udc.exception.ActionErrorDispatchException;
import com.koudai.udc.persistence.ProductCollectDAO;

public abstract class ProductCollectAction extends JsonResultAction {

	private static final long serialVersionUID = -3958730886335186249L;

	private static final Logger LOGGER = Logger.getLogger(ProductCollectAction.class);

	private CounterService counterService;

	private ProductCollectDAO productCollectDAOW;

	private ProductCollectFactory productCollectFactory;

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

	protected void collectAndCount(String userId, String productId, String machineId, String networkId, String softwareName, String softwareVersion, String firmWareVersion, String referId) {
		ProductCollect productCollect = productCollectDAOW.getProductCollectByUserAndProductId(userId, productId);
		if (productCollect != null) {
			if (productCollect.getCounterService() == null) {
				LOGGER.error("CounterService is null");
				productCollect.setCounterService(counterService);
			}
			productCollect.normal(machineId, networkId, softwareVersion, softwareName, firmWareVersion, referId);
			return;
		}
		productCollectDAOW.save(productCollectFactory.newInstance(productId, machineId, networkId, userId, softwareVersion, softwareName, firmWareVersion, referId));
		counterService.increaseProductCount(productId);
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public void setProductCollectDAOW(ProductCollectDAO productCollectDAOW) {
		this.productCollectDAOW = productCollectDAOW;
	}

	public void setProductCollectFactory(ProductCollectFactory productCollectFactory) {
		this.productCollectFactory = productCollectFactory;
	}

}
