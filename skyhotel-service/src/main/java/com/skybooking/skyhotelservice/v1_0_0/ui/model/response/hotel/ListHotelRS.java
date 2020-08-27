package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.filter.FilterRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort.SortRS;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListHotelRS {
    private String requestId;
    private FilterRS filterInfo;
    private SortRS sortInfo;
    private List<HotelRS> hotelList = new ArrayList<>();
}
