package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Keywords {
    @Valid
    private List<@NotNull Integer> keyword;
    private boolean allIncluded;
}
