package com.skybooking.stakeholderservice.v1_0_0.util.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeBean {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Date to iso date
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param date
     */
    public String convertDateTimeISO(Date date) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String text = sdf.format(date);

        return text;

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

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * convert date time string to date object
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return Date
     */
    public Date convertDateTimes(String Date) {

        try {
            var date = new SimpleDateFormat("yyyy-MM-dd");
            return date.parse(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

}
