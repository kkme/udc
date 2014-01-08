package com.koudai.udc.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtil {

	private static final int FIVE_MINUTE_BEFORE = -5;

	private static final int LARGE_YEAR = 9999;

	private static final int THIS_MONTH = 12;

	static public final int THIS_YEAR = 2005;

	public static final int LAST_MIILISECOND = 999;

	public static final int LAST_SECOND = 59;

	public static final int LAST_MINUTE = 59;

	public static final int LAST_HOUR = 23;

	public static final String FORMATTER = "yyyy-MM-dd";

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
		newDate.set(Calendar.YEAR, year);
		newDate.set(Calendar.MONTH, month);
		newDate.set(Calendar.DAY_OF_MONTH, day);
		newDate.set(Calendar.HOUR_OF_DAY, hour);
		newDate.set(Calendar.MINUTE, minute);
		newDate.set(Calendar.SECOND, second);
		newDate.set(Calendar.MILLISECOND, millisecond);
		return newDate;
	}

	public static Date createCurrentDateToMinuitePrecision() {
		Calendar newDate = Calendar.getInstance();
		newDate.set(Calendar.SECOND, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		return newDate.getTime();
	}

	public static boolean isNotInTradeTimePeriod() {
		Date SET_TIME_END_DATE = setTimeOnDate(new Date(), 8, 30, 0, 0);
		Date SET_TIME_START_DATE = setTimeOnDate(new Date(), 2, 0, 0, 0);
		boolean after = today().after(SET_TIME_START_DATE);
		boolean before = today().before(SET_TIME_END_DATE);
		if (before && after) {
			return true;
		} else {
			return false;
		}
	}

	public static Date createDate(int month, int day) {
		return DateUtil.createDate(DateUtil.THIS_YEAR, month, day);
	}

	public static Date createDate(int day) {
		return createDate(THIS_MONTH, day);
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

	public static String todayFormated(String formate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 0);
		return getDateString(cal.getTime(), formate);
	}

	public static String yesterdayFormated(String formate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		return getDateString(cal.getTime(), formate);
	}

	public static String getDateString(Date date, String formate) {
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(date);
	}

	public static Date today() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
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

	public static Date previousMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, FIVE_MINUTE_BEFORE);
		return cal.getTime();
	}

	public static Date previousDays(Date date, int daysBefore) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -daysBefore);
		return cal.getTime();
	}

	public static Date previousMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	public static Date nextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	public static int currrentWeek() {
		return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
	}

	public static int currrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static int currrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	public static int currrentHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	public static Date longFuture() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, LARGE_YEAR);
		return calendar.getTime();
	}

	public static long getTimeByCurrentDayBegin() {
		Date endDate = setTimeOnDate(new Date(), 0, 0, 0, 0);
		return endDate.getTime();
	}

	public static long getTimeByPreviousDayBegin(int days) {
		Date previousDays = previousDays(new Date(), days);
		Date beginDate = setTimeOnDate(previousDays, 0, 0, 0, 0);
		return beginDate.getTime();
	}

	public static int lastYear() {
		return Calendar.getInstance().get(Calendar.YEAR) - 1;
	}

	public static int yearOfLastWeek() {
		final int currrentWeek = currrentWeek();
		if (currrentWeek == 1) {
			return lastYear();
		}
		return currrentYear();
	}

	public static int lastWeek() {
		int currrentWeek = currrentWeek();
		if (currrentWeek == 1) {
			Calendar cal = new GregorianCalendar();
			cal.set(lastYear(), Calendar.DECEMBER, 31, 23, 59, 59);
			Date date = cal.getTime();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setMinimalDaysInFirstWeek(7);
			cal.setTime(date);
			return cal.get(Calendar.WEEK_OF_YEAR);
		}
		return currrentWeek - 1;
	}
}
