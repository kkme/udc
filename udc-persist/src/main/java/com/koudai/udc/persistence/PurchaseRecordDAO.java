package com.koudai.udc.persistence;

import java.util.Date;
import java.util.List;

import com.koudai.udc.domain.IdAndDate;
import com.koudai.udc.domain.PurchaseRecord;

public interface PurchaseRecordDAO {

	void save(PurchaseRecord purchaseRecord);

	PurchaseRecord getPurchaseRecordByUserIdAndProductId(String userId, String productId);

	List<IdAndDate> getProductIdAndPurchaseTimeByUserIdAndStartAndEndTime(String userId, Date startTime, Date endTime);

}
