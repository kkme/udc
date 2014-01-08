package com.koudai.udc.statis.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateFormatter {

	private static final Logger LOGGER = Logger.getLogger(DateFormatter.class);

	public static final String COMMON_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

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

	public Date parse(String unformattedDateStr) {
		try {
			return simpleDateFormat.parse(unformattedDateStr);
		} catch (ParseException e) {
			LOGGER.error(e);
			return null;
		}
	}
}
