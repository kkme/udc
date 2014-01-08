package com.koudai.udc.persistence;

import java.util.List;

import com.koudai.udc.domain.ProductCollectWeekTop;

public interface ProductCollectWeekTopDAO {

	void save(ProductCollectWeekTop weekTop);

	List<ProductCollectWeekTop> getWeekTopsByTypeIdAndWeekAndYear(String typeId, int week, int year);

}
