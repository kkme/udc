package com.koudai.udc.statis.dao.impl;

import java.io.Serializable;

public class PositionLimit implements Serializable {

	private static final long serialVersionUID = 5525064824257693503L;

	private int startPos;

	private int maxNum;

	public PositionLimit() {
	}

	public PositionLimit(int startPos, int maxNum) {
		this.startPos = startPos;
		this.maxNum = maxNum;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

}
