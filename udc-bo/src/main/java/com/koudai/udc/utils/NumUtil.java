package com.koudai.udc.utils;

import java.text.NumberFormat;

public final class NumUtil {

	public static String getPercentage(int srcNum, int desNum) {
		NumberFormat numberFormat = NumberFormat.getPercentInstance();
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format((double) (srcNum - desNum) / desNum);
	}

	public static String getAverage(int srcNum, int desNum) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format((double) srcNum / desNum);
	}

}
