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
}