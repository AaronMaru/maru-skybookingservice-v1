package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger;

import lombok.Data;

import java.util.List;

@Data
public class PassengerPagingRS {

    private Integer size;
    private Integer page;
    private Long totals;

    private List<PassengerRS> data;

}
