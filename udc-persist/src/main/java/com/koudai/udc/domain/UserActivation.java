package com.koudai.udc.domain;

import java.io.Serializable;

public class UserActivation implements Serializable {

	private static final long serialVersionUID = 16877510359511238L;
	private Long id;
	private String userId;
	private boolean yellowDiamond;
	private boolean iosPay;
	private boolean alreadyTest;

	public UserActivation() {
	}

	public UserActivation(String userId) {
		this.userId = userId;
		this.yellowDiamond = false;
		this.iosPay = true;
		this.alreadyTest = false;
	}

	public UserActivation(String userId, boolean yellowDiamond) {
		this.userId = userId;
		this.yellowDiamond = yellowDiamond;
		this.iosPay = false;
		this.alreadyTest = false;
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

	public boolean isYellowDiamond() {
		return yellowDiamond;
	}

	public void setYellowDiamond(boolean yellowDiamond) {
		this.yellowDiamond = yellowDiamond;
	}

	public boolean isIosPay() {
		return iosPay;
	}

	public void setIosPay(boolean iosPay) {
		this.iosPay = iosPay;
	}

	public boolean isAlreadyTest() {
		return alreadyTest;
	}

	public void setAlreadyTest(boolean alreadyTest) {
		this.alreadyTest = alreadyTest;
	}

	public boolean isActive() {
		return iosPay || yellowDiamond;
	}

}
