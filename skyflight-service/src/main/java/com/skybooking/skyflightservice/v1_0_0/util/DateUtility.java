package com.skybooking.skyflightservice.v1_0_0.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtility {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * calculate between date time zoned to minutes
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param start
     * @param end
     * @return long
     */
    public static long getMinuteDurations(String start, String end) {
        return Duration.between(ZonedDateTime.parse(start), ZonedDateTime.parse(end)).toMinutes();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * plus days to datetime zoned
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param date
     * @param days
     * @return String
     */
    public static String plusDays(String date, long days) {

        if (days == 0) return date;
        return ZonedDateTime.parse(date).plusDays(days).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * minus minutes of zone date time
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param dateTime
     * @param minutes
     * @return String
     */
    public static String minusMinutesZonedDateTime(String dateTime, long minutes) {

        if (minutes == 0) return dateTime;
        return ZonedDateTime.parse(dateTime).minusMinutes(minutes).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get departure window from zone date time
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param dateTime
     * @param minutes
     * @return String
     */
    public static String getDepartureWindow(String dateTime, long minutes) {

        var formatter = DateTimeFormatter.ofPattern("HHmm");
        var arrivalTime = ZonedDateTime.parse(dateTime).format(formatter);
        var departureTime = ZonedDateTime.parse(DateUtility.minusMinutesZonedDateTime(dateTime, minutes)).format(formatter);

        return departureTime + arrivalTime;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * convert zoned date time string to date object
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param zonedDateTime
     * @return Date
     */
    public static Date convertZonedDateTimeToDateTime(String zonedDateTime) {

        var formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(" ")
            .append(DateTimeFormatter.ISO_LOCAL_TIME)
            .toFormatter();

        var dateTime = ZonedDateTime.parse(zonedDateTime).format(formatter);

        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * convert zoned date time string to date string yyyyMMdd
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param zonedDateTime
     * @return String
     */
    public static String convertZonedDateTimeToDateString(String zonedDateTime) {

        var formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(" ")
            .append(DateTimeFormatter.ISO_LOCAL_TIME)
            .toFormatter();

        var dateTime = ZonedDateTime.parse(zonedDateTime).format(formatter);

        try {

            var date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);

            return new SimpleDateFormat("yyMMdd").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * convert date time string to date
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param dateTime
     * @param format
     * @return Date
     */
    public static Date convertDateTimeToDate(String dateTime, String format) {

        try {
            return new SimpleDateFormat(format).parse(dateTime);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get zoned id number from zoned date time string
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param zonedDateTime
     * @return Integer
     */
    public static Integer getZonedDateTimeId(String zonedDateTime) {

        try {

            String zoneId = ZonedDateTime.parse(zonedDateTime).format(DateTimeFormatter.ofPattern("X"));

            if (zoneId.equalsIgnoreCase("Z")) return 0;
            return Integer.valueOf(zoneId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get minutes between timestamp and system's time
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param timestamp
     * @return
     */
    public static long getMinutesMillisecondsSystemTimeStamp(long timestamp) {
        return TimeUnit.MILLISECONDS.toMinutes(timestamp - System.currentTimeMillis());
    }

}
