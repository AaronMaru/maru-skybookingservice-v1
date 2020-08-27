package com.skybooking.skypointservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
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
}
