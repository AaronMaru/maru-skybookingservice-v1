package com.skybooking.skyhotelservice.v1_0_0.client.model.response.hotel;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.hoteldata.BasicHotelDataDSRS;
import lombok.Data;

import java.util.List;

@Data
public class DataHotelDSRS {

    private int status;
    private String message = "";
    private List<BasicHotelDataDSRS> data;

}
