package com.koudai.udc.domain.factory;

import java.text.ParseException;
import java.util.Date;

import com.koudai.udc.domain.ProductCollectTop;

public interface ProductCollectTopFactory {

	ProductCollectTop newInstance(String productId, int collectedNum, String typeId, Date statTime, int useType);

	ProductCollectTop newInstance(String productId, int collectedNum, Date statTime) throws ParseException;

}
