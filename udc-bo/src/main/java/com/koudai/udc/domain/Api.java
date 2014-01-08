package com.koudai.udc.domain;

import java.io.Serializable;

import com.koudai.udc.utils.NumUtil;
import com.koudai.udc.utils.S;

public class Api implements Serializable {

	private static final long serialVersionUID = 7407234850018095034L;

	private String name;

	private int count;

	private int totalTime;

	public Api(String name, int totalTime) {
		this.name = name;
		this.count = 1;
		this.totalTime = totalTime;
	}

	public Api(String name, int count, int totalTime) {
		this.name = name;
		this.count = count;
		this.totalTime = totalTime;
	}

	public void add(int amount) {
		this.count++;
		this.totalTime = totalTime + amount;
	}

	public String getAverage() {
		return NumUtil.getAverage(totalTime, count);
	}

	public String getPercentage(int beforeCount) {
		if (beforeCount == 0) {
			return S.ZERO;
		}
		return NumUtil.getPercentage(count, beforeCount);
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public String getFormatterTotalTime() {
		return new StringBuffer(String.valueOf(totalTime)).append("ms").toString();
	}

}
