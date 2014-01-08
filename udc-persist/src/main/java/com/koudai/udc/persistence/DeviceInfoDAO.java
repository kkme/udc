package com.koudai.udc.persistence;

import java.util.List;

import com.koudai.udc.domain.DeviceInfo;
import com.koudai.udc.domain.Platform;

public interface DeviceInfoDAO {

	void save(DeviceInfo deviceInfo);

	DeviceInfo getDeviceInfoByMachineIdAndPlatform(String machineId, Platform platform);

	List<String> getTokensBySoftwarVersionAndPlatform(String fromVersion, String toVersion, Platform platform, int start, int max);

}
