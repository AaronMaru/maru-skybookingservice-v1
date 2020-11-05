package com.skybooking.backofficeservice.v1_0_0.client.model.response.hotel;

import lombok.Data;

@Data
public class HotelDetailServiceRS {
    private int status;
    private String message;
    private DetailServiceRS data;
}


