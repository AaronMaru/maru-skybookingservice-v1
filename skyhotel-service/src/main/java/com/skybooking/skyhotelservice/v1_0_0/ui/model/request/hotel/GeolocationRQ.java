package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import com.skybooking.skyhotelservice.exception.anotation.IsDecimal;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class GeolocationRQ {
    @NotNull(message = "latitude cannot be null")
    @IsDecimal
    private Double latitude;

    @NotNull(message = "longitude cannot be null")
    @IsDecimal
    private Double longitude;

    @Positive
    @Max(200)
    private Integer radius = 20;
}
