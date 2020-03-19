package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BaggageInfoRS {

    private String segment;
    List<BaggageAllowanceRS> baggageAllowance = new ArrayList<>();

}
