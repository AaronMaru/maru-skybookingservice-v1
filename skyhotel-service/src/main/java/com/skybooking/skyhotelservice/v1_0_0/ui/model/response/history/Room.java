package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history;

import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class Room {
    private BigInteger id;
    private String code;
    private Integer bookingId;
    private String supplierReference;
    private String status;
    private List<Pax> paxes = new ArrayList<>();
    private List<Rate> rates = new ArrayList<>();
}