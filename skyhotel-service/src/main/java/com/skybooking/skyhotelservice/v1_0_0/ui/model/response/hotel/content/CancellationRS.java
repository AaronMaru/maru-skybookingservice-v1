package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

import java.util.List;

@Data
public class CancellationRS {
    private Boolean cancellable;
    private List<FeeRS> fees;
}
