package com.koudai.udc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CurrencyUtil {

	public static BigDecimal transfer(String money) {
		BigDecimal temp = new BigDecimal(money);
		return temp.setScale(2, RoundingMode.HALF_DOWN);
	}

}
