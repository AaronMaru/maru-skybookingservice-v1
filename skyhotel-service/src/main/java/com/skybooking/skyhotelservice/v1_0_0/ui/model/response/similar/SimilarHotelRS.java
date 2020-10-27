package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.similar;

import lombok.Data;

import java.util.List;

@Data
public class SimilarHotelRS {
    private HotelCode data;

}

@Data
class HotelCode {
    private List<Integer> hotelCode;
}