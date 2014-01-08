package com.koudai.udc.statis.dao.impl;

import java.io.Serializable;

public class PageLimit implements Serializable {

	private static final long serialVersionUID = 8054066037774049487L;

	private String userId;

	private String startTime;

	private String endTime;

	private int startPos;

	private int maxNum;

	public PageLimit() {
	}

	public PageLimit(String userId, String startTime, String endTime, int startPos, int maxNum) {
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startPos = startPos;
		this.maxNum = maxNum;
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

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

}
