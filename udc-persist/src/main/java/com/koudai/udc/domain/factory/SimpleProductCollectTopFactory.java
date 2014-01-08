package com.koudai.udc.domain.factory;

import java.text.ParseException;
import java.util.Date;

import com.koudai.udc.domain.ProductCollectTop;

public class SimpleProductCollectTopFactory implements ProductCollectTopFactory {

	@Override
	public ProductCollectTop newInstance(String productId, int collectedNum, String typeId, Date statTime, int useType) {
		return new ProductCollectTop(productId, collectedNum, typeId, statTime, useType);
	}

	@Override
	public ProductCollectTop newInstance(String productId, int collectedNum, Date statTime) throws ParseException {
		return new ProductCollectTop(productId, collectedNum, statTime);
	}

}
