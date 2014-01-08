package com.koudai.udc.domain.factory;

import java.text.ParseException;

import com.koudai.udc.domain.ShopFavoriteTop;

public interface ShopFavoriteTopFactory {

	ShopFavoriteTop newInstance(String shopId, int favoritedNum, String statTime) throws ParseException;

}
