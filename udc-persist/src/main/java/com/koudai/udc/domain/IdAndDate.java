package com.koudai.udc.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdAndDate implements Serializable {

	private static final long serialVersionUID = 7715962291971485898L;

	private String id;

	private Date date;

	public IdAndDate() {
	}

	public IdAndDate(String id, Date date) {
		this.id = id;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFormatterDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public long getFormatterTimestamp() {
		return date.getTime();
	}

}
