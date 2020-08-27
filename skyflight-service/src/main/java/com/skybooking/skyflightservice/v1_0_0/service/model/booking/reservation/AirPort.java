package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import lombok.Data;

import java.io.Serializable;

@Data
public class AirPort implements Serializable {
    private String name;
    private String content;
}
