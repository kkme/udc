package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.ProductCollectWeekTop;

public interface ProductCollectWeekTopFactory {

	ProductCollectWeekTop newInstance(String productId, int collectedNum, String typeId, int week, int year);

}
