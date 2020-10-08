package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Pax {
    private BigInteger id;
    private Integer roomNumber;
    private String firstName;
    private String lastName;
    private String type;
    private Integer age;
}