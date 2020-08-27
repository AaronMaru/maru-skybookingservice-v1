package com.skybooking.skyhotelservice.v1_0_0.client.model.response.availability;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.BaseHotelRSDS;
import lombok.Data;

@Data
public class AvailabilityRSDS {

    private int status;
    private String message = "";
    private BaseHotelRSDS data;

}
