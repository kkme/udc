package com.koudai.udc.persistence;

import java.util.Date;
import java.util.List;

import com.koudai.udc.domain.ProductCollectTop;

public interface ProductCollectTopDAO {

	void save(ProductCollectTop productCollectTop);

	List<ProductCollectTop> getTopsByTypeIdAndStartAndEndTime(String typeId, Date startTime, Date endTime, int useType);

	List<ProductCollectTop> getTop30ByStartAndEndTime(Date startTime, Date endTime);

}
