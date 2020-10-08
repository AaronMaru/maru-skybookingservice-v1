package com.skybooking.skyhotelservice.v1_0_0.client.model.request.content;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HotelsRQDS {
    private List<Integer> hotels;
}
