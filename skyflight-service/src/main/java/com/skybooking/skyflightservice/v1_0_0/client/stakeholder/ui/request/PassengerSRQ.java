package com.skybooking.skyflightservice.v1_0_0.client.stakeholder.ui.request;

import lombok.Data;

@Data
public class PassengerSRQ {

    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
    private String nationality;
    private String idNumber;
    private String expireDate;
    private Integer idType;

}
