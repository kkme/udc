package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductCollectWeekTop;

public class SimpleProductCollectWeekTopFactory implements ProductCollectWeekTopFactory {

	@Override
	public ProductCollectWeekTop newInstance(String productId, int collectedNum, String typeId, int week, int year) {
		return new ProductCollectWeekTop(productId, collectedNum, typeId, week, year);
	}

}
