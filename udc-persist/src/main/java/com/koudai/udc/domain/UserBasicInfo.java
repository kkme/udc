package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class UserBasicInfo implements Serializable {

	private static final long serialVersionUID = 3373970637760050541L;

	private Long id;
	private String email;
	private String thirdId;
	private String phoneNumber;
	private String koudaiId;
	private String name;
	private String machineId;
	private String password;
	private char gender;
	private Date birthday;
	private String location;
	private Date registTime;
	private String qq;
	private String msn;
	private String introduction;
	private Date lastLoginTime;
	private boolean enabled;

	public UserBasicInfo() {
	}

	public UserBasicInfo(String email, String thirdId, String phoneNumber, String koudaiId, String name, String machineId, String password, char gender, Date birthday, String location, Date registTime, String qq, String msn, String introduction, Date lastLoginTime) {
		this.email = email;
		this.thirdId = thirdId;
		this.phoneNumber = phoneNumber;
		this.koudaiId = koudaiId;
		this.name = name;
		this.machineId = machineId;
		this.password = password;
		this.gender = gender;
		this.birthday = birthday;
		this.location = location;
		this.registTime = registTime;
		this.qq = qq;
		this.msn = msn;
		this.introduction = introduction;
		this.lastLoginTime = lastLoginTime;
		this.enabled = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getKoudaiId() {
		return koudaiId;
	}

	public void setKoudaiId(String koudaiId) {
		this.koudaiId = koudaiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
