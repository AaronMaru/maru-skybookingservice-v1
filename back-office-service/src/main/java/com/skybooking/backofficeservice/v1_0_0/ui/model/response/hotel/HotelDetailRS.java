package com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel;

import lombok.Data;

@Data
public class HotelDetailRS {
    private int status;
    private String message;
    private DetailRS data;
}
