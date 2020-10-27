package com.skybooking.skyhotelservice.v1_0_0.client.model.request.similarhotel;

import lombok.Data;

import java.util.List;

@Data
public class SimilarHotelDSRQ {
    private List<Integer> hotels;
    private int radius;
}
