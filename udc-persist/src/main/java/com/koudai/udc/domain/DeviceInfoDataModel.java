package com.koudai.udc.domain;

import java.io.Serializable;

public abstract class DeviceInfoDataModel implements Serializable {

	private static final long serialVersionUID = 1748531660623720715L;

	protected Long id;
	protected String machineId;
	protected String macId;
	protected String token;
	protected String companyName;
	protected String machineName;
	protected String terminalUa;
	protected String screenRes;
	protected String imei;
	protected String imsi;
	protected String udid;
	protected String phoneNumber;
	protected Platform platform;
	protected String firewareVersion;
	protected String softwareVersion;
	protected String softwareBuild;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getMacId() {
		return macId;
	}

	public void setMacId(String macId) {
		this.macId = macId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getTerminalUa() {
		return terminalUa;
	}

	public void setTerminalUa(String terminalUa) {
		this.terminalUa = terminalUa;
	}

	public String getScreenRes() {
		return screenRes;
	}

	public void setScreenRes(String screenRes) {
		this.screenRes = screenRes;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public String getFirewareVersion() {
		return firewareVersion;
	}

	public void setFirewareVersion(String firewareVersion) {
		this.firewareVersion = firewareVersion;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getSoftwareBuild() {
		return softwareBuild;
	}

	public void setSoftwareBuild(String softwareBuild) {
		this.softwareBuild = softwareBuild;
	}

}
