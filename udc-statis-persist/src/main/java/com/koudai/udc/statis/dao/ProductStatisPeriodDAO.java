package com.koudai.udc.statis.dao;

import java.util.Date;
import java.util.List;

import com.koudai.udc.statis.domain.ProductStatisPeriod;

public interface ProductStatisPeriodDAO {

	void save(ProductStatisPeriod productStatisPeriod);

	void batchSave(List<ProductStatisPeriod> productStatisPeriods);

	List<ProductStatisPeriod> getProductStatisPeriodsByProductIdAndStartAndEndTime(String productId, Date startTime, Date endTime);

}
