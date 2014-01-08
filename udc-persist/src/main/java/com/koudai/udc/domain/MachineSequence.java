package com.koudai.udc.domain;

import java.io.Serializable;

public class MachineSequence implements Serializable {

	private static final long serialVersionUID = 1636083122723456137L;

	private Long id;

	private String machineId;

	public MachineSequence() {
	}

	public MachineSequence(String machineId) {
		this.machineId = machineId;
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

}
