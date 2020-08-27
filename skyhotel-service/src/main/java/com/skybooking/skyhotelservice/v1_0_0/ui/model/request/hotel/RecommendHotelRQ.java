package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RecommendHotelRQ {

    List<String> destinations;

    @NotNull(message = "Latitude must not be null")
    private BigDecimal latitude;

    @NotNull(message = "Longitude must not be null")
    private BigDecimal longitude;

}
