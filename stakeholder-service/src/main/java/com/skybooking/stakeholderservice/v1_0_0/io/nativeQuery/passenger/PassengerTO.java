package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.passenger;


import lombok.Data;

import java.util.Date;

@Data
public class PassengerTO {

    private Integer id;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private String gender;
    private String nationality;
    private String isoCountry;
    private String idNumber;
    private Date expireDate;
    private byte idType;

}
