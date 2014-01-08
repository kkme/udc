package com.koudai.udc.statis.utils;

import java.text.NumberFormat;

public final class NumUtil {

	public static String getPercentage(int srcNum, int desNum) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		if (desNum == 0 || srcNum == 0) {
			return S.ZERO;
		}
		return numberFormat.format((double) srcNum / desNum);
	}

}
