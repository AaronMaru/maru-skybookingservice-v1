package com.skybooking.skyhotelservice.v1_0_0.client.model.response.hotel;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.BaseHotelRSDS;
import lombok.Data;

@Data
public class DataHotelRSDS {

    private int status;
    private String message = "";
    private BaseHotelRSDS data;

}
