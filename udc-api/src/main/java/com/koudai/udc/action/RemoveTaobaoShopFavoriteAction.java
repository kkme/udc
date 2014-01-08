package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.ShopFavoriteTaobaoDAO;
import com.koudai.udc.utils.S;

public class RemoveTaobaoShopFavoriteAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = 1642393847261534615L;

	private static final Logger LOGGER = Logger.getLogger(RemoveTaobaoShopFavoriteAction.class);

	private ShopFavoriteTaobaoDAO shopFavoriteTaobaoDAOW;

	private String userId;

	@Override
	protected void doExecute() throws Exception {
		if (S.isInvalidValue(userId) || !S.isTaobaoUser(userId)) {
			throw new IncorrectInputParameterException("User id is invalid");
		}
		long beginTime = System.currentTimeMillis();
		shopFavoriteTaobaoDAOW.deleteShopsByUserId(userId);
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("removeTaobaoShopFavorite cost>>>" + costTime);
	}

	public void setUserID(String userID) {
		this.userId = userID;
	}

	public void setShopFavoriteTaobaoDAOW(ShopFavoriteTaobaoDAO shopFavoriteTaobaoDAOW) {
		this.shopFavoriteTaobaoDAOW = shopFavoriteTaobaoDAOW;
	}

}
