package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

public class AndroidPushInfo implements Serializable {

	private static final long serialVersionUID = 8017933807025664113L;
	private static final Logger LOGGER = Logger.getLogger(AndroidPushInfo.class);

	private Long id;
	private String machineId;
	private String firstProductName;
	private String productIds;
	private int infoType;
	private int state;
	private Date createTime;

	private String userId;
	private Date startTime;
	private Date endTime;
	private Date pushTime;

	private int pushType;
	private String manualId;

	public AndroidPushInfo() {
	}

	public AndroidPushInfo(String machineId, String firstProductName, String productIds, int infoType) {
		this.machineId = machineId;
		this.firstProductName = firstProductName;
		this.productIds = productIds;
		this.infoType = infoType;
		this.state = PushState.NO.getCode();
		this.createTime = new Date();
		this.pushType = PushType.AUTO.getCode();
	}

	public AndroidPushInfo(String machineId, String firstProductName, String productIds, int infoType, int pushType, String manualId) {
		this.machineId = machineId;
		this.firstProductName = firstProductName;
		this.productIds = productIds;
		this.infoType = infoType;
		this.state = PushState.NO.getCode();
		this.createTime = new Date();
		this.pushType = pushType;
		this.manualId = manualId;
	}

	public void push() {
		if (PushState.YES.getCode() == this.state) {
			LOGGER.error("Android push info has already pushed");
			return;
		}
		this.state = PushState.YES.getCode();
		this.pushTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getFirstProductName() {
		return firstProductName;
	}

	public void setFirstProductName(String firstProductName) {
		this.firstProductName = firstProductName;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public int getInfoType() {
		return infoType;
	}

	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public int getPushType() {
		return pushType;
	}

	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

}
