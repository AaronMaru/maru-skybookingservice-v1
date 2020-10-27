package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.similarhotel;

import lombok.Data;

import java.util.List;

@Data
public class SimilarHotelRQ {
    private List<Integer> hotels;
    private int radius;
}
