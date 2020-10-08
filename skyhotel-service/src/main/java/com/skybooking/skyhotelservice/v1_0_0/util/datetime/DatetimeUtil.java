package com.skybooking.skyhotelservice.v1_0_0.util.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DatetimeUtil {

    public static long night(String checkIn, String checkOut) {

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date startDate = formatDate.parse(checkIn);
            Date endDate = formatDate.parse(checkOut);
            long diff = (endDate.getTime() - startDate.getTime());

            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        } catch (ParseException e) {

            e.printStackTrace();
            return 0;

        }
    }

    public static Date toDate(String s) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            return formatDate.parse(s);
        } catch (Exception exception) {
            return null;
        }
    }

    public static ZonedDateTime toZonedDateTime(String s) {
        try {
            return ZonedDateTime.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toFormattedDateTime(String datetime) {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetime);
            return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(zonedDateTime);
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertDateFormat(Date date) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("MMMM,dd,yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String text = sdf.format(date);
        return text;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Date to iso date
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param date
     */
    public static String convertDateTimeISO(Date date) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String text = sdf.format(date);

        return text;

    }

}
