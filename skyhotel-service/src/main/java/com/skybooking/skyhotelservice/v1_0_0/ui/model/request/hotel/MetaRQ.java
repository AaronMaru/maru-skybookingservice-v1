package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;

@Data
public class MetaRQ {
    private String requestId;
    private Integer page;
    private Integer size;
}
