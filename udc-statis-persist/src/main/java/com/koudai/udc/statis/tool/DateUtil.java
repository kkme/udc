package com.koudai.udc.statis.tool;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

	private static final int THIS_MONTH = 12;

	public static final int THIS_YEAR = 2005;

	public static final int LAST_MIILISECOND = 999;

	public static final int LAST_SECOND = 59;

	public static final int LAST_MINUTE = 59;

	public static final int LAST_HOUR = 23;

	private DateUtil() {
	}

	public static Date add(Date givenDate, int field, int increment) {
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(givenDate);
		newDate.add(field, increment);
		return newDate.getTime();
	}

	public static Date createDate(int year, int month, int day) {
		return createDate(year, month, day, 0, 0, 0);
	}

	public static Date createDate(int year, int month, int day, int hour, int minute, int second) {
		return createDateToMillisecondPrecision(year, month, day, hour, minute, second, 0).getTime();
	}

	public static Date createDate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
		return createDateToMillisecondPrecision(year, month, day, hour, minute, second, millisecond).getTime();
	}

	private static Calendar createDateToMillisecondPrecision(int year, int month, int day, int hour, int minute, int second, int millisecond) {
		Calendar newDate = Calendar.getInstance();
		setNewDateProperties(year, month, day, hour, minute, second, millisecond, newDate);
		return newDate;
	}

	private static void setNewDateProperties(int year, int month, int day, int hour, int minute, int second, int millisecond, Calendar newDate) {
		newDate.set(Calendar.YEAR, year);
		newDate.set(Calendar.MONTH, month);
		newDate.set(Calendar.DAY_OF_MONTH, day);
		newDate.set(Calendar.HOUR_OF_DAY, hour);
		newDate.set(Calendar.MINUTE, minute);
		newDate.set(Calendar.SECOND, second);
		newDate.set(Calendar.MILLISECOND, millisecond);
	}

	public static Date createCurrentDateToMinuitePrecision() {
		Calendar newDate = Calendar.getInstance();
		newDate.set(Calendar.SECOND, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		return newDate.getTime();
	}

	public static Date createDate(int month, int day) {
		return DateUtil.createDate(DateUtil.THIS_YEAR, month, day);
	}

	public static Date createDate(int day) {
		return createDate(THIS_MONTH, day);
	}

	public static Date setTimeOnDate(Date baseDate, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	public static Date setTimeOnDate(Date baseDate, int hour, int minute, int second, int millisecond) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal.getTime();
	}

	public static Date yesterday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	public static Date yesterdayWithoutTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		Date yesterday = cal.getTime();
		DateFormatter df = new DateFormatter(DateFormatter.COMMON_DATE_FORMAT);
		return df.parse(df.format(yesterday));
	}

	public static Date dayBeforeYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -2);
		return cal.getTime();
	}

	public static Date tomorrow(Date certainDate) {
		Calendar cal = Calendar.getInstance();
		if (certainDate == null) {
			cal.setTime(new Date());
		} else {
			cal.setTime(certainDate);
		}
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static Date previousDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	public static Date nextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	public static int currrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static int currrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

}
