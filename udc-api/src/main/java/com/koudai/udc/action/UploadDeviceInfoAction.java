package com.koudai.udc.action;

import org.apache.log4j.Logger;

import com.koudai.udc.domain.DeviceInfo;
import com.koudai.udc.domain.Platform;
import com.koudai.udc.domain.factory.DeviceInfoFactory;
import com.koudai.udc.exception.IncorrectInputParameterException;
import com.koudai.udc.persistence.DeviceInfoDAO;
import com.koudai.udc.utils.DeviceInfoKey;
import com.koudai.udc.utils.S;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

public class UploadDeviceInfoAction extends JsonResultWithTransactionAction {

	private static final long serialVersionUID = -2352084097046820897L;

	private static final Logger LOGGER = Logger.getLogger(UploadDeviceInfoAction.class);

	private String deviceInfoIn;

	private DeviceInfoDAO deviceInfoDAOW;

	private DeviceInfoFactory deviceInfoFactory;

	@Override
	protected void doExecute() throws Exception {
		long beginTime = System.currentTimeMillis();
		if (S.isInvalidValue(deviceInfoIn)) {
			throw new IncorrectInputParameterException("upload_deviceinfo_in is null or empty");
		}
		LOGGER.info("Upload device info request is : " + deviceInfoIn);
		JSONObject content = new JSONObject(deviceInfoIn);
		JSONObject inObject = content.getJSONObject(DeviceInfoKey.CONTENT_KEY);
		final String machineId = inObject.optString(DeviceInfoKey.MACHINE_ID, null);
		final String macId = inObject.optString(DeviceInfoKey.MAC_ID, null);
		final String token = inObject.optString(DeviceInfoKey.TOKEN, null);
		final String companyName = inObject.optString(DeviceInfoKey.COMPANY_NAME, null);
		final String machineName = inObject.optString(DeviceInfoKey.MACHINE_NAME, null);
		final String terminalUa = inObject.optString(DeviceInfoKey.TERMINAL_UA, null);
		final String screenRes = inObject.optString(DeviceInfoKey.SCREEN_RES, null);
		final String imei = inObject.optString(DeviceInfoKey.IMEI, null);
		final String imsi = inObject.optString(DeviceInfoKey.IMSI, null);
		final String udid = inObject.optString(DeviceInfoKey.UDID, null);
		final String phoneNumber = inObject.optString(DeviceInfoKey.PHONE_NUMBER, null);
		final Platform platform = Platform.valueOf(inObject.optString(DeviceInfoKey.PLATFORM, Platform.none.toString()));
		final String firewareVersion = inObject.optString(DeviceInfoKey.FIREWARE_VERSION, null);
		final String softwareVersion = inObject.optString(DeviceInfoKey.SOFTWARE_VERSION, null);
		final String softwareBuild = inObject.optString(DeviceInfoKey.SOFTWARE_BUILD, null);
		DeviceInfo deviceInfo = deviceInfoDAOW.getDeviceInfoByMachineIdAndPlatform(machineId, platform);
		if (deviceInfo == null) {
			deviceInfo = deviceInfoFactory.newInstance(machineId, macId, token, companyName, machineName, terminalUa, screenRes, imei, imsi, udid, phoneNumber, platform, firewareVersion, softwareVersion, softwareBuild);
			deviceInfoDAOW.save(deviceInfo);
		} else {
			deviceInfo.modifyProperties(macId, token, companyName, machineName, terminalUa, screenRes, imei, imsi, udid, phoneNumber, firewareVersion, softwareVersion, softwareBuild);
		}
		long endTime = System.currentTimeMillis();
		long costTime = endTime - beginTime;
		LOGGER.info("uploadDeviceInfo cost>>>" + costTime);
	}

	public void setUpload_deviceinfo_in(String upload_deviceinfo_in) {
		this.deviceInfoIn = upload_deviceinfo_in;
	}

	public void setDeviceInfoDAOW(DeviceInfoDAO deviceInfoDAOW) {
		this.deviceInfoDAOW = deviceInfoDAOW;
	}

	public void setDeviceInfoFactory(DeviceInfoFactory deviceInfoFactory) {
		this.deviceInfoFactory = deviceInfoFactory;
	}

}
