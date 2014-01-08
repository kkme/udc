package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class UserAlgorithmInfo implements Serializable {

	private static final long serialVersionUID = 6914874060763882615L;

	private Long id;

	private String userId;

	private int algorithmVersion;

	private String sessionVersion;

	private String userStyle;

	private Date updateTime;

	public UserAlgorithmInfo() {
	}

	public UserAlgorithmInfo(String userId, int algorithmVersion, String sessionVersion, String userStyle) {
		this.userId = userId;
		this.algorithmVersion = algorithmVersion;
		this.sessionVersion = sessionVersion;
		this.userStyle = userStyle;
		this.updateTime = new Date();
	}

	public void modifyProperties(int algorithmVersion, String sessionVersion, String userStyle) {
		this.algorithmVersion = algorithmVersion;
		this.sessionVersion = sessionVersion;
		this.userStyle = userStyle;
		this.updateTime = new Date();
	}

	public String getFormatAlgorithmVersion() {
		if (this.algorithmVersion == 0) {
			return "";
		}
		return String.valueOf(this.algorithmVersion);
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

	public int getAlgorithmVersion() {
		return algorithmVersion;
	}

	public void setAlgorithmVersion(int algorithmVersion) {
		this.algorithmVersion = algorithmVersion;
	}

	public String getSessionVersion() {
		return sessionVersion;
	}

	public void setSessionVersion(String sessionVersion) {
		this.sessionVersion = sessionVersion;
	}

	public String getUserStyle() {
		return userStyle;
	}

	public void setUserStyle(String userStyle) {
		this.userStyle = userStyle;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
