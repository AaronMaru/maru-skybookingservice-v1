package com.skybooking.skyhistoryservice.v1_0_0.util.flight;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight.FlightInfoEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.flight.FlightInfoRP;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Date;
import java.util.HashMap;


public class FlightShoppingBean {

    @Autowired
    private Environment environment;

    @Autowired
    private FlightInfoRP flightInfoRP;


    public HashMap<String, String> getAirlineInfoLogo(String airlineCode) {

        FlightInfoEntity flightInfo = flightInfoRP.findFirstByAirlineCode(airlineCode);

        HashMap<String, String> airLineLogo = new HashMap<>();

        airLineLogo.put("logo45", environment.getProperty("spring.awsImageUrl.airline") + "45/" + flightInfo.getLogo());
        airLineLogo.put("logo90", environment.getProperty("spring.awsImageUrl.airline") + "90/" + flightInfo.getLogo());

        if (flightInfo != null) {
            airLineLogo.put("logo45", environment.getProperty("spring.awsImageUrl.airline") + "45/" + flightInfo.getLogo());
            airLineLogo.put("logo90", environment.getProperty("spring.awsImageUrl.airline") + "90/" + flightInfo.getLogo());
        }

        return airLineLogo;

    }

    public String getPassTypeCode(String passType) {

        String code = "";

        if (passType.equals("Adult")) {
            code = "ADT";
        }

        if (passType.equals("Child")) {
            code = "CNN";
        }

        if (passType.equals("Infant")) {
            code = "INF";
        }

        return code;

    }

    public String DateToString(Date date) {
        String format = "";
        if (date != null) {
            format = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:SS");
        }

        return format;
    }

}
