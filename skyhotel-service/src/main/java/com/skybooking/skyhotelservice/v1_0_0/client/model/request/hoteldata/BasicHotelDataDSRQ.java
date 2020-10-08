package com.skybooking.skyhotelservice.v1_0_0.client.model.request.hoteldata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicHotelDataDSRQ {
    List<Integer> hotels;
}
