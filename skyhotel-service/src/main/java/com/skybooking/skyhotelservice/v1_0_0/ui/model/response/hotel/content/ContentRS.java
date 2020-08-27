package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

@Data
public class ContentRS {
    private Integer hotelCode;
    private Boolean isFavorite = true;
    private Boolean isCancellation;
    private String address;
    private String email;
}
