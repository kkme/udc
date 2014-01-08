package com.koudai.udc.domain.factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.koudai.udc.domain.ShopFavoriteTop;

public class SimpleShopFavoriteTopFactory implements ShopFavoriteTopFactory {

	@Override
	public ShopFavoriteTop newInstance(String shopId, int favoritedNum, String statTime) throws ParseException {
		Date statDate = new SimpleDateFormat("yyyy-MM-dd").parse(statTime);
		return new ShopFavoriteTop(shopId, favoritedNum, statDate);
	}

}
