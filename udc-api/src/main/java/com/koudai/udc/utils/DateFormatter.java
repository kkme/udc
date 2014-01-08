package com.koudai.udc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

	public static final String COMMON_DATE_FORMAT = "yyyy-MM-dd";

	public static final String SHORT_DATE_FORMAT = "yy-MM-dd HH:mm";

	public static final String SMALL_DATE_FORMAT = "MM-dd HH:mm";

	public static final String ABC_ORDER_DATE_FORMAT = "yyyy/MM/dd";

	public static final String ABC_ORDER_TIME_FORMAT = "HH:mm:ss";

	public static final String LOTTERY_PRINTING_TIME_FORMAT = "HH_mm_ss";

	public static final String CHECK_WALLET_FORMAT = "yyyyMMdd";

	public static final String TEN_PAY_DATE_STRING_FORMAT = "yyMMdd";

	public static final String ICBC_DATE_STRING_FORMAT = "yyyyMMddHHmmss";

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private SimpleDateFormat simpleDateFormat;

	public DateFormatter() {
		this(DEFAULT_DATE_FORMAT);
	}

	public DateFormatter(String formatString) {
		simpleDateFormat = new SimpleDateFormat(formatString);
	}

	public String format(Date unformattedDate) {
		return simpleDateFormat.format(unformattedDate);
	}

	public Date parse(String strDate) {
		try {
			return simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
	}
}
