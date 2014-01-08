package com.koudai.udc.domain.factory;

import java.util.Date;

import com.koudai.udc.domain.PurchaseRecord;

public interface PurchaseRecordFactory {

	PurchaseRecord newInstance(String userId, String productId, Date purchaseTime, String machineId, String networkId, String softwareVersion, String softwareName, String firmWareVersion, String referId);

}
