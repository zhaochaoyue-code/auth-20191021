package com.cmcc.v2x.dso.authcs.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateWorker {
	public static final long EVERY_DAY = 24*60*60*1000L ;
	/**
	 * This will create a date object represented by the milliseconds provided.
	 * @param millis The date in milliseconds.
	 * @return The date.
	 */
	public static Date createDate(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis( millis );
		return calendar.getTime();
	}

	/**
	 * This will create a calendar object from the date provided.
	 * @param date The date.
	 * @return The Calendar representing that date.
	 */
	public static Calendar createCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( date );
		return calendar;
	}

	/**
	 * This will set the time for the given date to zero (midnight).
	 * @param date The date.
	 * @return The date with the time set to midnight.
	 */
	public static Date startOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Get the Date representing the end of the day.
	 * 
	 * @param date The date.
	 * @return The end of the day.
	 */
	public static Date endOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * Add the specified number of days to the date specified. If the number is
	 * negative, the days will be subtracted.
	 * @param date The date.
	 * @param numdays The number of days to add.
	 * @return The new date.
	 */
	public static Date addDays(Date date, int numdays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, numdays);
		return calendar.getTime();
	}

	/**
	 * Add the specified number of weeks to the date specified. If the number is
	 * negative, the weeks will be subtracted.
	 * @param date The date.
	 * @param numWeeks The number of weeks to add.
	 * @return The new date.
	 */
	public static Date addWeeks(Date date, int numWeeks) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_YEAR, numWeeks);
		return calendar.getTime();
	}

	/**
	 * Add the specified number of months to the date specified. If the number is
	 * negative, the months will be subtracted.
	 * @param date The date.
	 * @param numMonths The number of months to add.
	 * @return The new date.
	 */
	public static Date addMonths(Date date, int numMonths) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, numMonths);
		return calendar.getTime();
	}

	/**
	 * Add the specified number of years to the date specified. If the number is
	 * negative, the years will be subtracted.
	 * @param date The date.
	 * @param numYears The number of years to add.
	 * @return The new date.
	 */
	public static Date addYears(Date date, int numYears) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, numYears);
		return calendar.getTime();
	}

	/**
	 * Format a date object provided to return String represenation with Date
	 * and Time information
	 * 
	 * @param date The date & time to format.
	 * @return The formatted date in "yyyy-MM-dd HH:mm:ss" format.
	 */
	public static String formatDateTime(Date date) {
		if( date == null ) return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	/**
	 * This will evaluate the date (assumed to be in the format yyyy-MM-dd).
	 * @param text The text version of the date.
	 * @return The date.
	 * @throws IllegalArgumentException if the text is an invalid format.
	 */
	public static Date evalDate(String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse( text );
		} catch (ParseException e) {
			return null ;
			//throw new IllegalArgumentException("���ڱ�����yyyy-MM-dd�ĸ�ʽ");
		}
	}

	/**
	 * This will evaluate the date/time (assumed to be in the format yyyy-MM-dd HH:mm:ss).
	 * @param text The text version of the date.
	 * @return The date.
	 * @throws IllegalArgumentException if the text is an invalid format.
	 */
	public static Date evalDateTime(String text) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse( text );
		} catch (ParseException e) {
			throw new IllegalArgumentException("����ʱ����Ҫ��yyyy-MM-dd HH:mm:ss�ĸ�ʽ");
		}
	}

	/**
	 * Format a date object provided to return String represenation of with only
	 * Date part of information in big-endian format.
	 * 
	 * @param date The date to format.
	 * @return The formatted date in "yyyy-MM-dd" format.
	 */
	public static String formatDate(Date date) {
		if( date == null ) return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	/**
	 * Format a date object provided to return String represenation of with only
	 * Time part of information
	 * 
	 * @param date the time to format.
	 * @return String The formatted time in "HH:mm:ss" format.
	 */
	public static String formatTime(Date date) {
		if( date == null ) return "";
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		return formatter.format(date);
	}

	/**
	 * This will format the specified Calendar object as a date/time
	 * @param calendar The calendar object.
	 * @return The text in yyyy-MM-dd HH:mm:ss format.
	 */
	public static String formatDateTime(Calendar calendar) {
		if( calendar == null )
			return null;
		else
			return formatDateTime( calendar.getTime() );
	}

	/**
	 * This is a big-endian date & time format that is suitable for filenames.
	 * There are no colons or other invalid characters in the result.
	 * 
	 * @param date The date & time to format.
	 * @return The formatted datetime in "yyyyMMdd_HHmmss" format.
	 */
	public static String formatDateTimeStamp(Date date) {
		if( date == null ) return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return formatter.format(date);
	}

	/**
	 * Get tomorrow.
	 * 
	 * @return The Date representing this time tomorrow.
	 */
	public static Date today() {
		return new Date();
	}

	/**
	 * Get tomorrow.
	 * 
	 * @return The Date representing this time tomorrow.
	 */
	public static Date tomorrow() {
		return DateWorker.addDays(new Date(), 1);
	}

	/**
	 * Get yesterday.
	 * 
	 * @return The Date representing this time yesterday.
	 */
	public static Date yesterday() {
		return DateWorker.addDays(new Date(), -1);
	}

	/**
	 * Get only the hour and minute of the calendar.
	 * 
	 * @param calendar The input calendar.
	 * @return The time.
	 */
	public static Calendar getTimeOnly(Calendar calendar) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set( Calendar.HOUR_OF_DAY , calendar.get( Calendar.HOUR_OF_DAY ) );
		cal.set( Calendar.MINUTE , calendar.get( Calendar.MINUTE ) );
		cal.set( Calendar.SECOND , calendar.get( Calendar.SECOND ) );
		return cal;
	}

	/**
	 * Get only the date component of the calendar.
	 * 
	 * @param calendar The calendar.
	 * @return The date component.
	 */
	public static Calendar getDateOnly(Calendar calendar) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set( Calendar.YEAR , calendar.get( Calendar.YEAR ) );
		cal.set( Calendar.DAY_OF_YEAR , calendar.get( Calendar.DAY_OF_YEAR ) );
		return cal;
	}

	/**
	 * Get only the date/time component of the calendar.
	 * 
	 * @param calendar The calendar.
	 * @return The date/time component.
	 */
	public static Calendar getDateTimeOnly(Calendar calendar) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set( Calendar.YEAR , calendar.get( Calendar.YEAR ) );
		cal.set( Calendar.DAY_OF_YEAR , calendar.get( Calendar.DAY_OF_YEAR ) );
		cal.set( Calendar.HOUR_OF_DAY , calendar.get( Calendar.HOUR_OF_DAY ) );
		cal.set( Calendar.MINUTE , calendar.get( Calendar.MINUTE ) );
		return cal;
	}

	/**
	 * This will return true if both calendars are on the same day.
	 * 
	 * @param calendar1 The first calendar.
	 * @param calendar2 The second calendar.
	 * @return True if they are on the same day.
	 */
	public static boolean compareDay(Calendar calendar1,Calendar calendar2) {
		if( calendar1.get( Calendar.YEAR ) != calendar2.get( Calendar.YEAR ) ) return false;
		if( calendar1.get( Calendar.DAY_OF_YEAR ) != calendar2.get( Calendar.DAY_OF_YEAR ) ) return false;
		return true;
	}

	/**
	 * This will return true if both calendars are on the same day.
	 * 
	 * @param date1 The first Date
	 * @param date2 The second Date
	 * @return True if they are on the same day.
	 */
	public static boolean compareDay(Date date1, Date date2) {
		Calendar calendar1 = createCalendar(date1);
		Calendar calendar2 = createCalendar(date2);
		if (calendar1.get(Calendar.YEAR) != calendar2.get(Calendar.YEAR))
			return false;
		if (calendar1.get(Calendar.DAY_OF_YEAR) != calendar2
				.get(Calendar.DAY_OF_YEAR))
			return false;
		return true;
	}
	/**
	 * This will return true if both calendars are on the same day.
	 * 
	 * @param date1 The first String
	 * @param date2 The second String
	 * @return True if they are on the same day.
	 */
	public static boolean compareDay(String date1, String date2) {
		Calendar calendar1 = createCalendar(evalDate(date1));
		Calendar calendar2 = createCalendar(evalDate(date2));
		if (calendar1.get(Calendar.YEAR) != calendar2.get(Calendar.YEAR))
			return false;
		if (calendar1.get(Calendar.DAY_OF_YEAR) != calendar2
				.get(Calendar.DAY_OF_YEAR))
			return false;
		return true;
	}

	/**
	 * Given a date it will return the 1st date of the 
	 * previous month
	 * @param date
	 * @return date yyyy/mm/01
	 */
	public static Date getPreviousMonthStartDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);		
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime(); 
	}

	/**
	 * Given a date it will return the 1st date of the 
	 * current month
	 * @param date
	 * @return date yyyy/mm/01
	 */
	public static Date getCurrentMonthStartDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));		
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime(); 
	}


	/**
	 * Given a date it will return the last date of the 
	 * previous month
	 * @param date
	 * @return date yyyy/mm/ 28,30 or 31
	 */

	public static Date getPreviousMonthLastDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);		
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime(); 
	}	

	/**
	 * Check the given date is saturday.
	 * @param date
	 * @return true or false
	 */
	public static boolean isDateSaturday(Date date) {
		boolean returnVal = false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY) {
			returnVal = true;
		} else {
			returnVal = false;
		}
		return returnVal;
	}

	/**
	 * date1�����Ƿ���date2֮ǰ
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean before(Date date1, Date date2) {
		Calendar calendar1 = createCalendar(date1);
		Calendar calendar2 = createCalendar(date2);
		return DateWorker.getDateOnly(calendar1).before(
				DateWorker.getDateOnly(calendar2));
	}

	/**
	 * date1�����Ƿ���date2֮ǰ
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean before(String date1, String date2) {
		Calendar calendar1 = createCalendar(evalDate(date1));
		Calendar calendar2 = createCalendar(evalDate(date2));
		return DateWorker.getDateOnly(calendar1).before(
				DateWorker.getDateOnly(calendar2));
	}

	/**
	 * ��ȡĳһ��Ŀ�ʼʱ��
	 * @param days
	 * @return
	 */
	public static Date getSomeDayStartDateTime(int days) {
		return DateWorker.addDays(DateWorker.startOfDay(new Date()), days);
	}

	/**
	 * ��ȡĳһ��Ľ���ʱ��
	 * @param days
	 * @return
	 */
	public static Date getSomeDayEndDateTime(int days) {
		return DateWorker.addDays(DateWorker.endOfDay(new Date()), days);
	}

	/**
	 * ��ȡĳ���µ�����
	 * @return
	 */
	public static int getMonthDays(Date date){
		Calendar calendar = Calendar.getInstance() ;
		calendar.setTime(date) ;
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH) ;
	}

	public static Date getLastDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance() ;
		calendar.setTime(date) ;
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) ;
		return calendar.getTime() ;
	}

	public static int getGivenPeriodDays(Date startDate,Date endDate){
		return (int) ((endDate.getTime() - startDate.getTime())/EVERY_DAY) ;
	}

	public static void main(String args[]) {
		Date date = getLastDayOfMonth(new Date()) ;
		System.out.println(date);
		System.out.println(subDay(date, 40));
	}

	public static Date subDay(Date date, int days) {
		Calendar calendar = Calendar.getInstance() ;
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -days) ;
		return calendar.getTime() ;
	}
}
