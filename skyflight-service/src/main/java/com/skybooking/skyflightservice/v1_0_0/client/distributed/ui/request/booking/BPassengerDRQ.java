package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking;

import lombok.Data;

import java.util.Date;

@Data
public class BPassengerDRQ {

    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private String nationality;
    private Integer idType;
    private String idNumber;
    private Date expireDate;
}
