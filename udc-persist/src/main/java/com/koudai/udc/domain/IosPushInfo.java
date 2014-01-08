package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

public class IosPushInfo implements Serializable {

	private static final long serialVersionUID = -2289757916092758474L;
	private static final Logger LOGGER = Logger.getLogger(IosPushInfo.class);

	private Long id;
	private String machineId;
	private String firstProductName;
	private String productIds;
	private int infoType;
	private String token;
	private int state;
	protected Platform platform;
	private Date createTime;

	private String userId;
	private Date startTime;
	private Date endTime;
	private Date pushTime;

	public IosPushInfo() {
	}

	public IosPushInfo(String machineId, String firstProductName, String productIds, int infoType, String token, Platform platform) {
		this.machineId = machineId;
		this.firstProductName = firstProductName;
		this.productIds = productIds;
		this.infoType = infoType;
		this.token = token;
		this.platform = platform;
		this.state = PushState.NO.getCode();
		this.createTime = new Date();
	}

	public void push() {
		if (PushState.YES.getCode() == this.state) {
			LOGGER.error("Ios push info has already pushed");
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
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

}
