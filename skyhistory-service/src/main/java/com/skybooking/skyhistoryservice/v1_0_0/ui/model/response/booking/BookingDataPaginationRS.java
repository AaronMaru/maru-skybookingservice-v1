package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

import java.util.List;

@Data
public class BookingDataPaginationRS {

    private Integer size;
    private Integer page;
    private Long totals;

    private List<BookingRS> data;

}
