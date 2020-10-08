package com.skybooking.skyflightservice.v1_0_0.util.datetime;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DatetimeFormat {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get datetime by format datetime
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param format
     * @param date
     * @return
     * @throws Exception
     */
    public static String parse(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String parse(String format, String date) {
        return ZonedDateTime.parse(date).toLocalDateTime().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get age by birthday
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param birthday
     * @return
     */
    public static Period age(Date birthday) {
        ZoneId defaultZoneId = ZoneId.systemDefault();

        Instant instant = birthday.toInstant(); // Convert Date -> Instant

        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate(); // Instant + system default time zone + toLocalDate() = LocalDate

        LocalDate now = LocalDate.now(); // gets localDate NOW

        return Period.between(localDate, now);
    }

    public static int getMinutesBetweenTwoDatetime(String dateString1, String dateString2) {
        try {
            Date date1 =new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateString1.replace("T", ""));
            Date date2 =new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateString2.replace("T", ""));
            long value = date2.getTime() - date1.getTime();
            long minutes = value / 60000; // convert millisecond to minutes
            return Math.toIntExact(minutes);
        } catch (Exception e) {
            System.out.println(":::::::::::::::::::::::::::: CATCH ::::::::::::::::::::::::::::::");
            return 0;
        }
    }
}