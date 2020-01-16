package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking;

import lombok.Data;

@Data
public class BSegmentDRQ {

    private String depDateTime;
    private String arrDateTime;
    private String flightNum;
    private String resBookDesignCode;
    private String status;
    private String depCode;
    private String arrCode;
    private String airline;
    private String numInParty;
}
