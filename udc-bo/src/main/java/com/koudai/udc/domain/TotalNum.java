package com.koudai.udc.domain;

import java.io.Serializable;

public class TotalNum implements Serializable {

	private static final long serialVersionUID = 3013580832312722782L;

	private int taobaoNum;

	private int sinaNum;

	private int qqNum;

	private int realNum;

	private int anonyNum;

	private int allNum;

	public TotalNum(int taobaoNum, int sinaNum, int qqNum, int realNum, int anonyNum, int allNum) {
		this.taobaoNum = taobaoNum;
		this.sinaNum = sinaNum;
		this.qqNum = qqNum;
		this.realNum = realNum;
		this.anonyNum = anonyNum;
		this.allNum = allNum;
	}

	public int getTaobaoNum() {
		return taobaoNum;
	}

	public int getSinaNum() {
		return sinaNum;
	}

	public int getQqNum() {
		return qqNum;
	}

	public int getRealNum() {
		return realNum;
	}

	public int getAnonyNum() {
		return anonyNum;
	}

	public int getAllNum() {
		return allNum;
	}

}
