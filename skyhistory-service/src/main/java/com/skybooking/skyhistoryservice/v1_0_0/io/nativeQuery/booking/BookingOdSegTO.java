package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking;

import lombok.Data;
import java.util.Date;

@Data
public class BookingOdSegTO {

    private Integer id;
    private String airlineCode;
    private String airlineName;
    private Integer flightNumber;
    private String equipType;
    private String airCraftName;
    private Integer stop;
    private String depTerminal;
    private Integer adjustmentDate;


    private Date depDateTime;
    private String depLocation;
    private String depLocationName;
    private String depCity;
    private String depCountry;


    private Date arrDateTime;
    private String arrLocation;
    private String arrLocationName;
    private String arrCity;
    private String arrCountry;
    private String arrTerminal;

}
