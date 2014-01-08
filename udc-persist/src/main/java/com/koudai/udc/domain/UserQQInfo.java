package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class UserQQInfo implements Serializable {

	private static final long serialVersionUID = 4190426459693394590L;

	private Long id;

	private String userId;

	private String token;

	private int expire;

	private String nick;

	private Date createTime;

	private LoginPlatform platform;

	public UserQQInfo() {
	}

	public UserQQInfo(String userId, String token, int expire, String nick, LoginPlatform platform) {
		this.userId = userId;
		this.token = token;
		this.expire = expire;
		this.nick = nick;
		this.createTime = new Date();
		this.platform = platform;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public LoginPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(LoginPlatform platform) {
		this.platform = platform;
	}

}
