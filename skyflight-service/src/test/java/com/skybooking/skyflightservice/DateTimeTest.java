package com.skybooking.skyflightservice;

import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DateTimeTest {

    @Test
    public void dateTimeTest(){
        var depPNH = "2020-01-04T15:50:00+07:00";
        var arrCAN = "2020-01-04T19:30:00+08:00";

        System.out.println("PHN-CAN: " + DateUtility.getMinuteDurations(depPNH, arrCAN));

        var depCAN = "2020-01-04T21:30:00+08:00";
        System.out.println("CAN Delay: " + DateUtility.getMinuteDurations(arrCAN, depCAN));

        var arrLAX = "2020-01-04T18:10:00-08:00";
        System.out.println("CAN-LAX: " + DateUtility.getMinuteDurations(depCAN, arrLAX));

        var depLAX = "2020-01-04T21:00:00-08:00";
        System.out.println("LAX Delay: " + DateUtility.getMinuteDurations(arrLAX, depLAX));
        var arrJFK = "2020-01-05T05:17:00-05:00";
        System.out.println("LAX-JFK: " + DateUtility.getMinuteDurations(depLAX, arrJFK));

        System.out.println(DateUtility.getMinuteDurations("2020-02-04T19:40:00+07:00", "2020-02-04T20:50:00+07:00"));
    }
}
