package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class CancellationRS {
    private Boolean cancellable;
    @JsonIgnore
    private List<FeeRS> fees;
    private CancellationDetailRS detail;
}
