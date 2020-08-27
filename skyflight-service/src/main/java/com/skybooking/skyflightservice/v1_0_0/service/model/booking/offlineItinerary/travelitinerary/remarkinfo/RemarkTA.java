package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.remarkinfo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RemarkTA implements Serializable {
    private String text;
    private String type;
    private String rph;
    private Integer id;
}
