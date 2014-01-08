package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Date;

public class UserTaobaoInfo implements Serializable {

	private static final long serialVersionUID = -7040702090641124982L;

	private Long id;

	private String userId;

	private String token;

	private int expire;

	private Date tokenCreateTime;

	private String refreshToken;

	private int refreshExpire;

	private Date refreshTokenCreateTime;

	private String taobaoUserId;

	private String sellerSid;

	private LoginPlatform platform;

	public UserTaobaoInfo() {
	}

	public UserTaobaoInfo(String userId, String taobaoUserId, String token, int expire, String refreshToken, int refreshExpire, LoginPlatform platform) {
		final Date currentDate = new Date();
		this.userId = userId;
		this.taobaoUserId = taobaoUserId;
		this.token = token;
		this.expire = expire;
		this.tokenCreateTime = currentDate;
		this.refreshToken = refreshToken;
		this.refreshExpire = refreshExpire;
		this.refreshTokenCreateTime = currentDate;
		this.platform = platform;
	}

	public UserTaobaoInfo(String userId, LoginPlatform platform) {
		this.userId = userId;
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

	public Date getTokenCreateTime() {
		return tokenCreateTime;
	}

	public void setTokenCreateTime(Date tokenCreateTime) {
		this.tokenCreateTime = tokenCreateTime;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public int getRefreshExpire() {
		return refreshExpire;
	}

	public void setRefreshExpire(int refreshExpire) {
		this.refreshExpire = refreshExpire;
	}

	public Date getRefreshTokenCreateTime() {
		return refreshTokenCreateTime;
	}

	public void setRefreshTokenCreateTime(Date refreshTokenCreateTime) {
		this.refreshTokenCreateTime = refreshTokenCreateTime;
	}

	public String getTaobaoUserId() {
		return taobaoUserId;
	}

	public void setTaobaoUserId(String taobaoUserId) {
		this.taobaoUserId = taobaoUserId;
	}

	public String getSellerSid() {
		return sellerSid;
	}

	public void setSellerSid(String sellerSid) {
		this.sellerSid = sellerSid;
	}

	public LoginPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(LoginPlatform platform) {
		this.platform = platform;
	}

}
