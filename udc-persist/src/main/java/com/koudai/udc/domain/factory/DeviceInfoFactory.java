package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.DeviceInfo;
import com.koudai.udc.domain.Platform;

public interface DeviceInfoFactory {

	DeviceInfo newInstance(String machineId, String macId);

	DeviceInfo newInstance(String machineId, String macId, String token, String companyName, String machineName, String terminalUa, String screenRes, String imei, String imsi, String udid, String phoneNumber, Platform platform, String firewareVersion, String softwareVersion, String softwareBuild);

}
