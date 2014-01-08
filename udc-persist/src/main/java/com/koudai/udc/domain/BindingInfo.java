package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class BindingInfo implements Serializable {

	private static final long serialVersionUID = 7735249984550169209L;

	private Long id;
	private String machineId;
	private String userId;
	private Date bindingTime;

	public BindingInfo() {
	}

	public BindingInfo(String machineId, String userId) {
		this.machineId = machineId;
		this.userId = userId;
		this.bindingTime = new Date();
	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getBindingTime() {
		return bindingTime;
	}

	public void setBindingTime(Date bindingTime) {
		this.bindingTime = bindingTime;
	}

}
