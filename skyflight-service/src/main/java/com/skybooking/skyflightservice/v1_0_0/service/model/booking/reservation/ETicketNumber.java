package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import lombok.Data;

import java.io.Serializable;

@Data
public class ETicketNumber implements Serializable {

    private String elementId;
    private int index;
    private int id;
    private String content;

}
