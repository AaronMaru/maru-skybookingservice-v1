package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

import java.util.List;

@Data
public class BookingActivityPagingRS {

    private Integer size;
    private Integer page;
    private Long totals;

    private List<BookingActivityRS> data;

}
