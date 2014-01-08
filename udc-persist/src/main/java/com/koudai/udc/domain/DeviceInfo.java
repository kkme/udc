package com.koudai.udc.domain;

public class DeviceInfo extends DeviceInfoDataModel {

	private static final long serialVersionUID = -7020781987464601508L;

	public DeviceInfo() {
	}

	public DeviceInfo(String machineId, String macId) {
		this.machineId = machineId;
		this.macId = macId;
		this.platform = Platform.none;
	}

	public DeviceInfo(String machineId, String macId, String token, String companyName, String machineName, String terminalUa, String screenRes, String imei, String imsi, String udid, String phoneNumber, Platform platform, String firewareVersion, String softwareVersion, String softwareBuild) {
		this.machineId = machineId;
		this.macId = macId;
		this.token = token;
		this.companyName = companyName;
		this.machineName = machineName;
		this.terminalUa = terminalUa;
		this.screenRes = screenRes;
		this.imei = imei;
		this.imsi = imsi;
		this.udid = udid;
		this.phoneNumber = phoneNumber;
		this.platform = platform;
		this.firewareVersion = firewareVersion;
		this.softwareVersion = softwareVersion;
		this.softwareBuild = softwareBuild;
	}

	public void modifyProperties(String macId, String token, String companyName, String machineName, String terminalUa, String screenRes, String imei, String imsi, String udid, String phoneNumber, String firewareVersion, String softwareVersion, String softwareBuild) {
		this.macId = macId;
		if (token != null && !"".equals(token.trim())) {
			this.token = token;
		}
		this.companyName = companyName;
		this.machineName = machineName;
		this.terminalUa = terminalUa;
		this.screenRes = screenRes;
		this.imei = imei;
		this.imsi = imsi;
		this.udid = udid;
		this.phoneNumber = phoneNumber;
		this.firewareVersion = firewareVersion;
		this.softwareVersion = softwareVersion;
		this.softwareBuild = softwareBuild;
	}

}
