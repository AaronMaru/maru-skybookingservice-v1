package com.skybooking.eventservice.v1_0_0.util.general;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ApiBean {

    public DecimalFormat decimalFormat = new DecimalFormat("#.##");

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate unique name for file
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return String
     */
    public String generateFileName(String ext) {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String name = UUID.randomUUID().toString().replace("-", "").substring(0, 10) + timeStamp;

        if (ext != null) {
            name = name + "." + ext;
        }

        return name;

    }
}
