package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ApplicationResultTA implements Serializable {
    private String status;
}
