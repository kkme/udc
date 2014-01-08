package com.koudai.udc.domain;

import java.io.Serializable;

import com.koudai.udc.domain.exception.InvalidStatusException;

public class TaobaoBindingInfo implements Serializable {

	private static final long serialVersionUID = -1173033974008005114L;

	private Long id;

	private String userId;

	private String bindId;

	private boolean bound;

	public TaobaoBindingInfo() {
	}

	public TaobaoBindingInfo(String userId, String bindId) {
		this.userId = userId;
		this.bindId = bindId;
		this.bound = true;
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

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public boolean isBound() {
		return bound;
	}

	public void setBound(boolean bound) {
		this.bound = bound;
	}

	public void bind(String bindId) throws InvalidStatusException {
		if (this.isBound()) {
			throw new InvalidStatusException("User < " + this.userId + " > has bound taobao user < " + this.bindId + " >");
		}
		this.bindId = bindId;
		this.bound = true;
	}

	public void unbind() {
		if (this.isBound()) {
			this.bound = false;
		}
	}

}
