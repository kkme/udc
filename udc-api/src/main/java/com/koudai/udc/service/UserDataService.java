package com.koudai.udc.service;

import java.util.Date;

public interface UserDataService {

	void synchronize(String mainId, String subId, String machineId);

	void replace(String mainId, String subId, String machineId);

	void batchCancel(String userId);

	int getUserCollectNum(String userId);

	int getUserCollectNum(String userId, Date endTime);

	int getUserFavoriteNum(String userId);

	void dealWithAnonymousData(String userId, String machineId);

}