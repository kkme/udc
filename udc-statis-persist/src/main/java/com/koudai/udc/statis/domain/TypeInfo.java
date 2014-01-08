package com.koudai.udc.statis.domain;

import java.io.Serializable;

public class TypeInfo implements Serializable {

	private static final long serialVersionUID = -5304629854186629627L;

	private Long id;

	private String typeId;

	private String typeName;

	public TypeInfo() {
	}

	public TypeInfo(String typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
