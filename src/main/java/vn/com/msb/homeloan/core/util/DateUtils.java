package vn.com.msb.homeloan.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {

  private DateUtils() {
  }

  public static final String DATE_FORMAT = "dd/MM/yyyy";
  public static final String RFC3339_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
  public static final String T24_DATE_FORMAT = "yyyy-MM-dd";
  public static final String REQ_TIME_FORMAT = "dd-MM-yyyy hh:mm:ss";
  public static final String REQUEST_TIME_FORMAT = "yyyyMMddHHmmss";

  public static String convertToRFC3339(Date date) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat format = new SimpleDateFormat(RFC3339_DATE_FORMAT);
    return format.format(date);
  }

  public static String convertToT24Format(Date date) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat format = new SimpleDateFormat(T24_DATE_FORMAT);
    return format.format(date);
  }

  public static String convertToSimpleFormat(Date date) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    return format.format(date);
  }

  public static Date convertToDateFormat(String date) throws ParseException {
    if (date == null) {
      return null;
    }
    return new SimpleDateFormat(DateUtils.DATE_FORMAT).parse(date);
  }

  public static String convertToSimpleFormat(Date date, String format) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.format(date);
  }

  public static LocalDate stringToLocalDate(String dateString) {
    if (dateString == null || dateString.trim().isEmpty()) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    try {
      return LocalDate.parse(dateString, formatter);
    } catch (Exception ex) {
      log.debug("parse string to date error", ex);
    }
    return null;
  }

  public static String localDateToString(LocalDate date) {
    if (date == null) {
      return null;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return date.format(formatter);
  }

  public static Integer calculateAge(LocalDate dob, LocalDate effectiveDate) {
    if (dob != null && effectiveDate != null) {
      return Period.between(dob, effectiveDate).getYears();
    } else {
      return null;
    }
  }

  public static void main(String[] args) throws ParseException {

    String dateInput = "2022-06-03T23:00:46+07:00";
    SimpleDateFormat formatter = new SimpleDateFormat(RFC3339_DATE_FORMAT);
    Date date = formatter.parse(dateInput);
    System.out.println(dateInput);
    Date dateEnd = new Date(date.getTime() + (5 * 60 * 1000));
    System.out.println(convertToRFC3339(dateEnd));

    long endTime = dateEnd.getTime();
    long currentTine = System.currentTimeMillis();
    boolean isExpired = endTime <= currentTine;

    System.out.println(isExpired);
    System.out.println(endTime);
    System.out.println(currentTine);
  }

  private boolean isExpired(String requestDate) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(RFC3339_DATE_FORMAT);
      Date date = formatter.parse(requestDate);
      Date maxDate = new Date(date.getTime() + (5 * 60 * 1000));
      return maxDate.getTime() <= System.currentTimeMillis();
    } catch (Exception e) {
      return true;
    }
  }

  public static Long addToDate(Date date, int field, int amount) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(field, amount);
    return calendar.getTime().getTime();
  }

  public static Date toUTCDate(Date fromDate) {
    Calendar c = Calendar.getInstance();
    c.setTime(fromDate);
    c.add(Calendar.HOUR, -7);
    return c.getTime();
  }

  public static Date toUTCEndOfDate(Date toDate) {
    Calendar c = Calendar.getInstance();
    c.setTime(toDate);
    c.add(Calendar.HOUR, 17);
    return c.getTime();
  }

  public static Date convertUTCDate(Date fromDate) {
    Calendar c = Calendar.getInstance();
    c.setTime(fromDate);
    c.add(Calendar.HOUR, 7);
    return c.getTime();
  }

  //Convert Date to Calendar
  public static Calendar dateToCalendar(Date date) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

  //Convert Calendar to Date
  public static Date calendarToDate(Calendar calendar) {
    return calendar.getTime();
  }

  public static Integer calculateAge(Date birthday) {
    Calendar birthCal = Calendar.getInstance();
    birthCal.setTime(birthday);
    return Calendar.getInstance().get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
  }

  public static Date getCurrentUtcTime() throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
    return localDateFormat.parse(simpleDateFormat.format(new Date()));
  }

  public static String convertTimestampToString(Timestamp timestamp) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    return dateFormat.format(timestamp);
  }

  public static Date convertStringToDate(String dateStr, String format) throws ParseException {
    return new SimpleDateFormat(format).parse(dateStr);
  }

  public static String convertDateToString(Date date, String patternFormat){
    SimpleDateFormat formatter = new SimpleDateFormat(patternFormat);
    return formatter.format(date);
  }
}
