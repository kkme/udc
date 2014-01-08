package com.koudai.udc.persistence;

import java.util.Date;
import java.util.List;

import com.koudai.udc.domain.AndroidPushInfo;

public interface AndroidPushInfoDAO {

	void save(AndroidPushInfo androidPushInfo);

	AndroidPushInfo getLatestPushInfoByMachineIdAndCreateTime(String machineId, Date createTime);

	List<AndroidPushInfo> getPushInfosByMachineIdAndCreateTime(String machineId, Date createTime);

}
