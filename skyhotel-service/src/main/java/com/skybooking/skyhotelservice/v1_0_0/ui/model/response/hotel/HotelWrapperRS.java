package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.filter.FilterRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort.SortRS;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelWrapperRS<T> {

    private String requestId;
    private FilterRS filterInfo;
    private SortRS sortInfo;
    private ResourceRS resource;
    private List<T> hotelList = new ArrayList<>();

    public HotelWrapperRS(String requestId, List<T> hotelList) {
        this.requestId = requestId;
        this.hotelList = hotelList;
    }

    public HotelWrapperRS(String requestId, FilterRS filterInfo, SortRS sortInfo, List<T> hotelList) {
        this.requestId = requestId;
        this.sortInfo = sortInfo;
        this.filterInfo = filterInfo;
        this.hotelList = hotelList;
    }
    
}
