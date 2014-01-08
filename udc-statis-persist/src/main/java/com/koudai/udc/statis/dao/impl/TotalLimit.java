package com.koudai.udc.statis.dao.impl;

import java.io.Serializable;

public class TotalLimit implements Serializable {

	private static final long serialVersionUID = -344756051631300390L;

	private String userId;

	private String startTime;

	private String endTime;

	public TotalLimit() {
	}

	public TotalLimit(String userId, String startTime, String endTime) {
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
