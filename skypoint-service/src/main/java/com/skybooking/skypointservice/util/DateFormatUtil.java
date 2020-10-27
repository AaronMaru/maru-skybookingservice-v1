package com.skybooking.skypointservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
    public static String dateFormat(Date date) {
        String format = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
            format = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return format;
    }

    public static boolean isValidFormat(String value) {
        String format = "yyyy-MM-dd";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

}
