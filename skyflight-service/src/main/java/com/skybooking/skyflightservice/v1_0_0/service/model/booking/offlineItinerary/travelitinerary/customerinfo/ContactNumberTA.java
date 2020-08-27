package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.customerinfo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ContactNumberTA implements Serializable {

    private String phone;
    private String locationCode;
    private String rph;
    private Integer id;

}
