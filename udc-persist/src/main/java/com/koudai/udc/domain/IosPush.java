package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class IosPush implements Serializable {

	private static final long serialVersionUID = -2289757916092758474L;

	private Long id;

	private String uuid;

	private String token;

	private Date createTime;

	public IosPush() {
	}

	public IosPush(String uuid, String token) {
		this.uuid = uuid;
		this.token = token;
		this.createTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
