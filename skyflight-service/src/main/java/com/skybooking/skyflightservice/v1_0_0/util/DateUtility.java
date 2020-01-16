package com.skybooking.skyflightservice.v1_0_0.util;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
        return ZonedDateTime.parse(date).plusDays(days).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

}
