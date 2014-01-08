package com.koudai.udc.domain.factory;

import com.koudai.udc.domain.DeviceInfo;
import com.koudai.udc.domain.Platform;

public class SimpleDeviceInfoFactory implements DeviceInfoFactory {

	@Override
	public DeviceInfo newInstance(String machineId, String macId) {
		return new DeviceInfo(machineId, macId);
	}

	@Override
	public DeviceInfo newInstance(String machineId, String macId, String token, String companyName, String machineName, String terminalUa, String screenRes, String imei, String imsi, String udid, String phoneNumber, Platform platform, String firewareVersion, String softwareVersion,
			String softwareBuild) {
		return new DeviceInfo(machineId, macId, token, companyName, machineName, terminalUa, screenRes, imei, imsi, udid, phoneNumber, platform, firewareVersion, softwareVersion, softwareBuild);
	}

}
