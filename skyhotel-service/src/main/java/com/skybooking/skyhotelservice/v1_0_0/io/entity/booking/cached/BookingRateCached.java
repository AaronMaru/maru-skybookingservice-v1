package com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.cached;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookingRateCached implements Serializable {
    private Integer rooms;
    private Integer adults;
    private Integer children;
    private String rateKey;
    private List<RatePaxCached> paxes;
}
